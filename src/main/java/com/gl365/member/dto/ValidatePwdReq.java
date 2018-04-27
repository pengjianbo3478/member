package com.gl365.member.dto;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;

public class ValidatePwdReq implements Serializable{

	private static final long serialVersionUID = -4651679489718492384L;
	
	private String userId;
	
	private String pwd;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
