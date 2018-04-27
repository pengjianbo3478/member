package com.gl365.member.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SmsCountLimit implements Serializable{

	private static final long serialVersionUID = 3536165876783678982L;
	
	private String phoneNo;
	
	private Integer count;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime beginTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastSendTime;
	
	public SmsCountLimit(String phoneNo, Integer count, LocalDateTime beginTime, LocalDateTime lastSendTime) {
		super();
		this.phoneNo = phoneNo;
		this.count = count;
		this.beginTime = beginTime;
		this.lastSendTime = lastSendTime;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public LocalDateTime getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(LocalDateTime beginTime) {
		this.beginTime = beginTime;
	}

	public LocalDateTime getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(LocalDateTime lastSendTime) {
		this.lastSendTime = lastSendTime;
	}
	
}
