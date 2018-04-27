package com.gl365.member.web;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.aliyun.ons.OnsProducer;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.MD5Utils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.SendSMSReq;
import com.gl365.member.dto.SmsCodeDto;
import com.gl365.member.dto.SmsCountLimit;
import com.gl365.member.dto.VerifySMSReq;
import com.gl365.member.dto.users.UserUpdateDto;
import com.gl365.member.model.User;
import com.gl365.member.service.RedisService;
import com.gl365.member.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/member/sms")
public class SmsController {

	@Value("${sms.config.expireTime}")
	private int expireTime;
	
	@Value("${sms.config.smsCodeRedisLiveTime}")
	private long smsCodeRedisLiveTime;
	
	@Value("${sms.config.smsMaxCount}")
	private int smsMaxCount;
	
	@Value("${sms.config.countLimitCycleTime}")
	private long countLimitCycleTime;
	
	@Value("${sms.config.smsCountRedisLiveTime}")
	private long smsCountRedisLiveTime;
	
	@Value("${sms.smsContext.default}")
	private String smsContextIndex;
	
	@Value("${sms.smsContext.register}")
	private String smsContextIndex0;
	
	@Value("${sms.smsContext.modifyPhoneNo}")
	private String smsContextIndex1;
	
	@Value("${sms.smsContext.login}")
	private String smsContextIndex2;
	
	@Value("${sms.smsContext.findLoginPwd}")
	private String smsContextIndex3;
	
	@Value("${sms.smsContext.oldPhoneNo}")
	private String smsContextIndex4;
	
	@Value("${sms.smsContext.bindOperator}")
	private String smsContextIndex5;
	
	@Value("${sms.smsContext.setBankCard}")
	private String smsContextIndex6;
	
	@Value("${sms.smsContext.setBindMobile}")
	private String smsContextIndex8;
	
	private static final String SEND_SMS = "sendSms";

	private static final String SEND_SMS_COUNT_LIMIT = "sendSmsCountLimit";

	private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
	
	private static final ZoneId TIMEZONE_ID = TimeZone.getTimeZone("Asia/Shanghai").toZoneId();

	@Autowired
	private UserService userService;

	@Autowired
	private RedisService redisService;

	@Lazy
	@Resource(name = "sms-producer")
	private OnsProducer smsProducer;
	
	@PostMapping("/sendSms")
	public Object sendSms(@RequestBody SendSMSReq req) {
		LOG.info("sendSms begin,reqParam:req={}", req.toString());
		ResultDto<String> resp = validateReqParam(req);
		if (null != resp && null != resp.getResult()) {
			return resp;
		}

		try {
			LocalDateTime currentTime = LocalDateTime.now(TIMEZONE_ID);
			StringBuffer key = new StringBuffer(SEND_SMS_COUNT_LIMIT);
			key.append("_").append(req.getPhoneNo());
			SmsCountLimit redisSmsCount = (SmsCountLimit) redisService.get(key.toString());
			if (redisSmsCount != null) {
				if(redisSmsCount.getLastSendTime() != null){
					//控制接口1分钟内只能调用一次
					LocalDateTime expired = LocalDateTime.ofEpochSecond(redisSmsCount.getLastSendTime().toEpochSecond(ZoneOffset.UTC) + 59, 0, ZoneOffset.UTC);
					if(currentTime.isBefore(expired)){
						resp = new ResultDto<>(ResultCodeEnum.Sms.SEND_SMS_LIMIT_PERMINU);
						LOG.info("sendSms limit send perminute,resp:{}",JsonUtils.toJsonString(resp));
						return resp;
					}
				}
				
				Boolean countLimitRlt = sendCountLimit(req.getPhoneNo(), redisSmsCount, currentTime);
				if (null == countLimitRlt) {
					LOG.error("sendSms ===> sendCountLimit exception");
					return ResultDto.getErrInfo();
				} else if (!countLimitRlt) {
					resp.setResult(ResultCodeEnum.Sms.SEND_SMS_TRANSFINITE.getCode());
					resp.setDescription(ResultCodeEnum.Sms.SEND_SMS_TRANSFINITE.getMsg());
					return resp;
				}
			}else{
				redisSmsCount = new SmsCountLimit(req.getPhoneNo(), 1, currentTime, currentTime);
			}

			// 生成6位随机码
			String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
			String context = String.format(buildSmsContextTemp(req.getBusinessType()), verifyCode,
					expireTime/60);
			// 发送mq
			send(req.getPhoneNo(), context);
			// 将验证码存到redis中
			setSmsCode2Redis(req.getPhoneNo(), verifyCode, req.getBusinessType());
			resp.setResult(ResultCodeEnum.System.SUCCESS.getCode());
			resp.setDescription(ResultCodeEnum.System.SUCCESS.getMsg());
			LOG.info("sendSms success,verifyCode={}", verifyCode);
			redisService.set(key.toString(), redisSmsCount, smsCountRedisLiveTime);
		} catch (Exception e) {
			LOG.error("sendSms exception,e:" + e);
			resp.setResult(ResultCodeEnum.System.SYSTEM_ERROR.getCode());
			resp.setDescription(ResultCodeEnum.System.SYSTEM_ERROR.getMsg());
		}
		return resp;
	}

