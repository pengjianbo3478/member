package com.gl365.member.mapper;

import org.apache.ibatis.annotations.Param;

import com.gl365.member.model.ExternalAccount;

public interface ExternalAccountMapper {

	int insertSelective(ExternalAccount account);
	
	Boolean queryIsRegisterByUserId(@Param("userId") String userId, @Param("externalChannel") String externalChannel);
}
