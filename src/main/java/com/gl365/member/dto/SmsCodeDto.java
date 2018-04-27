package com.gl365.member.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.member.common.JsonUtils;

/**
 * redis存放短信验证码DTO
 * @author dfs_519
 * 2017年4月28日上午10:49:59
 */
public class SmsCodeDto implements Serializable{

	private static final long serialVersionUID = 7492606397962488030L;
	
	private String phoneNo;
	
	private String verifyCode;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime sendTime;
	
	private Integer expireTime; //有效期（秒）

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public LocalDateTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
}
