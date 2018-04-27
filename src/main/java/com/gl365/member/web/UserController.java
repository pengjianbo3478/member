package com.gl365.member.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.aliyun.ons.OnsProducer;
import com.gl365.member.common.FallbackHandlerUtils;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.PageDto;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.ValidatePwdReq;
import com.gl365.member.dto.manage.InviteUserCount;
import com.gl365.member.dto.manage.InviteUserCountReq;
import com.gl365.member.dto.manage.MerchantInfoForMDto;
import com.gl365.member.dto.manage.UserInfo2Manage;
import com.gl365.member.dto.merchant.InviteUserReq;
import com.gl365.member.dto.merchant.InviteUserResp;
import com.gl365.member.dto.users.CertificationCommand;
import com.gl365.member.dto.users.LoginCommand;
import com.gl365.member.dto.users.MerchantCommontDto;
import com.gl365.member.dto.users.SaveLoginInfo4MemberCommand;
import com.gl365.member.dto.users.UserDto;
import com.gl365.member.dto.users.UserForMDto;
import com.gl365.member.dto.users.UserForSDto;
import com.gl365.member.dto.users.UserForgotPwdDto;
import com.gl365.member.dto.users.UserInfoDto;
import com.gl365.member.dto.users.UserInfotForSDto;
import com.gl365.member.dto.users.UserRedisDto;
import com.gl365.member.dto.users.UserSumFansDto;
import com.gl365.member.dto.users.UserUpdateDto;
import com.gl365.member.model.ExternalAccount;
import com.gl365.member.model.User;
import com.gl365.member.service.MerchantFavoritesService;
import com.gl365.member.service.RedisService;
import com.gl365.member.service.UserRegisterChannelService;
import com.gl365.member.service.UserService;
import com.gl365.member.service.repo.ValidatorService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/member/user")
public class UserController {

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private UserService userService;

	@Autowired
	private MerchantFavoritesService merchantFavoritesService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private UserRegisterChannelService userRegisterChannelService;

