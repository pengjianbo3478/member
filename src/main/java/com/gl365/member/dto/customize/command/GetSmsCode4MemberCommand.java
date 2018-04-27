package com.gl365.member.dto.customize.command;

import java.io.Serializable;

/**
 * < 请求短信验证码指令 >
 * 
 * @author hui.li 2017年4月27日 - 下午12:13:11
 * @Since 1.0
 */
public class GetSmsCode4MemberCommand implements Serializable {

	private static final long serialVersionUID = -8268856957162771736L;

	/**
	 * 短信接收手机号
	 */
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
