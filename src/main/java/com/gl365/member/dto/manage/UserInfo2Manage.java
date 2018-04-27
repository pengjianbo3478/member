package com.gl365.member.dto.manage;

import com.gl365.member.model.User;

import io.swagger.annotations.ApiModelProperty;

public class UserInfo2Manage extends User{

	private static final long serialVersionUID = 6707684153350369488L;

	@ApiModelProperty(value = "微信Id", example = "123", required = true)
	private String openId;
	
	@ApiModelProperty(value = "支付宝Id", example = "123", required = true)
	private String aliPayId;
	
	@ApiModelProperty(value = "渠道", example = "wx(微信)或zfb(支付宝)", required = true)
	private String channel;
	
	@ApiModelProperty(value = "是否绑定", example = "y(已绑定),n(未绑定)", required = true)
	private String isBind;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAliPayId() {
		return aliPayId;
	}

	public void setAliPayId(String aliPayId) {
		this.aliPayId = aliPayId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}
}
