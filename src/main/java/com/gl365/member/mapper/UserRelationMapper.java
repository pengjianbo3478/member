package com.gl365.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gl365.member.model.UserRelation;

public interface UserRelationMapper {

	List<UserRelation> selectByUserId(String userId);
	
    int insertSelective(UserRelation record);

    int updateByPrimaryKeySelective(UserRelation record);

	UserRelation selectByPayOrganId(String payOrganId);
	
	UserRelation selectByNewUserId(@Param("newUserId") String newUserId);
	
	UserRelation selectByUserIdAndChannel(@Param("userId") String userId,@Param("channel") String channel);

	List<UserRelation> getUserRelationByPayOrganId(@Param("payOrganIds") List<String> payOrganIds);

	Integer getManageUserCount(Map<String, Object> map);
	List<UserRelation> getManageUserList(Map<String, Object> map);

	List<UserRelation> getUserRelationsByNewId(@Param("newUserIds") List<String> newUserIds);
	List<UserRelation> getUserRelationsByOldId(@Param("oldUserIds") List<String> oldUserIds);
	
	List<UserRelation> getUserPhotoAndName(Map<String, Object> map);

}