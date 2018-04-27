package com.gl365.member.dto.users;

import java.io.Serializable;
/**
 * < 基础指令  >
 *   	
 * 包含常规请求携带的参数
 * @author hui.li 2017年4月12日 - 下午1:07:08
 * @Since  1.0
 */
public class Command implements Serializable{

	private static final long serialVersionUID = 4595251210060842443L;
	
	/**
	 * 	Token ： 保持登录状态的凭证
	 */
	private String token;
	
	/**
	 * 	DeviceId : 客户端设备ID 
	 *  如手机的IMEI
	 */
	private String deviceId;
	
	/**
	 * 	ClientId : 客户端类型ID
	 */
	private String clientId;
	
	/**
	 * 	ClientVer : 客户端软件版本号
	 * 	格式是X.X.X。
	 *  命名规则是：大版本号.子版本号.补丁版本号
	 */
	private String clientVer;
	
	/**
	 * 	Timestamp : 时间戳
	 */
	private String timestamp;
	
	/**
	 * 	Sign : 签名,基于RSA加密算法
	 */
	private String sign;
	
	/**
	 * 	SessionId : 用户身份的会话控制ID
	 */
	private String sessionId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientVer() {
		return clientVer;
	}

	public void setClientVer(String clientVer) {
		this.clientVer = clientVer;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	
}
