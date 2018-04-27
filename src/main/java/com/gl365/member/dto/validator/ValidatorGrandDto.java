package com.gl365.member.dto.validator;

import java.util.List;

public class ValidatorGrandDto {
	
	private String resCode;
	
	private String resMsg;
	
	private String sign;
	
	private List<ValidatorFatherDto> data;

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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public List<ValidatorFatherDto> getData() {
		return data;
	}

	public void setData(List<ValidatorFatherDto> data) {
		this.data = data;
	}
}
