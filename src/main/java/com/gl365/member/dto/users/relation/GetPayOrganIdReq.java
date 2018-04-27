package com.gl365.member.dto.users.relation;

import io.swagger.annotations.ApiModelProperty;

public class GetPayOrganIdReq {

	@ApiModelProperty("必填")
	private String userId;

	@ApiModelProperty("必填,第三方支付渠道。wx：微信，zfb：支付宝")
	private String channel;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
