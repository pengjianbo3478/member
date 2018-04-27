package com.gl365.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gl365.member.dto.manage.InviteUserCount;
import com.gl365.member.dto.users.UserInfoDto;
import com.gl365.member.dto.users.UserInfotForSDto;
import com.gl365.member.dto.users.UserRealNameInfo;
import com.gl365.member.dto.users.UserRltForSDto;
import com.gl365.member.model.User;

public interface UserMapper {
	int deleteByPrimaryKey(String userId);

	User selectByPrimaryKey(String userId);

	/**
	 * 选择更新，必须要有userid
	 */
	int updateByPrimaryKeySelective(User record);

	/**
	 * 选择插入
	 */
	int insertSelective(User record);

	/**
	 * 修改密码
	 */
	int updatePassWordByOldPassWord(Map<String, String> record);

	/**
	 * 根据电话获取用户信息
	 */
	User getUserInfoByMobilePhone(String mobilePhone);

	/**
	 * 根据手机号查询是否已注册
	 * 
	 * @param mobilePhone
	 * @return
	 */
	Boolean queryIsRegisterByMobileNo(String mobilePhone);

	/**
	 * 查询用户信息列表
	 * 
	 * @param userIds
	 * @return
	 */
	List<UserInfoDto> queryUserInfoListByUserIds(@Param("userIdList") List<String> userIdList);

	/**
	 * 查询用户信息列表(全量信息)
	 * 
	 * @param userIds
	 * @return
	 */
	List<User> getUserByuserIdList(@Param("userIdList") List<String> userIdList);

	/**
	 * 根据商户或者推荐人号查所有粉丝
	 * 
	 * @return
	 */
	Integer getInviteCount(@Param("recommendBy") String recommendBy, @Param("recommendAgentId") String recommendAgentId, @Param("recommendAgentType") String recommendAgentType);

	Integer getYesterdayCount(Map<String, Object> map);

	/**
	 * 根据机构类型，机构ID查询userId列表
	 */
	Integer getChannelUserCount(Map<String, Object> map);

	List<UserInfotForSDto> getChannelUserList(Map<String, Object> map);

	List<UserRltForSDto> getChannelUserCountList(@Param("recommendAgentIdList") List<String> recommendAgentIdList, @Param("recommendAgentType") String recommendAgentType);

	/**
	 * 查询实名信息
	 * 
	 * @param userId
	 * @return
	 */
	UserRealNameInfo getRealNameInfo(@Param("userId") String userId);

	/**
	 * 根据身份证查询条数
	 */
	Integer getUserInfoByIdCard(@Param("cardId") String cardId);

	/**
	 * 更新会员初次交易标识
	 * 
	 * @param userId
	 */
	void updateUserTransFlag(String userId);
	
	/**
	 * 获取会员信息 提供给M端调用
	 */
	Integer getManageUserCount(Map<String, Object> map);
	List<User> getManageUserList(Map<String, Object> map);
	
	/**
	 * 根据机构类型获取会员个数,提供给M端调用
	 * 
	 * @param types
	 * @return
	 */
	int getCountByAgentType(@Param("typeList") List<String> types);

	/**
	 * M端会员查询
	 * @param map
	 * @return
	 */
	Integer queryUserInfoForMCount(Map<String, Object> map);
	List<User> queryUserInfoForM(Map<String, Object> map);
	
	/**
	 * 根据真名查用户
	 * @param operatorName
	 * @param merchantNo
	 * @return
	 */
	List<User> queryUserInfoByRealName(@Param("realName") String realName, @Param("recommendAgentId") String recommendAgentId);

	/**
	 * B端获取推广粉丝
	 * @param map
	 * @return
	 */
	Integer queryInviteUserDtoCount(Map<String, Object> map);
	List<User> queryInviteUserDto(Map<String, Object> map);
	
	/**
	 * M端查询推广粉丝
	 * @param map
	 * @return
	 */
	List<InviteUserCount> queryInviteUserCountForM(Map<String, Object> map);
}