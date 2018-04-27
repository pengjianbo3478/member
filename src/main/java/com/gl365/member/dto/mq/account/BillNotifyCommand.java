package com.gl365.member.dto.mq.account;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 月账单接收MQ
 * @author dfs_508 2017年10月18日 下午12:14:19
 */
public class BillNotifyCommand{


	private String messageId;//避免因组件原因导致消息重复消费的消息标识
	
	private String userId;// 会员号 32 否
	
	private LocalDate billDate;// 账单月份 否
	
	private String userName;// 会员姓名 128 是
	
	private String userMobile;// 手机号 15 是
	
	private BigDecimal cashAmount;// 现金支付总额 否
	
	private BigDecimal beanAmount;// 乐豆支付总额 否
	
	private BigDecimal giftAmount;// 赠送乐豆总额 否
	
	private String systemType;// 消息类型 否

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

	public LocalDate getBillDate() {
		return billDate;
	}

	public void setBillDate(LocalDate billDate) {
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
}
