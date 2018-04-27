package com.gl365.member.dto.users;

public class UserForgotPwdDto {
	/**
	 * 绑定的手机号
	 */
	private String mobilePhone;
	/**
	 * 身份证号
	 */
	private String idCard;
	
	/**
	 * 密钥
	 */
	private String token;
	
	/**
	 * 密码
	 */
	private String password;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
