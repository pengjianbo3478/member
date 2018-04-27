package com.gl365.member.mapper;

import org.apache.ibatis.annotations.Param;

import com.gl365.member.model.LoginLog;

public interface LoginLogMapper {
    int insert(LoginLog record);

    int insertSelective(LoginLog record);

	int validateClient(String userId);
	
	String getLastDeviceId (@Param("userId") String userId,@Param("loginTime") String loginTime);
}