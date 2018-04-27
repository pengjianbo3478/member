package com.gl365.member.common.enums.manage;

/**
 * <用户注册枚举 >
 * 
 * @author xty
 */
public enum RegistTypeEnum {

	APP_REGIST(0, "APP注册"), 
	SCAN_REGIST(1, "扫码注册"), 
	AVTIVE_REGIST(2, "活动注册"), 
	CARD_REGIST(3, "卡号注册"), 
	MESSAGE_REGIST(4, "短信注册"), 
	OTHER_REGIST(5, "第三方系统转入"), 
	WECHAT_REGIST(6, "微信注册"),
	;

	private Integer value;

	private String desc;

	private RegistTypeEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
