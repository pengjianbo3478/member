package com.gl365.member.pojo;

import com.gl365.member.common.enums.AdStatus;

public class AdMainQueryReq extends PageReq{

	private static final long serialVersionUID = 2488856920460946159L;

	private String adName;
	
	private AdStatus state;

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public AdStatus getState() {
		return state;
	}

	public void setState(AdStatus state) {
		this.state = state;
	}
	
}