	@Lazy
	@Resource(name = "member-account-registry-producer")
	private OnsProducer creatAccountProducer;

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	/**
	 * 登录
	 * 
	 * @param request
	 * @param command
	 * @return
	 */
	@PostMapping("/login")
	@HystrixCommand(fallbackMethod = "loginFallback")
	public Object login(@RequestBody LoginCommand command) {
		ResultDto<?> rlt = null;
		Long begin = System.currentTimeMillis();
		try {
			LOG.info("member user login begin,param={}", JsonUtils.toJsonString(command));
			rlt = userService.login(command);
		} catch (Exception e) {
			LOG.error("用户登录 > > > 异常：{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long end = System.currentTimeMillis();
		LOG.info("member user login end  rlt={}  time={} ms", JsonUtils.toJsonString(rlt), end - begin);
		return rlt;
	}

	/**
	 * 保存登录信息 （设备、日志）
	 * 
	 * @param command
	 * @return
	 */
	@PostMapping("/saveLoginLog")
	@HystrixCommand(fallbackMethod = "saveLoginLogFallback")
	public Object saveLoginLog(@RequestBody SaveLoginInfo4MemberCommand command) {
		LOG.info("记录登录设备信息> > > 入参：{}", JsonUtils.toJsonString(command));
		try {
			return userService.saveLoinLog(command);
		} catch (Exception e) {
			LOG.info("记录登录设备信息> > > 失败,异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR, null);
		}
	}

	/**
	 * 修改用户
	 * 
	 * @param UserDto
	 * @return
	 */
	@PutMapping("/updateUserByUserId")
	@HystrixCommand(fallbackMethod = "updateUserByUserIdFallback")
	public Object updateUserByUserId(@RequestBody UserUpdateDto userUpdateDto) {
		LOG.info("修改用户 > > > 入参：{}", JsonUtils.toJsonString(userUpdateDto));
		try {
			return userService.updateUserByUserId(userUpdateDto);
		} catch (Exception e) {
			LOG.error("修改用户 > > > 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}
	/**
	 * M端口修改用户状态，主要是应为M端如果将用户状态改为禁用后，需要实现踢人下线的功能
	 * 仅仅提供给M端调用，其它修改是调用上面的  updateUserByUserId  方法
	 * 
	 * @param UserDto
	 * @return
	 */
	@PutMapping("/updateUserByUserIdForM")
	@HystrixCommand(fallbackMethod = "updateUserByUserIdForMFallback")
	public Object updateUserByUserIdForM(@RequestBody UserUpdateDto userUpdateDto) {
		LOG.info("M端修改用户 > > > 入参：{}", JsonUtils.toJsonString(userUpdateDto));
		try {
			return userService.updateUserByUserIdForM(userUpdateDto);
		} catch (Exception e) {
			LOG.error("M端修改用户 > > > 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	/**
	 * 保存用户
	 * 
	 * @param UserDto
	 * @return
	 */
	@PutMapping("/saveUserByUserId")
	@HystrixCommand(fallbackMethod = "saveUserByUserIdFallback")
	public Object saveUserByUserId(@RequestBody UserUpdateDto userUpdateDto) {
		LOG.info("保存用户 > > > 入参：{}", JsonUtils.toJsonString(userUpdateDto));
		try {
			ResultDto<?> result = userService.saveUserByUserId(userUpdateDto);
			if (ResultCodeEnum.System.SUCCESS.getCode().equals(result.getResult())) {
				if (StringUtils.isBlank(userUpdateDto.getUserId())) {
					ResultDto<?> rs = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
					rs.setDescription("注册成功，开户失败");
					return rs;
				}
				LOG.info("账号注册开户 > > > begin：{}", userUpdateDto.getUserId());
				creatAccountProducer.send(userUpdateDto.getUserId());
				LOG.info("账号注册开户 > > > success：");
			}
			return result;

		} catch (Exception e) {
			LOG.error("保存用户 > > > 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	/**
	 * 实名认证
	 * 
	 * @param userDto
	 * @return
	 */
	@PostMapping("/certification")
	@HystrixCommand(fallbackMethod = "certificationFallback")
	public Object certification(@RequestBody CertificationCommand command) {
		try {
			LOG.info("实名认证 > > > 入参：{}", JsonUtils.toJsonString(command));
			if (StringUtils.isBlank(command.getIdCard()) || StringUtils.isBlank(command.getName())) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			String userId = command.getUserId();
			if (!StringUtils.isBlank(userId)) {
				// 实名验证后是否调用手机认证开关
				Boolean flag = true;
				User user = userService.getUserInfoByUserId(userId);
				if (null == user) {
					return new ResultDto<>(ResultCodeEnum.User.NO_MEMBER_INFO_ERROR);
				}
				ResultDto<Boolean> hasIdCard = userService.getUserInfoByIdCard(command.getIdCard());
				if (!ResultCodeEnum.System.SUCCESS.getCode().equals(hasIdCard.getResult())) {
					return hasIdCard;
				}
				if (hasIdCard.getData()) {
					flag = false;
					// 假如已经认证过，校验手机认证，手机认证通过也可以通过
					ResultDto<?> result = phoneValidate(user, command.getIdCard(), command.getName());
					LOG.info("身份证已存在时,手机认证结果 > > > rlt={}", JsonUtils.toJsonString(result));
					if (!ResultCodeEnum.System.SUCCESS.getCode().equals(result.getResult())) {
						return new ResultDto<>(ResultCodeEnum.User.IDCARD_LIMIT_ERROR);
					}
				}

				ResultDto<Boolean> rlt = validatorService.certification(command.getIdCard(), command.getName());
				LOG.info("member实名认证结果   > > > rlt={}", JsonUtils.toJsonString(rlt));
				ResultDto<Boolean> rtData = new ResultDto<>();
				if (!ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())) {
					return rlt;
				}
				if (rlt.getData()) {
					UserUpdateDto updateLogintime = new UserUpdateDto();
					if (0 == user.getAuthStatus()) {
						updateLogintime.setAuthStatus(11000000);
						updateLogintime.setCertType(1);
					}
					updateLogintime.setUserId(userId);
					updateLogintime.setCertNum(command.getIdCard());
					updateLogintime.setRealName(command.getName());

					if (StringUtils.isNotBlank(command.getSex())) {
						if ("男".equals(command.getSex())) {
							updateLogintime.setSex(0);
						} else {
							updateLogintime.setSex(1);
						}
					}
					if (StringUtils.isNotBlank(command.getBirth())) {
						LocalDateTime birthday = LocalDateTime.of(LocalDate.parse(format(command.getBirth())), LocalTime.MIN);
						updateLogintime.setBirthday(birthday);
					}
					if (StringUtils.isNotBlank(command.getAddress())) {
						updateLogintime.setIdAddress(command.getAddress());
					}

					ResultDto<?> userResult = (ResultDto<?>) userService.updateUserByUserId(updateLogintime);
					if (!ResultCodeEnum.System.SUCCESS.getCode().equals(userResult.getResult())) {
						return userResult;
					}
					rtData.setResult(ResultCodeEnum.System.SUCCESS.getCode());
					rtData.setDescription(ResultCodeEnum.System.SUCCESS.getMsg());
					rtData.setData(true);
					if (flag) {
						// 身份认证后调用手机认证,通过写数据库,否则不写
						phoneValidate(user, command.getIdCard(), command.getName());
					}
				} else {
					rtData.setResult(ResultCodeEnum.System.SYSTEM_ERROR.getCode());
					rtData.setDescription(rlt.getDescription());
				}
				return rtData;

			} else {
				return new ResultDto<>(ResultCodeEnum.User.NO_MEMBER_INFO_ERROR);
			}
		} catch (Exception e) {
			LOG.error("实名认证  > > > 异常：{}", e);
			return ResultDto.getErrInfo();
		}
	}

	private String format(String str) {
		String[] s = str.split("-");
		if (s[1].length() < 2) {
			s[1] = "0" + s[1];
		}
		if (s[2].length() < 2) {
			s[2] = "0" + s[2];
		}
		return (s[0] + "-" + s[1] + "-" + s[2]);
	}

	public ResultDto<?> phoneValidate(User user, String idNum, String name) {
		try {
			String mobileNum = user.getMobilePhone();
			LOG.info("手机认证 > > > 入参：mobileNum{},idNum{},name{}", mobileNum, idNum, name);
			/*
			 * String json = validatorService.phoneValidate(mobileNum, idNum,
			 * name); LOG.info("手机认证   > > > json{}", json); Gson gson = new
			 * Gson(); ValidatorGrandDto result = gson.fromJson(json,
			 * ValidatorGrandDto.class);
			 * if(!"0000".equals(result.getResCode())){ LOG.info(
			 * "手机认证失败   > > > result{}", JsonUtils.toJsonString(result));
			 * return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR,
			 * result); } String rt =
			 * result.getData().get(0).getQuotaInfo().getQuotaValue();
			 * if("0,0,0".equals(rt)){ UserUpdateDto updateLogintime = new
			 * UserUpdateDto(); updateLogintime.setUserId(user.getUserId());
			 * updateLogintime.setAuthStatus(11100000); ResultDto<?> userResult
			 * = (ResultDto<?>) userService.updateUserByUserId(updateLogintime);
			 * if(!ResultCodeEnum.System.SUCCESS.getCode().equals(userResult.
			 * getResult())){ LOG.info("手机认证保存数据失败   > > > result{}",
			 * JsonUtils.toJsonString(userResult)); return userResult; }
			 * LOG.info("手机认证成功   > > > result{}",
			 * JsonUtils.toJsonString(result)); return userResult; }else{ String
			 * str = "[null](数据不存在),[-1](手机号码不存在),[0,0,0](身份证件号码验证一致)," +
			 * "[0,0,1](身份证件号码验证一致，姓名验证不一致),[0,1,-1](身份证件号码验证不一致，姓名验证不一致)," +
			 * "[1,-1,-1](身份证件类型验证不一致),[-1,-1,-1](未匹配到指定号码)";
			 * LOG.info("手机认证失败,参数("+ str +")  > > > 结果:{}", rt); return new
			 * ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR); }
			 */
			ResultDto<Boolean> rlt = validatorService.phoneValidate(mobileNum, idNum, name);
			LOG.info("member手机认证结果   > > > rlt={}", JsonUtils.toJsonString(rlt));
			if (!ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())) {
				return rlt;
			}
			if (rlt.getData()) {
				UserUpdateDto updateLogintime = new UserUpdateDto();
				updateLogintime.setUserId(user.getUserId());
				updateLogintime.setAuthStatus(11100000);
				ResultDto<?> userResult = (ResultDto<?>) userService.updateUserByUserId(updateLogintime);
				if (!ResultCodeEnum.System.SUCCESS.getCode().equals(userResult.getResult())) {
					LOG.info("手机认证保存数据失败   > > > result{}", JsonUtils.toJsonString(userResult));
					return userResult;
				}
				LOG.info("手机认证成功   > > > result{}", JsonUtils.toJsonString(userResult));
				return userResult;
			} else {
				LOG.info("手机认证失败  > > > 结果:rlt={}", JsonUtils.toJsonString(rlt));
				return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR, rlt.getDescription(), null);
			}
		} catch (Exception e) {
			LOG.error("手机认证  > > > 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	/**
	 * 我的评论查询
	 * 
	 * @param
	 * @return
	 */
	@GetMapping("/comments/{userId}/{curPage}/{pageSize}")
	@HystrixCommand(fallbackMethod = "queryMyCommentFallback")
	public ResultDto<PageDto<MerchantCommontDto>> queryMyComment(@PathVariable("userId") String userId, @PathVariable("curPage") String curPage, @PathVariable("pageSize") String pageSize) {
		ResultDto<PageDto<MerchantCommontDto>> resultDto = new ResultDto<PageDto<MerchantCommontDto>>();
		try {
			LOG.info("我的评论查询 > > > 入参：userId{}, curPage{}, pageSize{}", userId, curPage, pageSize);
			PageDto<MerchantCommontDto> page = userService.queryMyComment(userId, curPage, pageSize);
			resultDto = new ResultDto<>(ResultCodeEnum.System.SUCCESS, page);
		} catch (Exception e) {
			LOG.error("我的评论查询 > > > 异常：{}", e);
			resultDto = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		return resultDto;
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/info/{userId}")
	@HystrixCommand(fallbackMethod = "getCurUserInfoFallback")
	public Object getCurUserInfo(@PathVariable("userId") String userId) {
		LOG.info("获取当前用户信息 > > > 入参：{}", userId);
		try {
			return userService.getCurUserInfo(userId);
		} catch (Exception e) {
			LOG.error("获取当前用户信息 > > > 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	/**
	 * 根据电话获取用户信息
	 * 
	 * @param mobilePhone
	 * @return
	 */
	@PostMapping("/info/mobilePhone")
	@HystrixCommand(fallbackMethod = "getUserInfoByMobilePhoneFallback")
	public User getUserInfoByMobilePhone(@RequestBody UserDto userDto) {
		LOG.info("根据电话获取用户信息 > > > 入参  mobilePhone:{}", userDto.getMobilePhone());
		User user = new User();
		try {
			user = userService.getUserInfoByMobilePhone(userDto.getMobilePhone());
		} catch (Exception e) {
			LOG.error("根据电话获取用户信息 > > > 异常：{}", e);
			user = null;
		}
		return user;
	}

	/**
	 * 根据userid获取用户信息
	 * 
	 * @param mobilePhone
	 * @return
	 */
	@PostMapping("/info/userId")
	@HystrixCommand(fallbackMethod = "getUserInfoByUserIdFallback")
	public User getUserInfoByUserId(@RequestBody UserDto userDto) {
		LOG.info("根据userid获取用户信息 > > > 入参：userId{}", userDto.getUserId());
		User user = new User();
		try {
			String userId = userDto.getUserId();
			user = userService.getUserInfoByUserId(userId);
		} catch (Exception e) {
			LOG.error("根据userid获取用户信息 > > > 异常：{}", e);
			user = null;
		}
		return user;
	}

	/**
	 * 根据身份证判断用户是否存在
	 * 
	 * @param userDto
	 * @return
	 */
	@PostMapping("/info/idCard")
	@HystrixCommand(fallbackMethod = "getUserInfoByIdCardFallback")
	public ResultDto<Boolean> getUserInfoByIdCard(@RequestParam("cardId") String cardId) {
		LOG.info("根据身份证判断用户是否存在 > > > 入参：cardId{}", cardId);
		try {
			return userService.getUserInfoByIdCard(cardId);
		} catch (Exception e) {
			LOG.error("根据身份证判断用户是否存在 > > > 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	/**
	 * 获取当前当天使用的设备数
	 * 
	 * @param mobilePhone
	 * @return
	 */
	@PostMapping("/device/userId")
	@HystrixCommand(fallbackMethod = "validateClientFallback")
	public ResultDto<?> validateClient(@RequestBody UserDto userDto) {
		LOG.info("获取当前当天使用的设备数 > > > 入参：userId{}", userDto.getUserId());
		try {
			return userService.validateClient(userDto.getUserId());
		} catch (Exception e) {
			LOG.error("获取当前当天使用的设备数 > > > 异常：{}", e);
			return ResultDto.getErrInfo();
		}
	}

	/**
	 * 获取会员信息 提供给支付系统调用
	 * 
	 * @param userDto
	 * @return
	 */
	@PostMapping("/payment/queryUserInfo")
	@HystrixCommand(fallbackMethod = "getMemberInfoFallback")
	public Object getMemberInfo(@RequestBody UserDto userDto) {
		LOG.info("获取会员信息   提供给支付系统调用 ,getMemberInfo begin,param={}", userDto.getUserId());
		Object rlt = null;
		try {
			String userId = userDto.getUserId();
			if (StringUtils.isBlank(userId)) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = userService.getMemberInfo(userId);
		} catch (Exception e) {
			LOG.error("获取会员信息   提供给支付系统调用,getMemberInfo exception,e:{}", e);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("result", ResultCodeEnum.System.SYSTEM_ERROR.getCode());
			map.put("description", e.getMessage());
			rlt = map;
		}
		LOG.info("获取会员信息   提供给支付系统调用,getMemberInfo end,rlt={}", JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	@ApiOperation(value = "根据老用户userId查询绑定后的用户信息")
	@PostMapping("/payment/queryUserInfoByNewUserId")
	@HystrixCommand(fallbackMethod = "queryUserInfoByNewUserIdFallback")
	public Object queryUserRelationByUserId(@RequestBody UserDto userDto) {
		String userId = userDto.getUserId();
		LOG.info("获取会员信息   提供给支付系统调用 ,getMemberInfo begin,param={}", userDto.getUserId());
		Long beginTime = System.currentTimeMillis();
		Object rlt = null;
		try {
			if (StringUtils.isBlank(userId)) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = userService.getMemberInfoByOtherId(userId);
		} catch (Exception e) {
			LOG.error("获取会员信息   提供给支付系统调用,getMemberInfo exception,e:{}", e);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("result", ResultCodeEnum.System.SYSTEM_ERROR.getCode());
			map.put("description", e.getMessage());
			rlt = map;
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("获取会员信息   提供给支付系统调用,getMemberInfo end,rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}

	/**
	 * 获取会员信息 提供给S端调用
	 * 
	 * @param userForSDto
	 * @return
	 */
	@PostMapping("/channel/queryUserInfo")
	@HystrixCommand(fallbackMethod = "getMemberInfoToSFallback")
	public Object getMemberInfoToS(@RequestBody UserForSDto userForSDto) {
		LOG.info("获取会员信息提供给S端调用 userService.getMemberInfoToS begin  reqParam={}" ,JsonUtils.toJsonString(userForSDto));
		Long beginTime = System.currentTimeMillis();
		ResultDto<PageDto<UserInfotForSDto>> rlt = null;
		try {
			if (StringUtils.isBlank(userForSDto.getRecommendAgentT()) || StringUtils.isBlank(userForSDto.getRecommendAgentId())) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			if (null == userForSDto.getCurPage() || userForSDto.getCurPage() < 1) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR);
			}
			rlt = userService.getMemberInfoToS(userForSDto);
		} catch (Exception e) {
			LOG.error("获取会员信息提供给S端调用 userService.getMemberInfoToS exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
			rlt.setDescription(e.getMessage());
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("获取会员信息提供给S端调用 userService.getMemberInfoToS end  rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}

	/**
	 * 根据机构类型和机构id 获取会员个数 提供给S端调用
	 * 
	 * @param userForSDto
	 * @return
	 */
	@PostMapping("/channel/getUserCountToS")
	@HystrixCommand(fallbackMethod = "getMemberCountToSFallback")
	public Object getMemberCountToS(@RequestBody UserForSDto userForSDto) {
		LOG.info("根据机构类型和机构id,获取会员个数 ,提供给S端调用 ,getMemberCountToS begin,param={}", JsonUtils.toJsonString(userForSDto));
		ResultDto<?> rlt = null;
		try {
			String recommendAgentType = userForSDto.getRecommendAgentT();
			List<String> recommendAgentIdList = userForSDto.getRecommendAgentIdList();
			if (null == recommendAgentIdList || StringUtils.isBlank(recommendAgentType)) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			if ((recommendAgentIdList.size() < 1)) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR);
			}

			rlt = userService.getMemberCountToS(userForSDto);
		} catch (Exception e) {
			LOG.error("根据机构类型和机构id,获取会员个数 ,提供给S端调用 ,getMemberCountToS exception  ===> userService.getMemberCountToS exception,e:{}", e);
			ResultDto<?> resultDto = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
			resultDto.setDescription(e.getMessage());
			rlt = resultDto;
		}
		LOG.info("根据机构类型和机构id,获取会员个数 ,提供给S端调用 ,getMemberCountToS end,rlt={}", JsonUtils.toJsonString(rlt));
		return rlt;
	}

	/**
	 * 是否是常用设备
	 * 
	 * @param userId
	 * @return
	 */
	@PostMapping("/device/userId/deviceId")
	@HystrixCommand(fallbackMethod = "isAlwaysDeviceFallback")
	public ResultDto<?> isAlwaysDevice(@RequestBody UserDto userDto) {
		LOG.info("是否是常用设备 > > > 入参：{}", JsonUtils.toJsonString(userDto));
		ResultDto<?> resultDto = new ResultDto<>();
		try {
			resultDto = userService.isAlwaysDevice(userDto.getUserId(), userDto.getDeviceId());
		} catch (Exception e) {
			LOG.error("是否是常用设备 > > > 异常：{}", e);
			resultDto = ResultDto.getErrInfo();
		}
		return resultDto;
	}

	@PostMapping("/save2Redis")
	@HystrixCommand(fallbackMethod = "save2RedisFallback")
	public ResultDto<?> save2Redis(@RequestBody UserRedisDto userRedisDto) {
		LOG.info("保存至redis > > > 入参：{}", JsonUtils.toJsonString(userRedisDto));
		try {
			String key = userRedisDto.getKey();
			String value = userRedisDto.getValue();
			Long liveTime = userRedisDto.getLiveTime();
			if (liveTime == null) {
				liveTime = 1800L;
			}
			redisService.setStringValue(key, value, liveTime);
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		} catch (Exception e) {
			LOG.error("保存至redis> > > 异常：{}", e);
			return ResultDto.getErrInfo();
		}
	}

	@PostMapping("/get2Redis")
	@HystrixCommand(fallbackMethod = "get2RedisFallback")
	public ResultDto<?> get2Redis(@RequestBody UserRedisDto userRedisDto) {
		LOG.info("获取redis值 > > > 入参：{}", JsonUtils.toJsonString(userRedisDto));
		try {
			String key = userRedisDto.getKey();
			String value = redisService.getStringValue(key);
			return new ResultDto<String>(ResultCodeEnum.System.SUCCESS, value);
		} catch (Exception e) {
			LOG.error("获取redis值> > > 异常：{}", e);
			return ResultDto.getErrInfo();
		}
	}

	@PostMapping("/del2Redis")
	@HystrixCommand(fallbackMethod = "del2RedisFallback")
	public ResultDto<?> del2Redis(@RequestBody UserRedisDto userRedisDto) {
		LOG.info("删除redis值 > > > 入参：{}", JsonUtils.toJsonString(userRedisDto));
		try {
			String key = userRedisDto.getKey();
			redisService.del(key);
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		} catch (Exception e) {
			LOG.error("删除redis值> > > 异常：{}", e);
			return ResultDto.getErrInfo();
		}
	}

	@PostMapping("/userInfoList")
	@HystrixCommand(fallbackMethod = "userInfoListFallback")
	public List<UserInfoDto> getUserInfoList(@RequestBody String req) {
		LOG.info("getUserInfoList begin,userIdList={}", req);
		List<String> list = Arrays.asList(req.split(","));
		return userService.queryUserInfoListByUserIds(list);
	}

	@PostMapping("/getUserByuserIdList")
	@HystrixCommand(fallbackMethod = "getUserByuserIdListFallback")
	public List<UserUpdateDto> getUserByuserIdList(@RequestBody List<String> userIds) {
		return userService.getUserByuserIdList(userIds);
	}

	@PostMapping("/creatAccount")
	@HystrixCommand(fallbackMethod = "creatAccountFallback")
	public ResultDto<?> creatAccount(@RequestBody UserUpdateDto userUpdateDto) {
		LOG.info("账号注册开户MQ > > > 入参：{}", userUpdateDto.getUserId());
		try {
			User user = userService.getUserInfoByUserId(userUpdateDto.getUserId());
			if (null == user) {
				return new ResultDto<>(ResultCodeEnum.User.NO_MEMBER_INFO_ERROR);
			}
			String userId = user.getUserId();
			// Map<String, Object> map2Json = new HashMap<String, Object>();
			// map2Json.put("userId", userId);
			// Gson gson = new Gson();
			// String message = gson.toJson(map2Json);
			LOG.debug("账号注册开户 > > > begin：{}", userId);
			creatAccountProducer.send(userId);
			LOG.debug("账号注册开户 > > > success");
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		} catch (Exception e) {
			LOG.error("账号注册开户> > > 异常：{}", e);
			return ResultDto.getErrInfo();
		}
	}

	@PostMapping("/validatePassword")
	@HystrixCommand(fallbackMethod = "validatePasswordFallback")
	public Boolean validatePassword(@RequestBody ValidatePwdReq req) {
		if (null == req || StringUtils.isEmpty(req.getUserId()) || StringUtils.isEmpty(req.getPwd())) {
			LOG.error("validatePassword reqParam is null");
			return false;
		}
		LOG.info("validatePassword begin,req={}", req.toString());
		try {
			User user = userService.getUserInfoByUserId(req.getUserId());
			if (null != user) {
				if (!req.getPwd().equals(user.getPassword())) {
					LOG.info("validatePassword pwd isn't correct,user's pwd={},req's pwd={}", user.getPassword(), req.getPwd());
					return false;
				}
			} else {
				LOG.error("validatePassword getUserInfoByUserId user is null");
				return false;
			}
		} catch (Exception e) {
			LOG.error("validatePassword exception, e:" + e);
			return false;
		}
		LOG.info("validatePassword end,pwd correct");
		return true;
	}

	/**
	 * 获取userIdList
	 * 
	 * @return
	 */
	@PostMapping("/getUserIdByMerchantNo/{merchantNo}/{curPage}/{pageSize}")
	@HystrixCommand(fallbackMethod = "getUserIdByMerchantNoFallback")
	public Object getUserIdByMerchantNo(@PathVariable("merchantNo") String merchantNo, @PathVariable("curPage") Integer curPage, @PathVariable("pageSize") Integer pageSize) {
		return merchantFavoritesService.getUserIdByMerchantNo(merchantNo, curPage, pageSize);
	}

	/**
	 * 获取粉丝列表
	 * 
	 * @return
	 */
	@PostMapping("/invite/sum")
	@HystrixCommand(fallbackMethod = "sumFallback")
	public Object sum(@RequestBody UserSumFansDto user) {
		LOG.info("获取粉丝   sum begin  command={}", JsonUtils.toJsonString(user));
    	Long beginTime = System.currentTimeMillis();
    	Object rlt = null;
		try {
			rlt = userService.sum(user);
		} catch (Exception e) {
			LOG.error("获取粉丝  sum exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("获取粉丝   sum end  rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}

	/**
	 * 查询用户在第三方的注册状态
	 * 
	 * @param userId
	 * @param channelId
	 * @return
	 */
	@GetMapping("/userChannelRegStatus")
	@HystrixCommand(fallbackMethod = "getUserChannelRegStatusFallback")
	public Object getUserChannelRegStatus(@RequestParam("userId") String userId, @RequestParam("channelId") String channelId) {
		return userRegisterChannelService.queryRealNameInfo(userId, channelId);
	}

	@PostMapping("/channelRegisterInfo")
	@HystrixCommand(fallbackMethod = "addChannelRegisterInfoFallback")
	public Object addChannelRegisterInfo(@RequestBody ExternalAccount info) {
		return userRegisterChannelService.addChannelRegisterInfo(info);
	}
	
	@PostMapping("/forgotPassword")
	@HystrixCommand(fallbackMethod = "forgotPasswordFallback")
	public Object forgotPassword(@RequestBody UserForgotPwdDto userForgotPwdDto) {
		LOG.info("忘记密码 forgotPassword begin  param={}", JsonUtils.toJsonString(userForgotPwdDto));
    	Long beginTime = System.currentTimeMillis();
    	ResultDto<?> rlt = null;
		try {
			if (StringUtils.isBlank(userForgotPwdDto.getMobilePhone())
					|| (StringUtils.isBlank(userForgotPwdDto.getPassword())
							&& StringUtils.isBlank(userForgotPwdDto.getIdCard()))
			) {
					return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
				}
			rlt = userService.forgotPassword(userForgotPwdDto);
		} catch (Exception e) {
			LOG.error("忘记密码  userService.forgotPassword exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("忘记密码 forgotPassword end  rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}

	/**
	 * 获取会员信息 提供给M端调用
	 * 
	 * @param userForMDto
	 * @return
	 */
	@PostMapping("/manage/queryUserInfo")
	@HystrixCommand(fallbackMethod = "getMemberInfoToMFallback")
	public Object getMemberInfoToM(@RequestBody UserForMDto userForMDto) {
		LOG.info("获取会员信息提供给M端调用 userService.getMemberInfoToS begin  reqParam={}" ,JsonUtils.toJsonString(userForMDto));
		Long beginTime = System.currentTimeMillis();
		ResultDto<PageDto<UserInfo2Manage>> rlt = null;
		try {
			if (null == userForMDto.getCurPage() || userForMDto.getCurPage() < 1 || null == userForMDto.getPageSize() || userForMDto.getPageSize() > 500 || userForMDto.getPageSize() < 1) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR, null, "页数和页大小格式不正确");
			}
			rlt = userService.getMemberInfoToM(userForMDto);
		} catch (Exception e) {
			LOG.error("获取会员信息提供给M端调用 userService.getMemberInfoToS exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
			rlt.setDescription(e.getMessage());
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("获取会员信息提供给M端调用 userService.getMemberInfoToS end  rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	/**
	 * 获取会员信息详情 提供给M端调用
	 * 
	 * @param userForMDto
	 * @return
	 */
	@PostMapping("/manage/queryUserInfoDetail")
	@HystrixCommand(fallbackMethod = "getMemberInfoToMDetailFallback")
	public Object getMemberInfoToMDetail(@RequestBody UserForMDto userForMDto) {
		LOG.info("获取会员信息详情 提供给M端调用 userService.getMemberInfoToMDetail begin  reqParam={}" ,userForMDto.getUserId());
		Long beginTime = System.currentTimeMillis();
		ResultDto<UserInfo2Manage> rlt = null;
		try {
			rlt = userService.getMemberInfoToMDetail(userForMDto.getUserId());
		} catch (Exception e) {
			LOG.error("获取会员信息详情 提供给M端调用 userService.getMemberInfoToMDetail exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
			rlt.setDescription(e.getMessage());
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("获取会员信息详情 提供给M端调用 userService.getMemberInfoToMDetail end  rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	/**
	 * 根据机构类型获取会员个数,提供给M端调用
	 * 
	 * @param types
	 * @return
	 */
	@PostMapping("/manage/getCountByAgentType")
	@HystrixCommand(fallbackMethod = "getCountByAgentTypeFallback")
	public Object getCountByAgentType(@RequestBody List<String> types) {
		LOG.info("根据机构类型获取会员个数,提供给M端调用 userService.getCountByAgentType begin  reqParam={}" ,JsonUtils.toJsonString(types));
		Long beginTime = System.currentTimeMillis();
		ResultDto<Integer> rlt = null;
		try {
			if (null == types || types.size()< 1) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR,null);
			}
			rlt = userService.getCountByAgentType(types);
		} catch (Exception e) {
			LOG.error("根据机构类型获取会员个数,提供给M端调用 userService.getMemberInfoToS exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
			rlt.setDescription(e.getMessage());
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("根据机构类型获取会员个数,提供给M端调用 userService.getMemberInfoToS end  rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	@ApiOperation(value = "M端会员查询")
	@PostMapping("/manage/queryUserInfoByrealName")
	@HystrixCommand(fallbackMethod = "queryUserInfoByrealNameFallback")
	public Object queryUserInfoByrealName(@RequestBody MerchantInfoForMDto command) {
		LOG.info("M端会员查询 queryUserInfoByrealName begin  command={}", JsonUtils.toJsonString(command));
    	Long beginTime = System.currentTimeMillis();
    	ResultDto<PageDto<User>> rlt = null;
		try {
			rlt = userService.queryUserInfoByrealName(command);
		} catch (Exception e) {
			LOG.error("M端会员查询 queryUserInfoByrealName exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("M端会员查询 queryUserInfoByrealName end  rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	@ApiOperation(value = "B端查询推广粉丝")
	@PostMapping("/merchant/queryInviteUserDto")
	@HystrixCommand(fallbackMethod = "queryInviteUserDtoFallback")
	public Object queryInviteUserDto(@RequestBody InviteUserReq command) {
		LOG.info("B端查询推广粉丝  queryInviteUserDto begin  command={}", JsonUtils.toJsonString(command));
		Long beginTime = System.currentTimeMillis();
		ResultDto<PageDto<InviteUserResp>> rlt = null;
		try {
			if (null == command.getMerchantNo() && null == command.getOperatorName()&& null == command.getRecommendBy()) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR);
			}
			rlt = userService.queryInviteUserDto(command);
		} catch (Exception e) {
			LOG.error("B端查询推广粉丝  queryInviteUserDto exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("B端查询推广粉丝  queryInviteUserDto end  rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	@ApiOperation(value = "M端查询推广粉丝")
	@PostMapping("/manage/queryInviteUserCount")
	@HystrixCommand(fallbackMethod = "queryInviteUserCountFallback")
	public Object queryInviteUserCount(@RequestBody InviteUserCountReq command){
		LOG.info("M端查询推广粉丝  queryInviteUserCount begin command={}", JsonUtils.toJsonString(command));
		Long beginTime = System.currentTimeMillis();
		ResultDto<List<InviteUserCount>> rlt = null;
		try {
			if (null == command.getMerchantNo() || CollectionUtils.isEmpty(command.getRecommendBys())) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR);
			}
			rlt = userService.queryInviteUserCount(command);
		} catch (Exception e) {
			LOG.error("M端查询推广粉丝  queryInviteUserCount exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("M端查询推广粉丝  queryInviteUserCount end  rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	
	public Object queryInviteUserCountFallback(@RequestBody InviteUserCountReq command) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object queryInviteUserDtoFallback(@RequestBody InviteUserReq command) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object queryUserInfoByrealNameFallback(@RequestBody MerchantInfoForMDto command) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object getCountByAgentTypeFallback(@RequestBody List<String> userIds) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object getMemberInfoToMFallback(@RequestBody UserForMDto userForMDto) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object getMemberInfoToMDetailFallback(@RequestBody UserForMDto userForMDto) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object forgotPasswordFallback(@RequestBody UserForgotPwdDto info) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object addChannelRegisterInfoFallback(@RequestBody ExternalAccount info) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getUserChannelRegStatusFallback(@RequestParam("userId") String userId, @RequestParam("channelId") String channelId) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object sumFallback(@RequestBody UserSumFansDto user) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getUserIdByMerchantNoFallback(@PathVariable("merchantNo") String merchantNo, @PathVariable("curPage") Integer curPage, @PathVariable("pageSize") Integer pageSize) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public List<UserUpdateDto> getUserByuserIdListFallback(@RequestBody List<String> userIds) {
		return null;
	}

	public ResultDto<?> creatAccountFallback(@RequestBody UserUpdateDto userUpdateDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public ResultDto<?> save2RedisFallback(@RequestBody UserRedisDto userRedisDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public ResultDto<?> get2RedisFallback(@RequestBody UserRedisDto userRedisDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public ResultDto<?> del2RedisFallback(@RequestBody UserRedisDto userRedisDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object saveUserByUserIdFallback(@RequestBody UserUpdateDto userUpdateDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object saveLoginLogFallback(@RequestBody SaveLoginInfo4MemberCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public ResultDto<?> isAlwaysDeviceFallback(@RequestBody UserDto userDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object updateUserByUserIdFallback(@RequestBody UserUpdateDto userUpdateDto) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object updateUserByUserIdForMFallback(@RequestBody UserUpdateDto userUpdateDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object certificationFallback(@RequestBody CertificationCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public ResultDto<PageDto<MerchantCommontDto>> queryMyCommentFallback(@PathVariable("userId") String userId, @PathVariable("curPage") String curPage, @PathVariable("pageSize") String pageSize) {
		ResultDto<PageDto<MerchantCommontDto>> resultDto = new ResultDto<PageDto<MerchantCommontDto>>();
		resultDto.setResult(ResultCodeEnum.System.SYSTEM_ERROR.getCode());
		return resultDto;
	}

	public Object getUploadPwFallback() {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getCurUserInfoFallback(@PathVariable("userId") String userId) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public ResultDto<?> validateClientFallback(@RequestBody UserDto userDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getMemberInfoFallback(@RequestBody UserDto userDto) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object queryUserInfoByNewUserIdFallback(@RequestBody UserDto userDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getMemberInfoToSFallback(@RequestBody UserForSDto userForSDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getMemberCountToSFallback(@RequestBody UserForSDto userForSDto) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public User getUserInfoByMobilePhoneFallback(@RequestBody UserDto userDto) {
		return null;
	}

	public User getUserInfoByUserIdFallback(@RequestBody UserDto userDto) {
		return null;
	}

	public ResultDto<Boolean> getUserInfoByIdCardFallback(@RequestParam("cardId") String cardId) {
		return new ResultDto<>(ResultCodeEnum.System.SYSTEM_TIME_OUT);
	}

	public List<UserInfoDto> userInfoListFallback(@RequestBody String req) {
		return null;
	}

	public Boolean validatePasswordFallback(@RequestBody ValidatePwdReq req) {
		return null;
	}

	public Object loginFallback(@RequestBody LoginCommand command) {
		return new ResultDto<>(ResultCodeEnum.System.SYSTEM_TIME_OUT);
	}
}
