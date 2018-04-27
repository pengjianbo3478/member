package com.gl365.member.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ListUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.PageDto;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.VerifySMSReq;
import com.gl365.member.dto.manage.InviteUserCount;
import com.gl365.member.dto.manage.InviteUserCountReq;
import com.gl365.member.dto.manage.MerchantInfoForMDto;
import com.gl365.member.dto.manage.UserInfo2Manage;
import com.gl365.member.dto.merchant.InviteUserReq;
import com.gl365.member.dto.merchant.InviteUserResp;
import com.gl365.member.dto.merchant.MerchantInfo2Pay;
import com.gl365.member.dto.mq.push.PushMQ;
import com.gl365.member.dto.users.LoginCommand;
import com.gl365.member.dto.users.MerchantCommontDto;
import com.gl365.member.dto.users.SaveLoginInfo4MemberCommand;
import com.gl365.member.dto.users.UserForMDto;
import com.gl365.member.dto.users.UserForSDto;
import com.gl365.member.dto.users.UserForgotPwdDto;
import com.gl365.member.dto.users.UserInfoDto;
import com.gl365.member.dto.users.UserInfotForSDto;
import com.gl365.member.dto.users.UserRltForSDto;
import com.gl365.member.dto.users.UserSumFansDto;
import com.gl365.member.dto.users.UserUpdateDto;
import com.gl365.member.mapper.CommentLabelsMapper;
import com.gl365.member.mapper.CommentMapper;
import com.gl365.member.mapper.LoginLogMapper;
import com.gl365.member.mapper.UserDeviceMapper;
import com.gl365.member.mapper.UserMapper;
import com.gl365.member.mapper.UserRelationMapper;
import com.gl365.member.model.Comment;
import com.gl365.member.model.CommentLabels;
import com.gl365.member.model.LoginLog;
import com.gl365.member.model.User;
import com.gl365.member.model.UserDevice;
import com.gl365.member.model.UserRelation;
import com.gl365.member.mq.producer.JPushProducer;
import com.gl365.member.service.RedisService;
import com.gl365.member.service.UserService;
import com.gl365.member.service.repo.CfrontServiceRepo;
import com.gl365.member.service.repo.MerchantInfoServiceRepo;
import com.gl365.member.web.SmsController;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final String PASSWORD_ERR_REDIS_PREFIX = "PASSWORD_validate";// 密码错误key前缀
	private static final Integer Max_ERROR_COUNT = 5;// 密码最大错误次数
	private static final String RESULT_SUCCESS_CODE = ResultCodeEnum.System.SUCCESS.getCode();// 成功返回码

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private CommentLabelsMapper commentLabelsMapper;
	@Autowired
	private LoginLogMapper loginLogMapper;
	@Autowired
	private UserDeviceMapper userDeviceMapper;
	@Autowired
	private SmsController smsController;
	@Autowired
	private RedisService redisService;
	@Autowired
	JPushProducer jPushProducer;
	@Autowired
	CfrontServiceRepo cfrontServiceRepo;
	@Autowired
	MerchantInfoServiceRepo merchantInfoServiceRepo;
	@Autowired
	private UserRelationMapper userRelationMapper;

	@Value("${member.image.prefix}")
	private String urlPrefix;
	
	@Value("${member.user.uncheckDevice}")
	private String uncheckDevice;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Object updateUserByUserId(UserUpdateDto userUpdateDto) {
		User target = new User();
		BeanUtils.copyProperties(userUpdateDto, target);
		target.setPhoto(removeImagePrefix(userUpdateDto.getPhoto()));
		userMapper.updateByPrimaryKeySelective(target);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, null);

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Object updateUserByUserIdForM(UserUpdateDto userUpdateDto) {
		User target = new User();
		BeanUtils.copyProperties(userUpdateDto, target);
		target.setPhoto(removeImagePrefix(userUpdateDto.getPhoto()));
		int num = userMapper.updateByPrimaryKeySelective(target);
		//当状态为禁用时推极光
		LOG.info("M端禁用用户清除token bengin  param={},num={}",JsonUtils.toJsonString(target),num);
		if (null != target.getStatus() && 3 == target.getStatus() && StringUtils.isNotBlank(target.getUserId()) && num > 0) {
			UserForgotPwdDto command = new UserForgotPwdDto();
			command.setToken(target.getUserId());
			ResultDto<?> rlt = cfrontServiceRepo.clearToken(command);
			if(!ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())){
				LOG.info("member user status cancel clearToken error");
				return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR, null);
			}
			LOG.info("member user status cancel clearToken success");
		}
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, null);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ResultDto<?> saveUserByUserId(UserUpdateDto userUpdateDto) {
		User target = new User();
		BeanUtils.copyProperties(userUpdateDto, target);
		target.setPhoto(removeImagePrefix(userUpdateDto.getPhoto()));
		userMapper.insertSelective(target);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, null);
	}

	// ********************C端登录********
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ResultDto<?> login(LoginCommand command) {
		// 1.身份验证不通过 密码 短信验证
		User user = userMapper.getUserInfoByMobilePhone(command.getUsername());
		if (user == null) {
			return new ResultDto<>(ResultCodeEnum.User.NO_USER_INFO_ERROR);
		}

		String verificationCode = command.getVerificationCode();
		// 2. 密码 短信验证
		ResultDto<?> validateUserRt = validateUser(command, user, verificationCode);
		if (!RESULT_SUCCESS_CODE.equals(validateUserRt.getResult())) {
			return validateUserRt;
		}

		// 3.用户状态校验
		ResultDto<?> validateRt = validateUserStatus(user, command);
		if (!RESULT_SUCCESS_CODE.equals(validateRt.getResult())) {
			return validateRt;
		}

		// 4.推送极光之前先查出最近一次登录的设备日志ID，然后在写日志表
		String lastDeviceId = loginLogMapper.getLastDeviceId(user.getUserId(),LocalDate.now().minusDays(30L).toString());

		// 5.写表(写设备表，登录日志,初次登录写表)
		ResultDto<?> writeUserInfoRt = writeUserLoginInfo(command, user);
		if (!RESULT_SUCCESS_CODE.equals(writeUserInfoRt.getResult())) {
			return writeUserInfoRt;
		}

		// 6.登录成功推极光
		if (StringUtils.isNotBlank(lastDeviceId) && (!lastDeviceId.equals(command.getDeviceId()))) {
			LOG.info("member user login success jPushProducer begin,userId={},token={}", user.getUserId(),command.getToken());
			if (StringUtils.isNotBlank(user.getUserId())) {
				PushMQ msg = new PushMQ();
				msg.setApp("cfront");
				msg.setUid(user.getUserId());
				msg.setContent(getContent(null));
				jPushProducer.send(msg);
				LOG.info("member user login success jPushProducer end,param={}", JsonUtils.toJsonString(msg));
			} else {
				LOG.warn("member user login success jPushProducer warn ========> 参数为空");
			}
		}

		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, user);
	}

	// 生成消息内容
	private String getContent(String token) {
		Map<String, String> result = new HashMap<>();
		result.put("systemType", "user_login");
		result.put("token", token);
		result.put("tranTime", LocalDateTime.now().toString());
		return JsonUtils.toJsonString(result);
	}

	private ResultDto<?> validateUser(LoginCommand command, User user, String verificationCode) {
		if (StringUtils.isBlank(verificationCode)) {
			// 密码输错次数校验
			if (user.getPassword() == null || !user.getPassword().equals(command.getPassword())) {
				Integer count = errPasswordCount(user, command);
				if (count > (Max_ERROR_COUNT - 1)) {
					if (user.getStatus() == 1) {
						UserUpdateDto userUpdateDto = new UserUpdateDto();
						userUpdateDto.setUserId(user.getUserId());
						userUpdateDto.setStatus(2);
						updateUserByUserId(userUpdateDto);
					}
					return new ResultDto<>(ResultCodeEnum.User.PASSWORD_COUNT_ERROR);
				}

				ResultDto<?> rs = new ResultDto<>(ResultCodeEnum.User.ID_PWD_MATCHING_ERROR);
				Integer errCount = Max_ERROR_COUNT - count;
				if (errCount < 0) {
					errCount = 0;
				}
				if (count == 1) {
					rs.setDescription("密码错误，请重新输入");
				} else if(count == 2){
					rs.setDescription("密码错误，还可以输入" + errCount + " 次，如忘记密码，可通过#忘记密码#找回");
				} else if(count == 3 ||  count == 4){
					rs.setDescription("密码错误，还可以输入" + errCount + " 次，推荐使用手机验证码快捷登录");
				}else {
					rs.setDescription("密码错误，再错一次账号将被冻结，推荐使用手机验证码快捷登录");
				}
				return rs;
			}
		} else {
			VerifySMSReq verifySMSReq = new VerifySMSReq();
			verifySMSReq.setBusinessType(2);
			verifySMSReq.setPhoneNo(command.getUsername());
			verifySMSReq.setVerifyCode(verificationCode);
			ResultDto<?> result = (ResultDto<?>) smsController.verifySmsCode(verifySMSReq);
			if (!RESULT_SUCCESS_CODE.equals(result.getResult())) {
				return result;
			}
		}
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
	}

	// 密码输错，存入缓存
	private Integer errPasswordCount(User user, LoginCommand command) {
		String key = PASSWORD_ERR_REDIS_PREFIX + user.getMobilePhone();
		String value = redisService.getStringValue(key);
		Integer count = null;
		if (StringUtils.isBlank(value)) {
			count = 0;
		} else {
			count = Integer.parseInt(value);
		}
		count += 1;
		// redis自动解除冻结
		LocalDateTime cureTime = LocalDateTime.now();
		int hour = cureTime.getHour();
		int minute = cureTime.getMinute();
		int second = cureTime.getSecond();
		int time = 86400 - ((hour * 60 * 60) + (minute * 60) + second);
		redisService.setStringValue(key, count + "", new Long((long) time));
		return count;
	}

	// 校验用户状态(是否常用设备,是否账号冻结)
	private ResultDto<?> validateUserStatus(User user, LoginCommand command) {
		try {
			String verificationCode = command.getVerificationCode();
			Integer userStatus = user.getStatus();

			if (userStatus == 3) {
				return new ResultDto<>(ResultCodeEnum.User.USER_STATUS_ERROR);
			}
			if (userStatus == 1 && (!uncheckDevice.equals(user.getMobilePhone()))) {
				// 判断是否为常用设备 device_id,不是不允許登入
				ResultDto<?> alwaysDevice = new ResultDto<>();
				alwaysDevice.setResult(RESULT_SUCCESS_CODE);
				if (StringUtils.isBlank(verificationCode)) {
					alwaysDevice = isAlwaysDevice(user.getUserId(), command.getDeviceId());
				}
				if (!RESULT_SUCCESS_CODE.equals(alwaysDevice.getResult())) {
					return new ResultDto<>(ResultCodeEnum.User.NO_DEVICE_UNUSER_ERROR);
				}
			}

			if (userStatus == 2 && (!uncheckDevice.equals(user.getMobilePhone()))) {
				String key = PASSWORD_ERR_REDIS_PREFIX + user.getMobilePhone();
				String value = redisService.getStringValue(key);
				//当redis自动解封时,登录先要判断常用设备
				if(StringUtils.isBlank(verificationCode) && StringUtils.isBlank(value)){
					// 判断是否为常用设备 device_id,不是不允許登入
					ResultDto<?> alwaysDevice = isAlwaysDevice(user.getUserId(), command.getDeviceId());
					if (!RESULT_SUCCESS_CODE.equals(alwaysDevice.getResult())) {
						return new ResultDto<>(ResultCodeEnum.User.NO_DEVICE_UNUSER_ERROR);
					}
				}
				//有短信验证码或者redis自动解封时解锁
				if ((StringUtils.isNotBlank(verificationCode)) || (StringUtils.isBlank(value))) {
					UserUpdateDto userUpdateDto = new UserUpdateDto();
					userUpdateDto.setUserId(user.getUserId());
					userUpdateDto.setStatus(1);
					updateUserByUserId(userUpdateDto);
					redisService.del(key);
				} else {
					return new ResultDto<>(ResultCodeEnum.User.DEVICE_UNUSER_ERROR);
				}
			}

			return new ResultDto<>(ResultCodeEnum.System.SUCCESS);

		} catch (Exception e) {
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	private ResultDto<?> writeUserLoginInfo(LoginCommand command, User user) {
		Integer userStatus = user.getStatus();
		String userId = user.getUserId();
		// 5.1登录日志保存 设备信息保存
		SaveLoginInfo4MemberCommand loginDto = new SaveLoginInfo4MemberCommand();
		loginDto.setUserId(userId);
		loginDto.setDeviceId(command.getDeviceId());
		loginDto.setDeviceName(command.getDeviceName());
		loginDto.setDeviceVersion(command.getDeviceVersion());
		// loginDto.setIp(command.getIp());
		// loginDto.setCount(command.getCount());
		saveLoinLog(loginDto);

		// 5.2 记录登录时间
		UserUpdateDto updateLogintime = new UserUpdateDto();
		updateLogintime.setUserId(user.getUserId());
		updateLogintime.setLastLoginDatetime(LocalDateTime.now());
		if (userStatus == 0) {
			// 用户第一次登录系统
			updateLogintime.setStatus(1);
		}
		updateUserByUserId(updateLogintime);

		// 5.3 登录成功，清除缓存中密码错误次数
		String key = PASSWORD_ERR_REDIS_PREFIX + user.getMobilePhone();
		redisService.del(key);

		return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
	}

	// ********************C端登录********

	@Override
	public PageDto<MerchantCommontDto> queryMyComment(String userId, String curPag, String pageSiz) {
		int curPage = Integer.parseInt(curPag);// 当前页
		int pageSize = Integer.parseInt(pageSiz);// 页数大小
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("beginNum", pageSize * (curPage - 1));
		map.put("endNum", pageSize);
		int totalCount = commentMapper.selectByUserIdCount(map);// 总条数
		List<Comment> list = null;
		if (totalCount > 0) {
			list = commentMapper.selectByUserId(map);
		}
		if (null == list || list.size() < 1) {
			PageDto<MerchantCommontDto> pag = new PageDto<MerchantCommontDto>();
			pag.setCurPage(curPage);
			pag.setList(new ArrayList<>());
			pag.setPageSize(pageSize);
			pag.setTotalCount(totalCount);
			pag.setTotalPage(0);
			return pag;
		}
		List<MerchantCommontDto> listComment = new ArrayList<>();
		User user = userMapper.selectByPrimaryKey(userId);
		for (Comment comment : list) {
			MerchantCommontDto commentDto = new MerchantCommontDto();
			commentDto.setPaymentNo(comment.getPaymentNo());
			commentDto.setMerchantNo(comment.getMerchantNo());
			commentDto.setUserId(userId);
			commentDto.setNickName(user.getNickName());
			commentDto.setCreateTime(comment.getCreateTime());
			commentDto.setGrade(comment.getGrade());
			commentDto.setContent(comment.getContent());

			List<CommentLabels> listCommentLabels = commentLabelsMapper.selectByCommentId(comment.getId());
			String[] labels = new String[listCommentLabels.size()];
			for (int i = 0; i < listCommentLabels.size(); i++) {
				labels[i] = listCommentLabels.get(i).getCommentLabelName();
			}
			commentDto.setLabels(labels);
			listComment.add(commentDto);
		}
		PageDto<MerchantCommontDto> data = new PageDto<MerchantCommontDto>(totalCount, curPage, pageSize, listComment);
		return data;
	}

	@Override
	public Object getCurUserInfo(String userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		if (user != null) {
			String mobilePhone = user.getMobilePhone();
			String realName = user.getRealName();
			String idCardAuth = user.getCertNum();
			String photo = addImagePrefix(user.getPhoto());
			Integer authStatus = user.getAuthStatus();
			// 目前返回前台数据不管其它认证,若改动请与前台协商,只区分0(未认证)和1(已认证)
			if (null != authStatus && authStatus != 0) {
				authStatus = 1;
			} else {
				authStatus = 0;
			}
			Boolean enableHappycoin = user.getEnableHappycoin();
			Boolean accountProtect = user.getAccountProtect();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("mobilePhone", mobilePhone);
			map.put("realName", realName);
			map.put("idCardAuth", idCardAuth);
			map.put("photo", photo);
			map.put("authStatus", authStatus);
			map.put("enableHappyCoin", enableHappycoin);
			map.put("accountProtect", accountProtect);
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, map);
		} else {
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, null);
		}
	}

	@Override
	public User getUserInfoByMobilePhone(String mobilePhone) {
		User user = userMapper.getUserInfoByMobilePhone(mobilePhone);
		if(null != user){
			user.setPhoto(addImagePrefix(user.getPhoto()));
		}
		return user;
	}

	@Override
	public Object getMemberInfo(String userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		if(user == null){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("result", ResultCodeEnum.System.SYSTEM_ERROR.getCode());
			map.put("description", "用户不存在");
			return map;
		}
		//用户真实姓名为空时，去用户昵称
		String name = user.getRealName();
		if(StringUtils.isBlank(name)){
			name = user.getNickName();
		}
		String recommendAgentId = user.getRecommendAgentId();// 发展机构ID(推荐商家)
		String recommendAgentType = user.getRecommendAgentType();// 发展机构类型(推荐机构类型)
		String recommendBy = user.getRecommendBy();// 发展员工ID(推荐人)
		String recommendShopManager = user.getRecommendShopManager();// 发展店长ID(商家店长ID)
		Integer status = user.getStatus(); // 用户状态
		Boolean enableHappycoin = user.getEnableHappycoin(); // 乐豆开关
		String joinType = "0"; //合伙人商户,0否，1是',

		if ("5".equals(recommendAgentType)) {
			List<String>  merchantNos = new ArrayList<>();
			merchantNos.add(recommendAgentId);
			List<MerchantInfo2Pay> merchantInfo = merchantInfoServiceRepo.getMerchantByMerchantNoList(merchantNos);
			if (merchantInfo != null && (!merchantInfo.isEmpty())) {
				for (MerchantInfo2Pay merchantInfo2Pay : merchantInfo) {
					joinType = merchantInfo2Pay.getJoinType();
				}
			}else{
				LOG.info("商户不存在merchantNo={}", recommendAgentId);
			}
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", ResultCodeEnum.System.SUCCESS.getCode());
		map.put("description", "成功");
		map.put("status", status);
		map.put("agentNo", recommendAgentId);
		map.put("agentType", recommendAgentType);
		map.put("enableHappycoin", enableHappycoin);
		map.put("userId", user.getUserId());
		map.put("userName", name);
		map.put("userDevManager", recommendShopManager);
		map.put("userDevStaff", recommendBy);
		map.put("userMobile", user.getMobilePhone());
		map.put("joinType", joinType);

		return map;
	}
	
	@Override
	public Object getMemberInfoByOtherId(String userId) {
		UserRelation userRelation = userRelationMapper.selectByNewUserId(userId);
		if(userRelation != null && StringUtils.isNotBlank(userRelation.getOldUserId())){
			userId = userRelation.getOldUserId();
		}
		return getMemberInfo(userId);
	}
	
	@Override
	public ResultDto<PageDto<UserInfotForSDto>> getMemberInfoToS(UserForSDto userForSDto) {
		Integer curPage = userForSDto.getCurPage();// 当前页
		Integer pageSize = userForSDto.getPageSize();// 页数大小
		if (null == pageSize || pageSize < 1) {
			pageSize = 10;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("recommendAgentType", userForSDto.getRecommendAgentT());
		map.put("recommendAgentId", userForSDto.getRecommendAgentId());
		map.put("mobilePhone", userForSDto.getMobilePhone());
		map.put("beginTime", userForSDto.getBeginTime());
		map.put("endTime", userForSDto.getEndTime());
		map.put("begin", pageSize * (curPage - 1));
		map.put("end", pageSize);
		int totalCount = userMapper.getChannelUserCount(map);
		List<UserInfotForSDto> userList = null;
		if (totalCount > 0) {
			userList = userMapper.getChannelUserList(map);
			for (UserInfotForSDto userInfotForSDto : userList) {
				userInfotForSDto.setPhoto(addImagePrefix(userInfotForSDto.getPhoto()));
			}
		} else {
			userList = new ArrayList<>();
		}
		PageDto<UserInfotForSDto> data = new PageDto<UserInfotForSDto>(totalCount, curPage, pageSize, userList);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}

	@Override
	public ResultDto<List<UserRltForSDto>> getMemberCountToS(UserForSDto userForSDto) {
		List<String> recommendAgentIdList = userForSDto.getRecommendAgentIdList();
		String recommendAgentType = userForSDto.getRecommendAgentT();
		List<UserRltForSDto> data = userMapper.getChannelUserCountList(recommendAgentIdList, recommendAgentType);
		if (null == data) {
			data = new ArrayList<>();
		}
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}

	@Override
	public ResultDto<?> validateClient(String userId) {
		int logCount = loginLogMapper.validateClient(userId);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, logCount);
	}

	@Override
	public ResultDto<?> isAlwaysDevice(String userId, String deviceId) {
		List<UserDevice> userdevice = userDeviceMapper.selectByPrimaryKey(userId);
		for (UserDevice list : userdevice) {
			if (deviceId.equals(list.getDeviceId())) {
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS, userdevice);
			}
		}
		return new ResultDto<>(ResultCodeEnum.User.NO_DEVICE_UNUSER_ERROR, null);
	}

	@Override
	public User getUserInfoByUserId(String userId) {
		User result = userMapper.selectByPrimaryKey(userId);
		if (null == result){
			return null;
		}
		result.setPhoto(addImagePrefix(result.getPhoto()));
		return result;
	}

	@Override
	public ResultDto<Integer> saveLoinLog(SaveLoginInfo4MemberCommand command) {
		loginLogMapper.insertSelective(buildLoginLog(command));
		List<UserDevice> list = userDeviceMapper.selectByPrimaryKey(command.getUserId());
		boolean flag = false;
		for (UserDevice userDevice : list) {
			if (command.getDeviceId().equals(userDevice.getDeviceId())) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			userDeviceMapper.insertSelective(buildDeviceLog(command));
		} else {
			// 更新次数,需求待定
		}
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, null);
	}

	@Override
	public void updateUserTransFlag(String userId) {
		LOG.info("更新会员初次交易标识》》》会员ID:{}", userId);
		try {
			userMapper.updateUserTransFlag(userId);
		} catch (Exception e) {
			LOG.error("更新会员初次交易标识》》》错误{}", e);
		}
	}

	private UserDevice buildDeviceLog(SaveLoginInfo4MemberCommand command) {
		UserDevice data = new UserDevice();
		data.setLastLoginTime(LocalDateTime.now());
		data.setDeviceId(command.getDeviceId());
		data.setDeviceName(command.getDeviceName());
		data.setDeviceVersion(command.getDeviceVersion());
		data.setUserId(command.getUserId());
		return data;
	}

	private LoginLog buildLoginLog(SaveLoginInfo4MemberCommand command) {
		LoginLog data = new LoginLog();
		data.setLoginTime(LocalDateTime.now());
		data.setDeviceId(command.getDeviceId());
		data.setDeviceName(command.getDeviceName());
		data.setDeviceVersion(command.getDeviceVersion());
		data.setIp(command.getIp());
		data.setUserId(command.getUserId());
		return data;
	}

	@Override
	public Boolean queryIsRegisterByMobileNo(String mobilePhone) {
		return userMapper.queryIsRegisterByMobileNo(mobilePhone);
	}

	@Override
	public List<UserInfoDto> queryUserInfoListByUserIds(List<String> userIdList) {
		try {
			List<UserInfoDto> rltList = userMapper.queryUserInfoListByUserIds(userIdList);
			for (UserInfoDto userInfoDto : rltList) {
				if (!StringUtils.isEmpty(userInfoDto.getImgUrl())) {
					StringBuffer imgUrl = new StringBuffer(urlPrefix);
					userInfoDto.setImgUrl(imgUrl.append(userInfoDto.getImgUrl()).toString());
				}
			}
			return rltList;
		} catch (Exception e) {
			LOG.info("queryUserInfoListByUserIds exception,e：{}", e);
		}
		return null;
	}

	@Override
	public List<UserUpdateDto> getUserByuserIdList(List<String>  userIdList) {
		try {
			LOG.info("getUserByuserIdList begin,userIdList={}", JsonUtils.toJsonString(userIdList));
			if (null == userIdList || userIdList.size() < 1) {
				return new ArrayList<>();
			}
			userIdList = ListUtils.removeSameUnit(userIdList);
			//防止查询数据过大，进行批量查询
			List<List<String>> splitList = ListUtils.split(userIdList);
			List<User> userList = new ArrayList<>();
			for (List<String> list : splitList) {
				List<User> users = userMapper.getUserByuserIdList(list);
				if(CollectionUtils.isNotEmpty(users)){
					userList.addAll(users);
				}
			}
			
			List<UserUpdateDto> rltList = new ArrayList<>();
			for (User source : userList) {
				UserUpdateDto target = new UserUpdateDto();
				BeanUtils.copyProperties(source, target);
				target.setPhoto(addImagePrefix(source.getPhoto()));
				rltList.add(target);
			}
			LOG.info("getUserByuserIdList end,userIdList={}", JsonUtils.toJsonString(rltList));
			return rltList;
		} catch (Exception e) {
			LOG.info("getUserByuserIdList exception,e：{}", e);
		}
		return null;
	}

	@Override
	public Object sum(UserSumFansDto user) {
		String recommendBy = null;
		String recommendAgentId = null;
		String recommendAgentType = null;
		if (StringUtils.isBlank(user.getRecommendBy())) {
			recommendAgentId = user.getRecommendAgentId();// 推荐商家
			recommendAgentType = user.getRecommendAgentType();// 推荐机构类型
		} else {
			recommendBy = user.getRecommendBy();// 推荐人userId
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("beginTime", user.getBeginTime());
		map.put("endTime", user.getEndTime());
		map.put("recommendBy", recommendBy);
		map.put("recommendAgentId", recommendAgentId);
		map.put("recommendAgentType", recommendAgentType);
		Integer yesterdayCount = userMapper.getYesterdayCount(map);
		Integer inviteCount = userMapper.getInviteCount(recommendBy, recommendAgentId, recommendAgentType);
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("inviteCount", inviteCount == null ? 0 : inviteCount);
		rtMap.put("yesterdayCount", yesterdayCount == null ? 0 : yesterdayCount);
		LOG.info("sum(all fans) end success inviteCount {} yesterdayCount {}", inviteCount, yesterdayCount);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, rtMap);
	}

	@Override
	public ResultDto<Boolean> getUserInfoByIdCard(String cardId) {
		Integer cardCount = userMapper.getUserInfoByIdCard(cardId);
		Boolean result = false;
		if (cardCount > 0) {
			result = true;
		}
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, result);
	}

	@Override
	public String removeImagePrefix(String imageUri) {
		if (StringUtils.isBlank(imageUri)) {
			return imageUri;
		} else {
			String image = imageUri.replaceAll(urlPrefix, "");
			LOG.info("去除图片前缀 UserServiceImpl.removeImagePrefix,ago={},after={}", imageUri, image);
			return image;
		}
	}

	@Override
	public String addImagePrefix(String imageUri) {
		if (StringUtils.isBlank(imageUri)) {
			return imageUri;
		} else {
			String image = new StringBuffer(urlPrefix).append(imageUri).toString();
			LOG.info("添加图片前缀 UserServiceImpl.addImagePrefix,ago={},after={}", imageUri, image);
			return image;
		}
	}
	
	@Override
	@Transactional
	public ResultDto<?> forgotPassword(UserForgotPwdDto userForgotPwdDto) {
		User user = userMapper.getUserInfoByMobilePhone(userForgotPwdDto.getMobilePhone());
		if(null != user){
			if(StringUtils.isBlank(userForgotPwdDto.getIdCard()) && StringUtils.isNotBlank(userForgotPwdDto.getPassword())){
				User record = new User();
				record.setUserId(user.getUserId());
				record.setPassword(userForgotPwdDto.getPassword());
				if(2 == user.getStatus()){
					record.setStatus(1);
				}
				int rlt = 0;
				if (StringUtils.isNotBlank(userForgotPwdDto.getPassword())) {
					rlt = userMapper.updateByPrimaryKeySelective(record);
				}
				//锁定解除
				redisService.del(PASSWORD_ERR_REDIS_PREFIX + user.getMobilePhone());
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS,rlt);
			}else{
				if(0 == user.getAuthStatus()){
					return new ResultDto<>(ResultCodeEnum.User.IDCARD_UNVALIDATER_ERROR);
				}
				if(!userForgotPwdDto.getIdCard().equals(user.getCertNum())){
					return new ResultDto<>(ResultCodeEnum.User.IDCARD_VALIDATER_ERROR);
				}
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			}
		}else{
			return new ResultDto<>(ResultCodeEnum.User.NO_USER_INFO_ERROR);
		}
	}

	@Override
	public ResultDto<PageDto<UserInfo2Manage>> getMemberInfoToM(UserForMDto userForMDto) {
		Integer curPage = userForMDto.getCurPage();
		Integer pageSize = userForMDto.getPageSize();
		Map<String, Object> map = new HashMap<>();
		map.put("registerType", userForMDto.getRegistType() == null ? null : userForMDto.getRegistType().getValue());
		map.put("recommendAgentType", userForMDto.getAgentType() == null ? null : userForMDto.getAgentType().getValue());
		map.put("userId", userForMDto.getUserId());
		map.put("recommendAgentId", userForMDto.getAgentNo());
		map.put("activeId", userForMDto.getActiveId());
		map.put("mobilePhone", userForMDto.getMobilePhone());
		map.put("realName", userForMDto.getRealName());
		map.put("status", userForMDto.getStatus());
		map.put("channel", StringUtils.isNotBlank(userForMDto.getChannel()) ? userForMDto.getChannel() : null);
		map.put("isBind", StringUtils.isNotBlank(userForMDto.getIsBind()) ? userForMDto.getIsBind() : null);
		map.put("beginTime", userForMDto.getBeginRegisterTime());
		map.put("endTime", userForMDto.getEndRegisterTime());
		map.put("begin", pageSize * (curPage - 1));
		map.put("end", pageSize);

		//有渠道和绑定的过滤条件,以用户关系表为主表
		if ((!"pt".equals(userForMDto.getChannel())) && (StringUtils.isNotBlank(userForMDto.getChannel()) || StringUtils.isNotBlank(userForMDto.getIsBind()))) {
			return getInfoByChannelIsBind(map, userForMDto);
		}
		Integer totalCount = userMapper.getManageUserCount(map);
		List<UserInfo2Manage> userList = new ArrayList<>();
		if (totalCount > 0) {
			List<User> sourceList = userMapper.getManageUserList(map);
			//组装返回数据,无渠道和绑定的过滤条件,以用户表为主表
			userList = getInfoNotByChannelIsBind(userList, sourceList);
		}
		PageDto<UserInfo2Manage> data = new PageDto<UserInfo2Manage>(totalCount, curPage, pageSize, userList);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}

	private List<UserInfo2Manage> getInfoNotByChannelIsBind(List<UserInfo2Manage> userList, List<User> sourceList) {
		List<String> newUserIdList = new ArrayList<>();
		List<String> oldUserIdList = new ArrayList<>();
		for (User user : sourceList) {
			String userId = user.getUserId();
			// 组装查询关系表参数 ,若手机号为空以newuserid查询,不为空以olduserid查询
			if (StringUtils.isBlank(user.getMobilePhone())) {
				newUserIdList.add(userId);
			} else {
				oldUserIdList.add(userId);
			}
		}
		List<UserRelation> newUserRelation = null;
		if (!newUserIdList.isEmpty()) {
			newUserRelation = userRelationMapper.getUserRelationsByNewId(newUserIdList);
		}
		List<UserRelation> oldUserRelation = null;
		if (!oldUserIdList.isEmpty()) {
			oldUserRelation = userRelationMapper.getUserRelationsByOldId(oldUserIdList);
		}

		// 组装返回数据
		for (User user : sourceList) {
			String userId = user.getUserId();
			UserInfo2Manage data = new UserInfo2Manage();
			BeanUtils.copyProperties(user, data);
			if (StringUtils.isBlank(user.getMobilePhone())) {
				// 手机为空
				if (newUserRelation != null && (!newUserRelation.isEmpty())) {
					for (UserRelation userRelation : newUserRelation) {
						if (userId.equals(userRelation.getNewUserId())) {
							buildUserRelation(userRelation, data);
							break;
						}
					}
				}
			} else {
				// 手机不为空
				String channel = null;
				String isBind = null;
				if (oldUserRelation != null && (!oldUserRelation.isEmpty())) {
					for (UserRelation userRelation : oldUserRelation) {
						if (userId.equals(userRelation.getOldUserId())) {
							if (userRelation.getNewUserId().equals(userRelation.getOldUserId())) {
								channel = userRelation.getChannel();
								isBind = "y";
							}
							buildUserRelation(userRelation, data);
						}
					}
					data.setChannel(channel);
					data.setIsBind(isBind);
				}
			}
			userList.add(data);
		}
		return userList;
	}
	
	//以关系表为主表组装数据
	private ResultDto<PageDto<UserInfo2Manage>> getInfoByChannelIsBind(Map<String, Object> map,
			UserForMDto userForMDto) {
		Integer totalCount = userRelationMapper.getManageUserCount(map);
		List<UserInfo2Manage> userList = new ArrayList<>();
		if (totalCount > 0) {
			// 组装关系表表数据
			List<UserRelation> userRelations = userRelationMapper.getManageUserList(map);
			List<String> userIdList = new ArrayList<>();
			for (UserRelation userRelation : userRelations) {
				userIdList.add(userRelation.getNewUserId());
			}
			// 组装会员表数据
			List<User> users = userMapper.getUserByuserIdList(userIdList);
			Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
			for (UserRelation userRelation : userRelations) {
				String userId = userRelation.getNewUserId();
				if (userMap.containsKey(userId)) {
					User source = userMap.get(userId);
					UserInfo2Manage target = new UserInfo2Manage();
					BeanUtils.copyProperties(source, target);
					target = buildUserRelation(userRelation, target);
					userList.add(target);
				}
			}
		}
		PageDto<UserInfo2Manage> data = new PageDto<UserInfo2Manage>(totalCount, userForMDto.getCurPage(),
				userForMDto.getPageSize(), userList);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}

	private UserInfo2Manage buildUserRelation(UserRelation userRelation, UserInfo2Manage target) {
		String channel = userRelation.getChannel();
		target.setChannel(channel);
		if(StringUtils.isNotBlank(userRelation.getOldUserId())){
			target.setIsBind("y");
		}else{
			target.setIsBind("n");
		}
		if(channel.equals("wx")){
			target.setOpenId(userRelation.getPayOrganId());
		}else if(channel.equals("zfb")){
			target.setAliPayId(userRelation.getPayOrganId());
		}else{
			
		}
		return target;
	}

	@Override
	public ResultDto<UserInfo2Manage> getMemberInfoToMDetail(String userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		user.setPhoto(addImagePrefix(user.getPhoto()));
		//将recommendBy赋值为实名，若未实名则赋值为手机号码，M端展示需求（目前推荐人都为C端用户）
		if(StringUtils.isNotBlank(user.getRecommendBy())){
			User recommendBy = userMapper.selectByPrimaryKey(user.getRecommendBy());
			if(null != recommendBy){
				user.setRecommendBy(StringUtils.isBlank(recommendBy.getRealName())?recommendBy.getMobilePhone():recommendBy.getRealName());
			}else{
				LOG.info("获取会员信息提供给M端调用 UserServiceImpl.getMemberInfoToM 推荐人【{}】未找到",user.getRecommendBy());
				user.setRecommendBy(null);
			}
		}
		UserInfo2Manage data = null;
		if(user != null){
			data = new UserInfo2Manage();
			BeanUtils.copyProperties(user, data);
		}
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}
	
	@Override
	public ResultDto<Integer> getCountByAgentType(List<String> types) {
		int count = userMapper.getCountByAgentType(types);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, count);
	}
	
	@Override
	public ResultDto<PageDto<User>> queryUserInfoByrealName(MerchantInfoForMDto command) {
		Integer pageSize = command.getNumPerPage();
		Integer curPage = command.getPageNum();
		Map<String, Object> map = new HashMap<>();
		map.put("realName", command.getPlanName());
		map.put("begin", pageSize * (curPage - 1));
		map.put("end", pageSize);
		Integer totalCount = userMapper.queryUserInfoForMCount(map);
		totalCount = totalCount == null ? 0 : totalCount;
		List<User> merchantList = null;
		if (totalCount > 0) {
			merchantList = userMapper.queryUserInfoForM(map);
		} else {
			merchantList = new ArrayList<>();
		}
		PageDto<User> data = new PageDto<User>(totalCount, curPage, pageSize, merchantList);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}
	
	@Override
	public ResultDto<PageDto<InviteUserResp>> queryInviteUserDto(InviteUserReq command) {
		Integer pageSize = command.getPageSize();
		Integer curPage = command.getCurPage();
		String operatorName = command.getOperatorName(); 
		String merchantNo = command.getMerchantNo();
		List<String> operatorNames = null;
		if(StringUtils.isNotBlank(operatorName)){
			List <User> users = userMapper.queryUserInfoByRealName(operatorName, null);
			if(CollectionUtils.isEmpty(users)){
				PageDto<InviteUserResp> data = new PageDto<>(0, curPage, pageSize, new ArrayList<>());
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
			}else{
				operatorNames = new ArrayList<>();
				for (User user : users) {
					operatorNames.add(user.getUserId());
				}
			}
		}
		
		if(StringUtils.isNotBlank(command.getRecommendBy())){
			if(operatorNames != null){
				operatorNames.add(command.getRecommendBy());
			}else{
				operatorNames = new ArrayList<>();
				operatorNames.add(command.getRecommendBy());
			}
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("recommendAgentId", merchantNo);
		map.put("recommendBys", operatorNames);
		map.put("beginTime", command.getBeginTime());
		map.put("endTime", command.getEndTime());
		map.put("begin", pageSize * (curPage - 1));
		map.put("end", pageSize);
		
		Integer totalCount = userMapper.queryInviteUserDtoCount(map);
		totalCount = totalCount == null ? 0 : totalCount;
		List<InviteUserResp> rltList = new ArrayList<>();
		if (totalCount > 0) {
			List<User> userList = userMapper.queryInviteUserDto(map);
			List<String> recommendBys = new ArrayList<>();
			Map<String,String> recommendByMap = new HashMap<>();
			for (User user : userList) {
				String id = user.getRecommendBy();
				if(StringUtils.isNotBlank(id) && (!recommendBys.contains(id))){
					recommendBys.add(id);
				}
			}
			if(recommendBys.size() > 0){
				List<User> recommendByList = userMapper.getUserByuserIdList(recommendBys);
				if(CollectionUtils.isNotEmpty(recommendByList)){
					recommendByList.forEach((user) -> recommendByMap.put(user.getUserId(), user.getRealName()));
				}
			}
			for (User user : userList) {
				InviteUserResp target = new InviteUserResp();
				target.setStatus("0");
				target.setAvatarUrl(addImagePrefix(user.getPhoto()));
				target.setMobile(user.getMobilePhone());
				target.setOperatorName(null == recommendByMap.get(user.getRecommendBy())? "" : recommendByMap.get(user.getRecommendBy()));
				if(null != user.getRegisterTime()){
					target.setRegistDate(user.getRegisterTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					String registMonth = ""+user.getRegisterTime().getYear() + (user.getRegisterTime().getMonthValue()>9 ? user.getRegisterTime().getMonthValue() : ("0"+user.getRegisterTime().getMonthValue()));
					target.setRegistMonth(Integer.valueOf(registMonth));
				}
				target.setUserName(user.getNickName());
				rltList.add(target);
			}
		} 
		PageDto<InviteUserResp> data = new PageDto<>(totalCount, curPage, pageSize, rltList);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}

	@Override
	public ResultDto<List<InviteUserCount>> queryInviteUserCount(InviteUserCountReq command) {
		Map<String, Object> map = new HashMap<>();
		map.put("recommendAgentId", command.getMerchantNo());
		map.put("recommendBys", command.getRecommendBys());
		List<InviteUserCount> data = userMapper.queryInviteUserCountForM(map);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}
}
