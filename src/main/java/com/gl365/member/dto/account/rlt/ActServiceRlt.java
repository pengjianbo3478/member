package com.gl365.member.dto.account.rlt;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;

public class ActServiceRlt implements Serializable{

	private static final long serialVersionUID = 3084457887446191172L;

	private String resultCode;
	
	private String resultDesc;
	
	public String getResultCode() {
		return resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
