package com.gl365.member.mq.consumer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl365.aliyun.ons.OnsListener;
import com.gl365.member.dto.mq.MQRegistryBO;
import com.gl365.member.mapper.ExternalAccountMapper;
import com.gl365.member.model.ExternalAccount;
import com.google.gson.Gson;

@Component("getway-registry-notify-consumer")
public class ChannelRegisterNotifyConsumer implements OnsListener{

	private static final Logger logger = LoggerFactory.getLogger(ChannelRegisterNotifyConsumer.class);
	
	@Autowired
	private ExternalAccountMapper eaMapper;
	
	@Override
	public void receive(byte[] source) {
		String req = new String(source);
		logger.info("<mq-received>第三方渠道注册开户成功通知,req:{}", req);
		MQRegistryBO registerBo = newInstance(req);
		if(null == registerBo || StringUtils.isBlank(registerBo.getMerchantUserName()) || StringUtils.isBlank(registerBo.getChannelId())){
			logger.error("<mq-received>第三方渠道注册开户成功通知,参数非法");
			return;
		}
		if("success".equalsIgnoreCase(registerBo.getCreateResp())){
			Boolean b = eaMapper.queryIsRegisterByUserId(registerBo.getMerchantUserName(), registerBo.getChannelId());
			if(null == b){
				ExternalAccount account = new ExternalAccount();
				account.setUserId(registerBo.getMerchantUserName());
				account.setExternalChannel(registerBo.getChannelId());
				eaMapper.insertSelective(account);
			}else{
				logger.info("<mq-received>第三方渠道注册开户成功通知,开户记录已存在，无需新增");
			}
		}
		logger.info("<mq-received>第三方渠道注册开户成功通知 end");
	}
	
	private MQRegistryBO newInstance(String message) {
		return new Gson().fromJson(message, MQRegistryBO.class);
	}

}
