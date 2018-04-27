package com.gl365.member.dto;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;


public class SendSMSReq implements Serializable{
	
	private static final long serialVersionUID = -7918640510239388269L;

	private String phoneNo;
	
	/**
	 * 业务类型
	 * 0：注册
	 * 1：修改手机号给新手机号
	 * 2：登录
	 * 3：C端用户忘记密码
	 * 4：修改手机号给旧手机号
	 * 5：b端绑定操作员
	 * 6：b端设置银行卡
	 * 7：不论场景直接发送短信
	 */
	private Integer businessType;
	
	/**
	 * 预留字段，后面分语音短信和普通短信
	 */
	private String sendType;
	
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	
	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
}
