package com.gl365.member.dto.mq;

import java.io.Serializable;

public class MQRegistryBO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 给乐用户名
	 */
	private String merchantUserName;
	
	/**
	 * 开户结果
	 */
	private String createResp;
	
	/**
	 * 遮蔽卡号
	 */
	private String cardNo;
	
	/**
	 * 支付渠道
	 */
	private String channelId;

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public String getCreateResp() {
		return createResp;
	}

	public void setCreateResp(String createResp) {
		this.createResp = createResp;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	
	
}