	@PostMapping("/verifySmsCode")
	public Object verifySmsCode(@RequestBody VerifySMSReq req) {
		LOG.info("verifySmsCode begin,req:{}", req.toString());
		ResultDto<String> resp = new ResultDto<>();
		if (StringUtils.isEmpty(req.getPhoneNo()) || req.getBusinessType() == null || StringUtils.isEmpty(req.getVerifyCode())) {
			resp.setResult(ResultCodeEnum.System.PARAM_NULL.getCode());
			resp.setDescription(ResultCodeEnum.System.PARAM_NULL.getMsg());
			return resp;
		}
		try {
			// 从redis获取短信验证码对象
			SmsCodeDto scd = getSmsCodeFromRedis(req.getPhoneNo(), req.getBusinessType());
			if (null == scd) {
				LOG.error("verifySmsCode ===> getSmsCodeFromRedis rlt is null");
				resp.setResult(ResultCodeEnum.Sms.SMS_CODE_EXPIRED.getCode());
				resp.setDescription(ResultCodeEnum.Sms.SMS_CODE_EXPIRED.getMsg());
				return resp;
			}
			// 校验验证码
			verify(resp, scd, req);
			// 验证成功后删除redis验证码
			if (ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult())) {
				delSmsCodeFromRedis(req.getPhoneNo(), req.getBusinessType());
			}
		} catch (Exception e) {
			LOG.error("verifySmsCode 校验短信验证码异常,e:" + e);
			resp.setResult(ResultCodeEnum.System.SYSTEM_ERROR.getCode());
			resp.setDescription(ResultCodeEnum.System.SYSTEM_ERROR.getMsg());
		}

