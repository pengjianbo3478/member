package com.gl365.member.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.aliyun.ons.OnsProducer;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.users.UserRegistReq;
import com.gl365.member.dto.users.relation.CreateUserReq;
import com.gl365.member.dto.users.relation.GetPayOrganIdReq;
import com.gl365.member.dto.users.relation.GetPhotoReq;
import com.gl365.member.dto.users.relation.UserPhotoAndName;
import com.gl365.member.model.UserRelation;
import com.gl365.member.service.UserRelationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.ApiOperation;

/**
 * < 用户合并关系Controller >
 * 
 * @author dfs_508 2017年9月21日 上午10:58:09
 * @Since 1.0
 */
@RestController
@RequestMapping("/member/userRelation")
public class UserRelationController {

	@Autowired
	private UserRelationService userRelationService;

	@Lazy
	@Resource(name = "member-account-registry-producer")
	private OnsProducer creatAccountProducer;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserRelationController.class);
	
	@ApiOperation(value = "根据用户userId查询会员关联的其它userId")
	@PostMapping("/queryUserRelationByUserId/{userId}")
	@HystrixCommand(fallbackMethod = "queryUserRelationByUserIdFallback")
	public ResultDto<Set<String>> queryUserRelationByUserId(@PathVariable("userId") String userId) {
		LOG.info("queryUserRelationByUserId begin userId={}", userId);
    	Long beginTime = System.currentTimeMillis();
    	ResultDto<Set<String>> rlt = null;
		try {
			if (StringUtils.isBlank(userId)){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = userRelationService.queryUserRelationByUserId(userId);
		} catch (Exception e) {
			LOG.error("queryUserRelationByUserId exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("queryUserRelationByUserId end rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	@ApiOperation(value = "根据第三方支付Id创建用户,开乐豆账户并返回用户userId")
	@PostMapping("/createUserByPayOrganId")
	@HystrixCommand(fallbackMethod = "createUserByPayOrganIdFallback")
	public ResultDto<Map<String, Object>> createUserByPayOrganId(@RequestBody CreateUserReq createUserReq) {
		LOG.info("createUserByPayOrganId begin userId={}", JsonUtils.toJsonString(createUserReq));
    	Long beginTime = System.currentTimeMillis();
    	ResultDto<Map<String, Object>> rlt = null;
		try {
			if (StringUtils.isBlank(createUserReq.getPayOrganId()) || StringUtils.isBlank(createUserReq.getChannel())){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = userRelationService.createUserByPayOrganId(createUserReq);
			if (ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())) {
				String userId = (String) rlt.getData().get("userId");
				Boolean isSend = (Boolean) rlt.getData().get("isSend");
				if (Boolean.TRUE.equals(isSend)) {
					try {
						LOG.info("账号注册开户 > > > begin：{}", userId);
						creatAccountProducer.send(userId);
						LOG.info("账号注册开户 > > > success：");
					} catch (Exception e) {
						LOG.info("账号注册开户 exception,e:{}",e);
						return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR,"乐豆账户开户失败",null);
					}
				}
				rlt.getData().remove("isSend");
			}
		} catch (Exception e) {
			LOG.error("createUserByPayOrganId exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("createUserByPayOrganId end rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	@ApiOperation(value = "引导用户注册合并用户")
	@PostMapping("/registPayOrgan")
	@HystrixCommand(fallbackMethod = "registPayOrganFallback")
	public ResultDto<?> registPayOrgan(@RequestBody UserRegistReq req) {
		LOG.info("registPayOrgan begin param={}", JsonUtils.toJsonString(req));
    	ResultDto<?> rlt = null;
		try {
			rlt = userRelationService.registPayOrgan(req);
		} catch (Exception e) {
			LOG.error("registPayOrgan exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		LOG.info("registPayOrgan end rlt={}",JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	@ApiOperation(value = "根据第三方支付id获取关联关系")
	@PostMapping("/getUserRelationByPayOrganId")
	@HystrixCommand(fallbackMethod = "getUserRelationByPayOrganIdFallback")
	public List<UserRelation> getUserRelationByPayOrganId(@RequestBody List<String> payOrganIds) {
		LOG.info("getUserRelationByPayOrganId begin payOrganId={}", JsonUtils.toJsonString(payOrganIds));
		List<UserRelation> rlt = null;
		try {
			rlt = userRelationService.getUserRelationByPayOrganId(payOrganIds);
		} catch (Exception e) {
			LOG.error("getUserRelationByPayOrganId exception,e:{}",e);
			rlt = new ArrayList<UserRelation>();
		}
		LOG.info("getUserRelationByPayOrganId end rlt={}",JsonUtils.toJsonString(rlt));
		return rlt;
	}

	@ApiOperation(value = "根据用户Id获取关联的第三方头像和名称")
	@PostMapping("/getUserInfoByuserId")
	@HystrixCommand(fallbackMethod = "getUserInfoByuserIdFallback")
	public List<UserRelation> getUserInfoByuserId(@RequestBody GetPhotoReq getPhotoReq) {
		LOG.info("getUserInfoByuserId begin userId={},channel={}", JsonUtils.toJsonString(getPhotoReq));
		Long beginTime = System.currentTimeMillis();
		List<UserRelation> rlt = null;
		try {
			rlt = userRelationService.getUserInfoByUserId(getPhotoReq);
		} catch (Exception e) {
			LOG.error("getUserInfoByuserId exception,e:{}",e);
			rlt = new ArrayList<UserRelation>();
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("getUserInfoByuserId end rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	@ApiOperation(value = "根据用户Id获取头像和名称")
	@PostMapping("/getUserPhotoAndName")
	@HystrixCommand(fallbackMethod = "getUserPhotoAndNameFallback")
	public List<UserPhotoAndName> getUserPhotoAndName(@RequestBody List<String> userIds) {
		LOG.info("getUserPhotoAndName begin userIds={}", JsonUtils.toJsonString(userIds));
		Long beginTime = System.currentTimeMillis();
		List<UserPhotoAndName> rlt = null;
		try {
			if(!CollectionUtils.isEmpty(userIds)){
				rlt = userRelationService.getUserPhotoAndName(userIds);
			}else{
				rlt = new ArrayList<>();
			}
		} catch (Exception e) {
			LOG.error("getUserPhotoAndName exception,e:{}",e);
			rlt = new ArrayList<>();
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("getUserPhotoAndName end rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}

	@ApiOperation(value = "根据userId获取第三方支付id")
	@PostMapping("/getPayOrganIdByUserId")
	@HystrixCommand(fallbackMethod = "getPayOrganIdByUserIdFallback")
	public ResultDto<String> getPayOrganIdByUserId(@RequestBody GetPayOrganIdReq req) {
		LOG.info("getPayOrganIdByUserId begin param={}", JsonUtils.toJsonString(req));
		Long beginTime = System.currentTimeMillis();
		ResultDto<String> rlt = null;
		try {
			if (StringUtils.isBlank(req.getUserId()) || StringUtils.isBlank(req.getChannel())){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = userRelationService.getPayOrganIdByUserId(req.getUserId(),req.getChannel());
		} catch (Exception e) {
			LOG.error("getPayOrganIdByUserId exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("getPayOrganIdByUserId end rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	
	
	public ResultDto<String> getPayOrganIdByUserIdFallback(@RequestBody GetPayOrganIdReq req) {
		return new ResultDto<>(ResultCodeEnum.System.SYSTEM_TIME_OUT);
	}
	public ResultDto<Set<String>> queryUserRelationByUserIdFallback(@PathVariable("userId") String userId) {
		return new ResultDto<>(ResultCodeEnum.System.SYSTEM_TIME_OUT);
	}
	public ResultDto<Map<String, Object>> createUserByPayOrganIdFallback(@RequestBody CreateUserReq createUserReq) {
		return new ResultDto<>(ResultCodeEnum.System.SYSTEM_TIME_OUT);
	}
	public ResultDto<?> registPayOrganFallback(@RequestBody UserRegistReq req) {
		return new ResultDto<>(ResultCodeEnum.System.SYSTEM_TIME_OUT);
	}
	public List<UserRelation> getUserRelationByPayOrganIdFallback(@RequestBody List<String> payOrganIds) {
		return new ArrayList<>();
	}
	public List<UserRelation> getUserInfoByuserIdFallback(@RequestBody GetPhotoReq getPhotoReq) {
		return new ArrayList<>();
	}
	public List<UserPhotoAndName> getUserPhotoAndNameFallback(@RequestBody List<String> userIds) {
		return new ArrayList<>();
	}

}
