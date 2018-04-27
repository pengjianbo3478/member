package com.gl365.member.service;

import com.gl365.member.dto.ResultDto;
import com.gl365.member.model.ExternalAccount;

public interface UserRegisterChannelService {

	public ResultDto<?> queryRealNameInfo(String userId,String channelId);
	
	public ResultDto<?> addChannelRegisterInfo(ExternalAccount info);
}