		return resp;
	}
	
	@PostMapping("/getSmsCode")
	@ApiOperation("业务类型 0：注册 1：修改手机号 2：登录 3：忘记登录密码")
	public Object getSmsCode(@RequestParam("mobile") String mobile, @RequestParam("businessType") Integer businessType) {
		SmsCodeDto scd = getSmsCodeFromRedis(mobile, businessType);
		return scd == null ? "验证码过期" : scd.getVerifyCode();
	}

	/**
	 * 发送mq消息
	 * 
	 * @param mobile
	 * @param content
	 */
	private void send(String mobile, String content) {
		String message = String.format("{'content':'%s','mobiles':['%s']}", content, mobile);
		LOG.debug("send SMS message [{}]", message);
		smsProducer.send(message);
	}

	@PostMapping("/bindMobileSendSms")
	@ApiOperation("微信新用户注册绑定完后成发送短信")
	public Object bindMobileSendSms(@RequestParam("mobile") String mobile) {
		LOG.info("bindMobileSendSms begin,reqParam:mobile={}", mobile);
		ResultDto<?> resp = null;
		try {
			// 生成8位随机码
			StringBuilder str = new StringBuilder();//定义变长字符串
			Random random=new Random();
			//随机生成数字，并添加到字符串
			for(int i=0;i<8;i++){
				str.append(random.nextInt(10));
			}
			String password = str.toString();
			String context = String.format(smsContextIndex8,  password);
			// 发送mq
			send(mobile, context);
			LOG.info("sendSms success,mobile={},password={}", mobile,password);
			
			//发送成功后调用接口修改密码
			User user = userService.getUserInfoByMobilePhone(mobile);
			UserUpdateDto userUpdateDto = new UserUpdateDto();
			userUpdateDto.setUserId(user.getUserId());
			userUpdateDto.setPassword(MD5Utils.md5Password(password));
			userUpdateDto.setModifyTime(LocalDateTime.now());
			userService.updateUserByUserId(userUpdateDto);
			
			resp = new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		} catch (Exception e) {
			LOG.error("sendSms exception,e:" + e);
			resp = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		return resp;
	}
	
	/**
	 * 验证码保存到redis
	 * 
	 * @param phoneNo
	 * @param verifyCode
	 * @param businessType
	 */
	private void setSmsCode2Redis(String phoneNo, String verifyCode, int businessType) {
		StringBuffer key = new StringBuffer(phoneNo);
		key.append("_").append(SEND_SMS).append("_").append(businessType);
		SmsCodeDto scd = new SmsCodeDto();
		scd.setPhoneNo(phoneNo);
		scd.setVerifyCode(verifyCode);
		scd.setSendTime(LocalDateTime.now());
		scd.setExpireTime(expireTime);
		try {
			redisService.set(key.toString(), scd, smsCodeRedisLiveTime);
		} catch (Exception e) {
			LOG.error("set smsCode to redis exception,e:{}" + e);
		}
	}

	/**
	 * 从redis获取短信验证码对象，不存在返回null
	 * 
	 * @param phoneNo
	 * @param businessType
	 * @return
	 */
	private SmsCodeDto getSmsCodeFromRedis(String phoneNo, int businessType) {
		StringBuffer key = new StringBuffer(phoneNo);
		key.append("_").append(SEND_SMS).append("_").append(businessType);
		SmsCodeDto scd = null;
		try {
			scd = (SmsCodeDto) redisService.get(key.toString());
		} catch (Exception e) {
			LOG.error("get smsCode from redis exception,e:" + e);
			return null;
		}
		return scd;
	}

	private void delSmsCodeFromRedis(String phoneNo, int businessType) {
		StringBuffer key = new StringBuffer(phoneNo);
		key.append("_").append(SEND_SMS).append("_").append(businessType);
		try {
			redisService.del(key.toString());
		} catch (Exception e) {
			LOG.error("get smsCode from redis exception,e:" + e);
		}
		return;
	}

	/**
	 * 参数校验
	 * 
	 * @param resp
	 * @param req
	 */
	private ResultDto<String> validateReqParam(SendSMSReq req) {
		ResultDto<String> resp = new ResultDto<>();
		if (StringUtils.isEmpty(req.getPhoneNo()) || req.getBusinessType() == null) {
			resp.setResult(ResultCodeEnum.System.PARAM_NULL.getCode());
			resp.setDescription(ResultCodeEnum.System.PARAM_NULL.getMsg());
			return resp;
		}
		Boolean result = null;
		try {
			switch (req.getBusinessType()) {
			// 注册
			case 0:
			//修改手机号给新手机号
			case 1:
				// 需要判断手机号是否已注册,已注册不发送短信验证码
				result = userService.queryIsRegisterByMobileNo(req.getPhoneNo());
				if (result != null) {
					resp.setResult(ResultCodeEnum.Sms.PHONENO_HAS_REGISTER.getCode());
					resp.setDescription(ResultCodeEnum.Sms.PHONENO_HAS_REGISTER.getMsg());
				}
				break;
			//登录	
			case 2:
			//修改登录密码(忘记密码)
			case 3:
				// 需要判断手机号是否已注册,为注册不发送短信验证码
				result = userService.queryIsRegisterByMobileNo(req.getPhoneNo());
				if (result == null) {
					resp.setResult(ResultCodeEnum.Sms.PHONENO_HAS_NOT_REGISTER.getCode());
					resp.setDescription(ResultCodeEnum.Sms.PHONENO_HAS_NOT_REGISTER.getMsg());
				}
				break;
			//修改手机号给旧手机号
			case 4:
			//绑定操作员
			case 5:
			//b端设置银行卡
			case 6:
				//需要判断手机号是否已注册,未注册不发送短信验证码
				result = userService.queryIsRegisterByMobileNo(req.getPhoneNo());
				if (result == null) {
					resp.setResult(ResultCodeEnum.Sms.PHONENO_HAS_NOT_REGISTER.getCode());
					resp.setDescription(ResultCodeEnum.Sms.PHONENO_HAS_NOT_REGISTER.getMsg());
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			LOG.error("sendSms ===>userService.queryIsRegisterByMobileNo 异常，e：" + e);
			resp.setResult(ResultCodeEnum.System.SYSTEM_ERROR.getCode());
			resp.setDescription(ResultCodeEnum.System.SYSTEM_ERROR.getMsg());
		}
		return resp;

	}

	/**
	 * 获取短信模板
	 * 
	 * @param businessType
	 * @return
	 */
	private String buildSmsContextTemp(int businessType) {
		String context = "";
		switch (businessType) {
		case 0:
			context = smsContextIndex0;
			break;
		case 1:
			context = smsContextIndex1;
			break;
		case 2:
			context = smsContextIndex2;
			break;
		case 3:
			context = smsContextIndex3;
			break;
		case 4:
			context = smsContextIndex4;
			break;
		case 5:
			context = smsContextIndex5;
			break;
		case 6:
			context = smsContextIndex6;
			break;
		default:
			context = smsContextIndex;
			break;
		}
		return context;
	}

	/**
	 * 校验短信验证码有效期以及是否正确
	 * 
	 * @param resp
	 * @param scd
	 * @param req
	 */
	private void verify(ResultDto<String> resp, SmsCodeDto scd, VerifySMSReq req) {
		LocalDateTime currentTime = LocalDateTime.now(TIMEZONE_ID);
		LocalDateTime expired = LocalDateTime.ofEpochSecond(scd.getSendTime().toEpochSecond(ZoneOffset.UTC) + scd.getExpireTime(), 0, ZoneOffset.UTC);
		if (currentTime.isBefore(expired)) {
			if (!req.getPhoneNo().equals(scd.getPhoneNo()) || !req.getVerifyCode().equals(scd.getVerifyCode())) {
				resp.setResult(ResultCodeEnum.Sms.SMS_CODE_ERROR.getCode());
				resp.setDescription(ResultCodeEnum.Sms.SMS_CODE_ERROR.getMsg());
			} else {
				resp.setResult(ResultCodeEnum.System.SUCCESS.getCode());
				resp.setDescription(ResultCodeEnum.System.SUCCESS.getMsg());
			}
		} else {
			resp.setResult(ResultCodeEnum.Sms.SMS_CODE_EXPIRED.getCode());
			resp.setDescription(ResultCodeEnum.Sms.SMS_CODE_EXPIRED.getMsg());
		}

	}

	private Boolean sendCountLimit(String phoneNo, SmsCountLimit redisSmsCount, LocalDateTime currentTime) {
		LOG.info("sendCountLimit begin,phoneNo={}", phoneNo);
		try {
			LocalDateTime expired = LocalDateTime.ofEpochSecond(redisSmsCount.getBeginTime().toEpochSecond(ZoneOffset.UTC) + countLimitCycleTime, 0, ZoneOffset.UTC);
			// 判读是否在一个周期内，超过一个周期或者超过一个自然日更新redis，还在一个周期则返回
			if (currentTime.isAfter(expired) || currentTime.toLocalDate().isAfter(redisSmsCount.getBeginTime().toLocalDate())) {
				LOG.info("此周期第一次发送短信验证码,phoneNo={},currentTime={}", phoneNo, currentTime);
				redisSmsCount.setBeginTime(currentTime);
				redisSmsCount.setCount(1);
				redisSmsCount.setPhoneNo(phoneNo);
				redisSmsCount.setLastSendTime(currentTime);
				return true;
			}
			if (redisSmsCount.getCount() >= smsMaxCount) {
					LOG.info("phoneNo={}在此周期内发送次数超过{}次,首次发送时间为beginTime={}，在此时间{}后解除限制", phoneNo,
							smsMaxCount, redisSmsCount.getBeginTime(),
							expired);
					return false;
			} else {
				LOG.info("phoneNo={} 第{}次发送短信验证码 ", phoneNo, redisSmsCount.getCount() + 1);
				redisSmsCount.setCount(redisSmsCount.getCount() + 1);
				redisSmsCount.setLastSendTime(currentTime);
				return true;
			}
		} catch (Exception e) {
			LOG.error("sendCountLimit exception ,e:" + e);
			return null;
		}
	}

	@DeleteMapping("sendSmsCountLimit")
	public void delectLimit(@RequestParam("phoneNo") String phoneNo) {
		LOG.info("delectLimit begin,phoneNo={}", phoneNo);
		try {
			StringBuffer key = new StringBuffer(SEND_SMS_COUNT_LIMIT);
			key.append("_").append(phoneNo);
			redisService.del(key.toString());
			LOG.info("delectLimit end");
		} catch (Exception e) {
			LOG.error("delectLimit exception,e:" + e);
		}
	}
}
