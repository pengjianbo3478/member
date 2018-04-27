package com.gl365.member.common.enums.message;

public enum MQSystemTypeEnum {

	GL_SALE("gl_sale"),
	
	PAYMENT_APP("payment_app"),
	
	MONTHLY_BILLS("monthly_bills"),
	
	GL_PAYMENT("gl_payment"),
	
	;

	private String code;

	private MQSystemTypeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
