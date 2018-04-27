package com.gl365.member.dto.merchant.command;

import java.math.BigDecimal;

/**
 * < 商家评论DTO >
 * 
 * @author hui.li 2017年4月20日 - 下午4:47:32
 * @Since 1.0
 */
public class SaveComment4MemberCommand {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 商户编号
	 */
	private String merchantNo;

	/**
	 * 消费金额
	 */
	private BigDecimal account;

	/**
	 * 支付订单编号
	 */
	private String paymentNo;

	public SaveComment4MemberCommand(String userId, String merchantNo, String paymentNo, BigDecimal account) {
		super();
		this.userId = userId;
		this.merchantNo = merchantNo;
		this.paymentNo = paymentNo;
		this.account = account;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public SaveComment4MemberCommand() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

}