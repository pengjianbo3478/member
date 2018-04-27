package com.gl365.member.dto.payment.pay;

import org.apache.commons.lang3.StringUtils;

public enum PaySceneEnum {

	QUICK_PAYMENT_SCENE("00", "直接支付(快捷支付)"), 
	B2C_PAYMENT_SCENE("01", "B扫C支付"), 
	C2B_PAYMENT_SCENE("02", "C扫B支付"), 
	POS_PAYMENT_SCENE("03", "POS支付"), 
	//设计要求这个都叫微信支付,交易那边仍然不变
	// WX_QUICK_PAYMENT_SCENE("04", "微信直接支付"),
	// APP_PAYMENT_SCENE("05", "给乐APP发起微信支付"),
	WX_QUICK_PAYMENT_SCENE("04", "微信支付"), 
	APP_PAYMENT_SCENE("05", "微信支付"),
	;

	private final String code;
	private final String desc;

	private PaySceneEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}

	public static String getDescByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return code;
		}
		for (PaySceneEnum e : values()) {
			if (e.getCode().equals(code)) {
				return e.getDesc();
			}
		}
		return code;
	}
}
