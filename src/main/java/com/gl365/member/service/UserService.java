package com.gl365.member.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.gl365.member.dto.PageDto;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.manage.InviteUserCount;
import com.gl365.member.dto.manage.InviteUserCountReq;
import com.gl365.member.dto.manage.MerchantInfoForMDto;
import com.gl365.member.dto.manage.UserInfo2Manage;
import com.gl365.member.dto.merchant.InviteUserReq;
import com.gl365.member.dto.merchant.InviteUserResp;
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
import com.gl365.member.model.User;

public interface UserService {

	/**
	 * 通过uerid获取用户表信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserInfoByUserId(String userId);

	/**
	 * 修改用户
	 * 
	 * @param UserDto
	 * @return
	 */
	public Object updateUserByUserId(UserUpdateDto userUpdateDto);
	public Object updateUserByUserIdForM(UserUpdateDto userUpdateDto);

	/**
	 * 保存用户
	 * 
	 * @param UserDto
	 * @return
	 */
	public ResultDto<?> saveUserByUserId(UserUpdateDto userUpdateDto);

	/**
	 * 我的评论查询
	 * 
	 * @param curPage
	 *            , pageSize
	 * @return
	 */
	public PageDto<MerchantCommontDto> queryMyComment(String userId, String curPage, String pageSize);

	/**
	 * 获取当前用户信息,登录成功后调用
	 * 
	 * @param userId
	 * @return
	 */
	public Object getCurUserInfo(@PathVariable("userId") String userId);

	/**
	 * 根据电话获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserInfoByMobilePhone(@PathVariable("mobilePhone") String mobilePhone);

	/**
	 * 获取会员信息 提供给支付系统调用
	 * 
	 * @param userId
	 * @return
	 */
	public Object getMemberInfo(@PathVariable("userId") String userId);
	
	/**
	 * 先判断是不是微信或支付宝用户，若是且绑定则返回绑定会员信息，否则返回原会员信息
	 * @param userId
	 * @return
	 */
	public Object getMemberInfoByOtherId(String userId);
	

	/**
	 * 获取会员信息 提供给S端调用
	 * 
	 * @return ResultDto<?>
	 */
	public ResultDto<PageDto<UserInfotForSDto>> getMemberInfoToS(UserForSDto userForSDto);
	
	/**
	 * 根据机构类型和机构id,获取会员个数 ,提供给S端调用
	 * 
	 * @param userForSDto
	 * @return ResultDto<?>
	 */
	public ResultDto<List<UserRltForSDto>> getMemberCountToS(UserForSDto userForSDto);

	/**
	 * 获取当前当天使用的设备数
	 * 
	 * @param userId
	 * @return
	 */
	public ResultDto<?> validateClient(String userId);

	/**
	 * 是否是常用设备
	 * 
	 * @param userId
	 * @return
	 */
	public ResultDto<?> isAlwaysDevice(String userId, String deviceId);

	/**
	 * 记录登录日志
	 * 
	 * @param command
	 * @return
	 */
	public ResultDto<Integer> saveLoinLog(SaveLoginInfo4MemberCommand command);

	/**
	 * 根据手机号查询手机号是否已注册
	 * 
	 * @param mobilePhone
	 * @return 未注册返回null；已注册返回true
	 */
	public Boolean queryIsRegisterByMobileNo(@PathVariable("mobilePhone") String mobilePhone);

	/**
	 * 查询用户信息列表
	 * 
	 * @param userIdList
	 * @return
	 */
	public List<UserInfoDto> queryUserInfoListByUserIds(List<String> userIdList);

	/**
	 * 查询用户信息列表(全量)
	 * 
	 * @param userIdList
	 * @return
	 */
	public List<UserUpdateDto> getUserByuserIdList(List<String> userIdList);

	public Object sum(UserSumFansDto user);

	public ResultDto<Boolean> getUserInfoByIdCard(String cardId);

	/**
	 * 更新会员初次交易标识
	 * 
	 * @param 会员ID
	 */
	public void updateUserTransFlag(String userId);

	/**
	 * C端登录
	 */
	public ResultDto<?> login(LoginCommand command);

	/**
	 * 处理图片前缀
	 */
	public String removeImagePrefix(String imageUri);
	public String addImagePrefix(String imageUri);

	/**
	 * 忘记密码
	 */
	public ResultDto<?> forgotPassword(UserForgotPwdDto userForgotPwdDto);

	/**
	 * 获取会员信息 提供给M端调用
	 */
	public ResultDto<PageDto<UserInfo2Manage>> getMemberInfoToM(UserForMDto userForMDto);

	/**
	 * 根据机构类型获取会员个数,提供给M端调用
	 */
	public ResultDto<Integer> getCountByAgentType(List<String> types);

	public ResultDto<PageDto<User>> queryUserInfoByrealName(MerchantInfoForMDto command);

	public ResultDto<UserInfo2Manage> getMemberInfoToMDetail(String userId);

	public ResultDto<PageDto<InviteUserResp>> queryInviteUserDto(InviteUserReq command);

	public ResultDto<List<InviteUserCount>> queryInviteUserCount(InviteUserCountReq command);

}