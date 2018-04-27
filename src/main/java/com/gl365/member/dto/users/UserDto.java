package com.gl365.member.dto.users;

import java.io.Serializable;

public class UserDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String username;
	
	private String password;
	
	private String oldPassword;
	
	private String newPassword;
	
	private String idCard;
	
	private String mobilePhone;
	
	private String verificationCode;
	
	private String name;
	
	private String url;
	
	private Integer value;
	
	private String oldMobilePhone;
	
	private String oldverificationCode;
	
	private String newMobilePhone;
	
	private String newverificationCode;
	
	private String curPage;
	
	private String pageSize;
	
	private String deviceId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getOldMobilePhone() {
		return oldMobilePhone;
	}

	public void setOldMobilePhone(String oldMobilePhone) {
		this.oldMobilePhone = oldMobilePhone;
	}

	public String getOldverificationCode() {
		return oldverificationCode;
	}

	public void setOldverificationCode(String oldverificationCode) {
		this.oldverificationCode = oldverificationCode;
	}

	public String getNewMobilePhone() {
		return newMobilePhone;
	}

	public void setNewMobilePhone(String newMobilePhone) {
		this.newMobilePhone = newMobilePhone;
	}

	public String getNewverificationCode() {
		return newverificationCode;
	}

	public void setNewverificationCode(String newverificationCode) {
		this.newverificationCode = newverificationCode;
	}

	public String getCurPage() {
		return curPage;
	}

	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	
}
