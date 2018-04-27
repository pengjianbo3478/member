package com.gl365.member.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gl365.member.common.Constant;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.MaskUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.account.req.ActBalanceInfoReq;
import com.gl365.member.dto.account.req.MergeAccountReq;
import com.gl365.member.dto.account.rlt.ActBalance;
import com.gl365.member.dto.account.rlt.ActServiceRlt;
import com.gl365.member.dto.merchant.command.RegistAddOperatorDto;
import com.gl365.member.dto.users.MerchantOperatorDto;
import com.gl365.member.dto.users.UserRegistReq;
import com.gl365.member.dto.users.relation.CreateUserReq;
import com.gl365.member.dto.users.relation.GetPhotoReq;
import com.gl365.member.dto.users.relation.UserPhotoAndName;
import com.gl365.member.mapper.UserMapper;
import com.gl365.member.mapper.UserRelationMapper;
import com.gl365.member.model.User;
import com.gl365.member.model.UserRelation;
import com.gl365.member.service.UserRelationService;
import com.gl365.member.service.UserService;
import com.gl365.member.service.repo.AccountService;
import com.gl365.member.service.repo.MerchantInfoServiceRepo;
import com.gl365.member.web.SmsController;

@Service
public class UserRelationServiceImpl implements UserRelationService {
	
