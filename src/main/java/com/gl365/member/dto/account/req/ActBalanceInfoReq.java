package com.gl365.member.dto.account.req;

import java.io.Serializable;

public class ActBalanceInfoReq implements Serializable{

	private static final long serialVersionUID = 7463155360644870773L;

	private String userId;
	
	private String agentId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
}
