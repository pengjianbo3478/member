package com.gl365.member.dto.merchant.command;

import java.io.Serializable;

public class GetCommentStatusReq implements Serializable{

	private static final long serialVersionUID = 8890215974878380470L;
	
	private String merchantNo;
	
	private String userId;
	
	private String paymentNo;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
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
	
}
