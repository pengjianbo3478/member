package com.gl365.member.dto.validator;

public class ValidatorSonDto {

	private String quotaID;
	
	private String quotaName;
	
	private Integer quotaType;
	/**
	 * 0 认证一致   1 认证一致  -1 库无记录
	 */
	private String quotaValue;
	
	private Integer quotaPrice;
	
	private Integer quotaValueType;
	
	private Integer quotaValuePercent;

	public String getQuotaID() {
		return quotaID;
	}

	public void setQuotaID(String quotaID) {
		this.quotaID = quotaID;
	}

	public String getQuotaName() {
		return quotaName;
	}

	public void setQuotaName(String quotaName) {
		this.quotaName = quotaName;
	}

	public Integer getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(Integer quotaType) {
		this.quotaType = quotaType;
	}

	public String getQuotaValue() {
		return quotaValue;
	}

	public void setQuotaValue(String quotaValue) {
		this.quotaValue = quotaValue;
	}

	public Integer getQuotaPrice() {
		return quotaPrice;
	}

	public void setQuotaPrice(Integer quotaPrice) {
		this.quotaPrice = quotaPrice;
	}

	public Integer getQuotaValueType() {
		return quotaValueType;
	}

	public void setQuotaValueType(Integer quotaValueType) {
		this.quotaValueType = quotaValueType;
	}

	public Integer getQuotaValuePercent() {
		return quotaValuePercent;
	}

	public void setQuotaValuePercent(Integer quotaValuePercent) {
		this.quotaValuePercent = quotaValuePercent;
	}
}
