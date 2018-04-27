package com.gl365.member.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.users.UserRealNameInfo;
import com.gl365.member.mapper.ExternalAccountMapper;
import com.gl365.member.mapper.UserMapper;
import com.gl365.member.model.ExternalAccount;
import com.gl365.member.service.UserRegisterChannelService;

@Service
public class UserRegisterChannelServiceImpl implements UserRegisterChannelService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserRegisterChannelServiceImpl.class);
	
	@Autowired
	private ExternalAccountMapper eaMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public ResultDto<?> queryRealNameInfo(String userId, String channelId) {
		logger.info("queryRealNameInfo begin,userId={},channelId={}",userId,channelId);
		ResultDto<?> rlt = null; 
		try{
			Boolean b = eaMapper.queryIsRegisterByUserId(userId, channelId);
			UserRealNameInfo info = userMapper.getRealNameInfo(userId);
			if(null != b){
				info.setFftRegister(true);
			}else{
				info.setFftRegister(false);
			}
			rlt = ResultDto.result(ResultCodeEnum.System.SUCCESS, info);
		}catch(Exception e){
			logger.error("queryRealNameInfo exception,e:{}",e);
			rlt = ResultDto.getErrInfo();
		}
		logger.info("queryRealNameInfo end,rlt:{}",JsonUtils.toJsonString(rlt));
		return rlt;
	}

	@Override
	public ResultDto<?> addChannelRegisterInfo(ExternalAccount info) {
		logger.info("addChannelRegisterInfo begin,req={}",info);
		ResultDto<?> rlt = null;
		try{
			Boolean b = eaMapper.queryIsRegisterByUserId(info.getUserId(), info.getExternalChannel());
			if(null != b){
				logger.info("addChannelRegisterInfo 开户信息已存在无需更新");
			}else{
				eaMapper.insertSelective(info);
			}
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		}catch(Exception e){
			logger.error("addChannelRegisterInfo exception,e:{}",e);
			rlt = ResultDto.getErrInfo();
		}
		logger.info("addChannelRegisterInfo end,rlt={}",rlt);
		return null;
	}


}
