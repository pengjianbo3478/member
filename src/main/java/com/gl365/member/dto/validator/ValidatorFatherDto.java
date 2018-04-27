package com.gl365.member.dto.validator;

public class ValidatorFatherDto {

	private String resCode;
	
	private String resMsg;
	
	private ValidatorSonDto quotaInfo;

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public ValidatorSonDto getQuotaInfo() {
		return quotaInfo;
	}

	public void setQuotaInfo(ValidatorSonDto quotaInfo) {
		this.quotaInfo = quotaInfo;
	}
}
