package com.gl365.member.common.enums.manage;

/**
 * <机构类型枚举 >
 * 
 * @author xty
 */
public enum AgentTypeEnum {
	/**
	 * 总公司（实际业务中不存在此类型代理）
	 */
	PLATFORM(0),

	/**
	 * 省级代理
	 */
	PROVINCE(1),

	/**
	 * 市级代理
	 */
	CITY(2),

	/**
	 * 县级代理
	 */
	COUNTY(3),

	/**
	 * 业务员机构
	 */
	PERSONAL(4),

	/**
	 * 联盟商家商户
	 */
	ALLIANCE_MERCHANT(5),

	/**
	 * 员工,店长,会员
	 */
	MEMBER_MERCHANT(6),

	/**
	 * 企业协会
	 */
	ASSOCIATION(7),

	/**
	 * 积分机构
	 */
	INTERGRAL(8),

	/**
	 * 电子商城
	 */
	ELECTRONIC_EMPORIUM(9),

	/**
	 * 积分商城
	 */
	JFSC(10),

	/**
	 * 政府机构
	 */
	ZFJG(11),

	/**
	 * 银行机构
	 */
	YHJG(12),

	/**
	 * 支付公司
	 */
	ZFGS(13),

	/**
	 * 服务公司
	 */
	FWGS(14),

	/**
	 * 奖励账户
	 */
	JLZH(15);

	private int value;

	private AgentTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
