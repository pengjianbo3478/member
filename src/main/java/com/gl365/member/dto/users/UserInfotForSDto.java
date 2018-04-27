package com.gl365.member.dto.users;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserInfotForSDto {
	
	private String userId;

    private String realName;

    private String mobilePhone;
    
    private String photo;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public LocalDateTime getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(LocalDateTime registerTime) {
		this.registerTime = registerTime;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