	@Autowired
	private UserRelationMapper userRelationMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private MerchantInfoServiceRepo merchantInfoServiceRepo;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private SmsController smsController;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserRelationServiceImpl.class);
	
	@Override
	public ResultDto<Set<String>> queryUserRelationByUserId(String userId) {
		Set<String> set = new HashSet<>();
		List<UserRelation> userRelations = userRelationMapper.selectByUserId(userId);
		if (userRelations == null || userRelations.size() < 1) {
			User user = userMapper.selectByPrimaryKey(userId);
			if (null != user) {
				set.add(user.getUserId());
			}
		} else {
			for (UserRelation userRelation : userRelations) {
				if(StringUtils.isNotBlank(userRelation.getOldUserId())){
					set.add(userRelation.getOldUserId());
				}
				if(StringUtils.isNotBlank(userRelation.getNewUserId())){
					set.add(userRelation.getNewUserId());
				}
			}
		}
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, set);
	}
	
	@Override
	@Transactional
	public ResultDto<Map<String, Object>> createUserByPayOrganId(CreateUserReq createUserReq) {
		//每次更新微信头像和昵称
		String payOrganId = createUserReq.getPayOrganId();
		if(StringUtils.isNotBlank(createUserReq.getNickName()) || StringUtils.isNotBlank(createUserReq.getPhoto())){
			UserRelation record = new UserRelation();
			record.setPayOrganId(payOrganId);
			record.setNickName(createUserReq.getNickName());
			record.setPhoto(createUserReq.getPhoto());
			record.setModifyTime(LocalDateTime.now());
			userRelationMapper.updateByPrimaryKeySelective(record);
		}
		
		String nickName = null;
		Integer registerType = null;
		if("wx".equals(createUserReq.getChannel())){
			nickName= "微信注册用户";
			registerType= 6;
		}else if("zfb".equals(createUserReq.getChannel())){
			nickName= "支付宝注册用户";
			registerType= 7;
		}else{
			return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR);
		}
		
		BigDecimal amount = BigDecimal.valueOf(0);
		Integer bindFlag = 0;
		Boolean isSend = false;
		String userId = null;
		UserRelation userRelation = userRelationMapper.selectByPayOrganId(payOrganId);
		if(null != userRelation){
			//关系表存在直接查询有效的userId
			userId = userRelation.getNewUserId();
			if(StringUtils.isNotBlank(userRelation.getOldUserId())){
				userId = userRelation.getOldUserId();
				bindFlag = 1;
			}else{
				//以最新交易的商家为依据填入机构id和类型以及店长推荐人等
				User user = new User();
				user.setUserId(userId);
				user.setModifyTime(LocalDateTime.now());
				user = buildUser(user, createUserReq);
				int num = userMapper.updateByPrimaryKeySelective(user);
				LOG.info("createUserByPayOrganId Update the latest merchant information param={},rlt={}", JsonUtils.toJsonString(user), num);
			}
			ActBalanceInfoReq req = new ActBalanceInfoReq();
			req.setUserId(userId);
			req.setAgentId(Constant.hcAgentID);
			ActBalance rlt = accountService.queryAccountBalanceInfo(req);
			LOG.info("accountService.queryAccountBalanceInfo success rlt:{}", JsonUtils.toJsonString(rlt));
			if (!Constant.actRespSuccess.equals(rlt.getResultCode())) {
				return new ResultDto<>(rlt.getResultCode(),rlt.getResultDesc(),null);
			}
			amount = rlt.getBalance();
		}else{
			//关系表不存在新建关联关系
			ResultDto<Map<String, Object>> result = createUserAndRelation(createUserReq, nickName, registerType);
			if(!ResultCodeEnum.System.SUCCESS.getCode().equals(result.getResult())){
				return result;
			}
			userId = (String) result.getData().get("userId");
			//是否发送开户MQ
			isSend = true;
		}
		
		//返回绑定手机
		String mobilePhone = null;
		if(bindFlag.intValue() == 1){
			User user = userMapper.selectByPrimaryKey(userId);
			if(user != null){
				mobilePhone = user.getMobilePhone();
			}
		}
		
		Map<String, Object> rltMap = new HashMap<>();
		rltMap.put("userId", userId);
		rltMap.put("amount", amount);//乐豆余额
		rltMap.put("bindFlag", bindFlag);//是否存在绑定关系
		rltMap.put("isSend", isSend);//是否发送开户MQ
		rltMap.put("mobilePhone", mobilePhone);//绑定的手机号
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, rltMap);
	}
	
	private User buildUser(User user, CreateUserReq createUserReq) {
		if (StringUtils.isNotBlank(createUserReq.getRecommendAgentType())
				&& StringUtils.isNotBlank(createUserReq.getRecommendAgentId())) {
			user.setRecommendAgentType(createUserReq.getRecommendAgentType());
			user.setRecommendAgentId(createUserReq.getRecommendAgentId());
			if (StringUtils.isNotBlank(createUserReq.getRecommendBy())) {
				user.setRecommendBy(createUserReq.getRecommendBy());
				if (StringUtils.isNotBlank(createUserReq.getRecommendShopManager())) {
					user.setRecommendShopManager(createUserReq.getRecommendShopManager());
				} else {
					//判断是否有店长
					ResultDto<MerchantOperatorDto> result = merchantInfoServiceRepo.queryOperatorByUserId(createUserReq.getRecommendBy());
					LOG.info("merchantInfoServiceRepo.queryOperatorByUserId userId={},rlt={}", createUserReq.getRecommendBy(), JsonUtils.toJsonString(result));
					if(ResultCodeEnum.System.SUCCESS.getCode().equals(result.getResult()) && null != result.getData() && StringUtils.isNotBlank(result.getData().getUserId())){
						user.setRecommendShopManager(result.getData().getUserId());
					}
				}
			}
		}
		return user;
	}
	
	@Transactional
	private ResultDto<Map<String, Object>> createUserAndRelation(CreateUserReq createUserReq, String nickName, Integer registerType) {
		// 创建新用户
		String userId = UUID.randomUUID().toString().replace("-", "");
		try {
			User user = new User();
			user.setUserId(userId);
			user.setRegisterTime(LocalDateTime.now());
			user.setModifyTime(LocalDateTime.now());
			user.setAuthStatus(0);
			user.setStatus(1);
			user.setSex(2);
			user.setLevel(0);
			user.setAuthStatus(0);
			user.setRecommendAgentType("0");
			user.setRecommendAgentId("00");
			user.setEnableHappycoin(true);
			user.setAccountProtect(true);
			user.setNickName(nickName);
			user.setRegisterType(registerType);

			user = buildUser(user, createUserReq);

			int count = userMapper.insertSelective(user);
			// 如果超过则开户
			if (count < 1) {
				return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR, "用户创建失败", null);
			}
			// 创建关联关系
			UserRelation record = new UserRelation();
			record.setPayOrganId(createUserReq.getPayOrganId());
			record.setNewUserId(userId);
			record.setChannel(createUserReq.getChannel());
			record.setStatus(0);
			record.setNickName(createUserReq.getNickName());
			record.setPhoto(createUserReq.getPhoto());
			record.setCreateTime(LocalDateTime.now());
			record.setModifyTime(LocalDateTime.now());
			
			int num = userRelationMapper.insertSelective(record);
			if (num < 1) {
				return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR, "用户创建关联关系失败", null);
			}
		} catch (DuplicateKeyException e) {
			//防止微信重复提交产生脏数据
			LOG.info("createUserAndRelation commint again PayOrganId={}", createUserReq.getPayOrganId());
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_TIME_OUT);
		}
		
		Map<String, Object> rltMap = new HashMap<>();
		rltMap.put("userId", userId);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, rltMap);
	}
	
	@Override
	@Transactional
	public ResultDto<?> registPayOrgan(UserRegistReq userRegistReq) {
		String userId = userRegistReq.getUserId();
		String mobilePhone = userRegistReq.getMobilePhone();
		String channel = userRegistReq.getChannel();
		String active = userRegistReq.getActive();
		String activeId = userRegistReq.getActiveId();
		boolean isChinaPhoneLegal = isChinaPhoneLegal(mobilePhone);
		if(!isChinaPhoneLegal){
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR, "手机号码不规范", null);
		}
		// 根据userId获取关联关系
		UserRelation userRelation = userRelationMapper.selectByNewUserId(userId);
		if (null == userRelation) {
			LOG.info("registPayOrgan not found data param userId={}", userId);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_DATA_EXECEPTION);
		}
		if (StringUtils.isNotBlank(userRelation.getOldUserId())) {
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR, "系统存在绑定用户", null);
		}
		String authStatus = "0";
		String newUserId = userRelation.getNewUserId();
		User oldUser = userMapper.getUserInfoByMobilePhone(mobilePhone);
		String oldUserId = null;
		String fromUserId = null;
		String toUserId = null;
		if (null == oldUser) {
			//不存在用户,直接赋值手机
			User record = new User();
			record.setUserId(newUserId);
			record.setMobilePhone(mobilePhone);
			record.setNickName(MaskUtils.mobileMask(mobilePhone));
			record.setModifyTime(LocalDateTime.now());
			if("00".equals(active)){
				record.setRegisterType(2);
				record.setActiveId(activeId);
			}
			int count = userMapper.updateByPrimaryKeySelective(record);
			if (count > 0) {
				oldUserId = newUserId;
				LOG.info("registPayOrgan update newUserId={},count={}", newUserId, count);
				
				fromUserId = newUserId;
				toUserId = newUserId;
				//C端注册完成后自动新增为离职员工 
				RegistAddOperatorDto command = new RegistAddOperatorDto();
				command.setUserId(newUserId);
				command.setTelphone(mobilePhone);
				ResultDto<?> rlt = merchantInfoServiceRepo.saveOperatorForRegister(command);
				LOG.info("C端注册后直接新增离线员工  \n param={},rlt={}",JsonUtils.toJsonString(command),JsonUtils.toJsonString(rlt));
				//新用户发密码短信
				smsController.bindMobileSendSms(mobilePhone);
			}else{
				return new ResultDto<>(ResultCodeEnum.System.SYSTEM_DATA_EXECEPTION);
			}
		} else {
			//一个手机一个渠道只能绑定一个用户
			List<String> oldUserIds = new ArrayList<>();
			oldUserIds.add(oldUser.getUserId());
			List<UserRelation> userRelations = userRelationMapper.getUserRelationsByOldId(oldUserIds);
			for (UserRelation info : userRelations) {
				if(info.getChannel().equals(channel)){
					return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR, "手机号已绑定,请使用其它号码", null);
				}
			}
			
			//存在用户,禁止之前产生的用户,合并乐豆账户
			if (Integer.valueOf(3).equals(oldUser.getStatus())) {
				return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR, "该手机号码已被禁用,请使用其它号码绑定", null);
			}
			if(!Integer.valueOf(0).equals(oldUser.getAuthStatus())){
				authStatus = "1";
			}
			User record = new User();
			record.setUserId(newUserId);
			record.setStatus(3);//将第三方产生的user记录注销
			record.setModifyTime(LocalDateTime.now());
			int count = userMapper.updateByPrimaryKeySelective(record);
			if (count > 0) {
				oldUserId = oldUser.getUserId();
				fromUserId = newUserId;
				toUserId = oldUserId;
				LOG.info("registPayOrgan update newUserId={},count={}", newUserId, count);
			}else{
				return new ResultDto<>(ResultCodeEnum.System.SYSTEM_DATA_EXECEPTION);
			}
		}
		UserRelation relation = new UserRelation();
		relation.setPayOrganId(userRelation.getPayOrganId());
		relation.setOldUserId(oldUserId);
		relation.setModifyTime(LocalDateTime.now());
		int num = userRelationMapper.updateByPrimaryKeySelective(relation);
		if (num > 0) {
			LOG.info("registPayOrgan update newUserId={},count={},num={}", newUserId, num);
		}
		BigDecimal amount = BigDecimal.valueOf(0);
		//调用合并接口
		if (StringUtils.isNotBlank(fromUserId) && StringUtils.isNotBlank(toUserId)) {
			MergeAccountReq req = new MergeAccountReq();
			req.setAgendId(Constant.hcAgentID);
			req.setFromUserId(fromUserId);
			req.setToUserId(toUserId);
			ActServiceRlt rlt = accountService.mergeAccount(req);
			LOG.info("accountService.mergeAccount success rlt:{}", JsonUtils.toJsonString(rlt));
			if (!Constant.actRespSuccess.equals(rlt.getResultCode())) {
				return new ResultDto<>(rlt.getResultCode(), rlt.getResultDesc(), null);
			}
		}
		
		ActBalanceInfoReq param = new ActBalanceInfoReq();
		param.setUserId(oldUserId);
		param.setAgentId(Constant.hcAgentID);
		LOG.info("accountService.queryAccountBalanceInfo param:{}", JsonUtils.toJsonString(param));
		ActBalance actBalance = accountService.queryAccountBalanceInfo(param);
		LOG.info("accountService.queryAccountBalanceInfo success rlt:{}", JsonUtils.toJsonString(actBalance));
		if (!Constant.actRespSuccess.equals(actBalance.getResultCode())) {
			return new ResultDto<>(actBalance.getResultCode(),actBalance.getResultDesc(),null);
		}
		amount = actBalance.getBalance();
		
		Map<String, Object> rltMap = new HashMap<>();
		rltMap.put("amount", amount);//乐豆余额
		rltMap.put("authStatus", authStatus);//实名状态
		rltMap.put("userId", oldUserId);//userId
		rltMap.put("payOrganId", userRelation.getPayOrganId());//第三方id
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS,rltMap);
	}
	/**
	 * 手机号码匹配格式
	 */
	private boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^1(3|4|5|7|8)\\d{9}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	@Override
	public List<UserRelation> getUserRelationByPayOrganId(List<String> payOrganIds) {
		List<UserRelation> result = new ArrayList<>();
		if(payOrganIds == null || payOrganIds.size()<1){
			return result;
		}
		List<UserRelation> list = userRelationMapper.getUserRelationByPayOrganId(payOrganIds);
		if(list == null || list.size()<1){
			return result;
		}
		return list;
	}

	@Override
	public List<UserRelation> getUserInfoByUserId(GetPhotoReq getPhotoReq) {
		List<UserRelation> rlt = new ArrayList<>();
		
		List<String> appUserIds = getPhotoReq.getAppUserIds();
		if(null != appUserIds && (!appUserIds.isEmpty())){
			List<User> appUser = userMapper.getUserByuserIdList(appUserIds);
			if(null != appUser && (!appUser.isEmpty())){
				for (User user : appUser) {
					UserRelation userRelation = new UserRelation();
					userRelation.setOldUserId(user.getUserId());
					userRelation.setNickName(user.getNickName());
					userRelation.setPhoto(userService.addImagePrefix(user.getPhoto()));
					rlt.add(userRelation);
				}
			}
		}
		
		List<String> wxUserIds = getPhotoReq.getWxUserIds();
		if(null != wxUserIds && (!wxUserIds.isEmpty())){
			for (String string : wxUserIds) {
				UserRelation user = userRelationMapper.selectByUserIdAndChannel(string, getPhotoReq.getChannel());
				if(user != null){
					UserRelation userRelation = new UserRelation();
					userRelation.setOldUserId(string);
					userRelation.setNickName(user.getNickName());
					userRelation.setPhoto(user.getPhoto());
					rlt.add(userRelation);
				}
			}
		}
		
		return rlt;
	}

	@Override
	public List<UserPhotoAndName> getUserPhotoAndName(List<String> uids) {
		List<UserPhotoAndName> rlt = new ArrayList<>();
		rlt = buildAppUrlAndName(rlt,uids);
		rlt = buildWxUrlAndName(rlt,uids);
		return rlt;
	}

	// 组装 app头像
	private List<UserPhotoAndName> buildAppUrlAndName(List<UserPhotoAndName> rlt, List<String> uids) {
		List<User> appUser = userMapper.getUserByuserIdList(uids);
		if (!CollectionUtils.isEmpty(appUser)) {
			Map<String, User> userMap = appUser.stream()
					.collect(Collectors.toMap(User::getUserId, Function.identity()));
			for (String id : uids) {
				UserPhotoAndName source = new UserPhotoAndName();
				source.setUserId(id);
				if (userMap.containsKey(id)) {
					User user = userMap.get(id);
					source.setRealName(user.getRealName());
					source.setAppNickName(user.getNickName());
					source.setAppPhoto(userService.addImagePrefix(user.getPhoto()));
				}
				rlt.add(source);
			}
		}
		return rlt;
	}

	// 组装 wx头像
	private List<UserPhotoAndName> buildWxUrlAndName(List<UserPhotoAndName> rlt, List<String> uids) {
		Map<String, Object> map = new HashMap<>();
		map.put("channel", "wx");
		map.put("userIds", uids);
		List<UserRelation> wxUser = userRelationMapper.getUserPhotoAndName(map);
		for (UserPhotoAndName user : rlt) {
			for (UserRelation userRelation : wxUser) {
				if(user.getUserId().equals(userRelation.getOldUserId()) || user.getUserId().equals(userRelation.getNewUserId())){
					user.setWxNickName(userRelation.getNickName());
					user.setWxPhoto(userRelation.getPhoto());
					break;
				}
			}
		}
		return rlt;
	}

	@Override
	public ResultDto<String> getPayOrganIdByUserId(String userId, String channel) {
		UserRelation user = userRelationMapper.selectByUserIdAndChannel(userId,channel);
		if(user != null){
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, user.getPayOrganId());
		}
		return new ResultDto<>(ResultCodeEnum.User.NO_MEMBER_INFO_ERROR);
	}
	
}
