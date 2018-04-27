package com.gl365.member.model;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;

public class ExternalAccount implements Serializable{

	private static final long serialVersionUID = -3123226308563536686L;
	
	private String userId;
	
	private String externalChannel;

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExternalChannel() {
		return externalChannel;
	}

	public void setExternalChannel(String externalChannel) {
		this.externalChannel = externalChannel;
	}

}
