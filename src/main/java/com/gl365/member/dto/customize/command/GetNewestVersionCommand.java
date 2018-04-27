package com.gl365.member.dto.customize.command;

/**
 * < 获取最新App版本指令 >
 * 
 * @author hui.li 2017年5月8日 - 上午11:02:01
 * @Since 1.0
 */
public class GetNewestVersionCommand {

	private String appVersion;// APP的版本

	private String clientId;// 客户端ID

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
