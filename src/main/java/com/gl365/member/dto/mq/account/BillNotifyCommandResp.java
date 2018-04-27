package com.gl365.member.dto.mq.account;

import java.math.BigDecimal;

/**
 * 月账单发送MQ,主要为了转时间
 * @author dfs_508 2017年10月25日 下午3:20:55
 */
public class BillNotifyCommandResp{


	private String messageId;//避免因组件原因导致消息重复消费的消息标识
	
	private String userId;// 会员号 32 否
	
	private String billDate;// 账单月份 否
	
	private String userName;// 会员姓名 128 是
	
	private String userMobile;// 手机号 15 是
	
	private BigDecimal cashAmount;// 现金支付总额 否
	
	private BigDecimal beanAmount;// 乐豆支付总额 否
	
	private BigDecimal giftAmount;// 赠送乐豆总额 否
	
	private String systemType;// 消息类型 否
	
	private String sendTime;// 消息发送时间

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getBeanAmount() {
		return beanAmount;
	}

	public void setBeanAmount(BigDecimal beanAmount) {
		this.beanAmount = beanAmount;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
