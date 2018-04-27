package com.gl365.member.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.users.UserRegistReq;
import com.gl365.member.dto.users.relation.CreateUserReq;
import com.gl365.member.dto.users.relation.GetPhotoReq;
import com.gl365.member.dto.users.relation.UserPhotoAndName;
import com.gl365.member.model.UserRelation;

public interface UserRelationService {

	ResultDto<Set<String>> queryUserRelationByUserId(String userId);

	ResultDto<Map<String, Object>> createUserByPayOrganId(CreateUserReq createUserReq);

	ResultDto<?> registPayOrgan(UserRegistReq req);

	List<UserRelation> getUserRelationByPayOrganId(List<String> payOrganIds);

	List<UserRelation> getUserInfoByUserId(GetPhotoReq getPhotoReq);
	
	List<UserPhotoAndName> getUserPhotoAndName(List<String> uids);

	ResultDto<String> getPayOrganIdByUserId(String userId, String channel);

}