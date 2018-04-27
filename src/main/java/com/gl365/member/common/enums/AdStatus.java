package com.gl365.member.common.enums;

/**
 * 广告状态枚举
 * @author dfs_519
 *2017年7月25日下午12:22:35
 */
public enum AdStatus {
	
	WAIT_AUDIT(0,"等待审核"), PASS_AUDIT(1,"审核通过"), REFUSE(2,"审核不通过"), DELETE(3,"删除");

	private Integer value;
	
	private String desc;

	private AdStatus(Integer value, String desc) {
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
