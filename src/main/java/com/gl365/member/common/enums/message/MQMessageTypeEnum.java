package com.gl365.member.common.enums.message;

public enum MQMessageTypeEnum {
	
	MSG_00("00","支付类消息"),
	
	MSG_01("01","账单类消息"),
	
	MSG_02("02","系统公告类消息"),
	
	MSG_03("03","激活粉丝类消息"),

	MSG_04("04","进件相关类消息"),
	
	;
	
	private final String code;
	private final String desc;

	private MQMessageTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}
}
