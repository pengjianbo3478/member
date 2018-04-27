package com.gl365.member.dto.merchant;

public class MerchantInfo2Pay{

	private Long merchantId;

	private String merchantNo;

	private String merchantName;
	
	private String merchantShortname;
	
	private String status;
	
	private String joinType;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantShortname() {
		return merchantShortname;
	}

	public void setMerchantShortname(String merchantShortname) {
		this.merchantShortname = merchantShortname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
}