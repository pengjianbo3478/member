package com.gl365.member.dto.users;

/**
 * < 登录指令 >
 * 
 * @author hui.li 2017年4月12日 - 下午1:05:55
 * @Since 1.0
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = 4098317368451844677L;

	/**
	 * 用户登录名
	 */
	private String username;

	/**
	 * 用户登录密码
	 */
	private String password;
	
	/**
	 * 设备名称 例Iphone6s 华为P9
	 */
	private String deviceName;	

	/**
	 * 操作系统及版本号 例ios 10.3 android 6.0
	 */
	private String deviceVersion;		

	/**
	 * 验证码 设备首次登录时需要。参考登录验证短信
	 */
	private String  verificationCode;

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

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

}
