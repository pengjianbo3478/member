package com.gl365.member.dto.users;

/**
 * < 会员实名认证指令 >
 * 
 * @since hui.li 2017年5月22日 下午4:17:20
 */
public class CertificationCommand {

	private String userId;

	private Integer authStatus;
	
	private String idCard;//身份证号码
	
	private String name;//真实姓名
	
	private String sex;//性别
	
	private String birth;//生日
	
	private String address;//地址

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
