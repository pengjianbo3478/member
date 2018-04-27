package com.gl365.member.service;

public interface RedisService {
	
	public abstract void set(String key, Object value, Long liveTime);
	
	public abstract void setStringValue(String key, String value, long liveTime);
	
	public abstract String getStringValue(String key);
	
	public abstract void del(String key);
	
	public abstract Object get(String key);
	
}
