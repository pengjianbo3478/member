package com.gl365.member.dto.ad.req;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;

public class AdmainReq implements Serializable{

	private static final long serialVersionUID = 3673420830714622442L;

	private String adName;
	
	private Integer state;

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
}
