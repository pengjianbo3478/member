package com.gl365.member.dto.users;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;

public class UserRealNameInfo implements Serializable{

	private static final long serialVersionUID = -8083729915091573580L;

	private String userId;

	private String realName;

	private String mobilePhone;

	private Integer certType; // 1身份证 2军官证 3护 照 4港澳通行证 5台胞证 6其它

	private String certNum;
	
	private Boolean fftRegister; //是否在付费通开户
	
	public Boolean getFftRegister() {
		return fftRegister;
	}

	public void setFftRegister(Boolean fftRegister) {
		this.fftRegister = fftRegister;
	}

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

	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
}
