package com.gl365.member.dto.merchant;

public class InviteUserResp {


	private String userName;// 姓名
	private String mobile;// 手机号
	private String avatarUrl;// 头像地址
	private String registDate;// 注册时间或消费时间
	private Integer registMonth;// 注册时间月份
	private String operatorName;// 推荐人名称
	private String status;// 0推荐 1收藏

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public Integer getRegistMonth() {
		return registMonth;
	}

	public void setRegistMonth(Integer registMonth) {
		this.registMonth = registMonth;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
