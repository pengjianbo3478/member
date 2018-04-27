package com.gl365.member.dto.users;

import java.io.Serializable;

public class UserRedisDto implements Serializable{

private static final long serialVersionUID = 1L;
	
	private String key;
	
	private String value;
	
	private Long liveTime;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Long liveTime) {
		this.liveTime = liveTime;
	}
	
}
