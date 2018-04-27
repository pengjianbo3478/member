package com.gl365.member.common.enums;

public enum OperatorRoleTypeEnum {

	ADMIN(1, "管理员");

	private Integer value;

	private String desc;

	private OperatorRoleTypeEnum(Integer value, String desc) {
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
