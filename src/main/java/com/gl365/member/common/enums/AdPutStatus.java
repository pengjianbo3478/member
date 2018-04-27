package com.gl365.member.common.enums;

/**
 * 广告投放状态枚举
 * @author dfs_519
 *2017年7月25日下午2:02:17
 */
public enum AdPutStatus {
	
	NORMAL(1,"正常投放"), CANCEL(2,"取消投放");

	private Integer value;
	
	private String desc;

	private AdPutStatus(Integer value, String desc) {
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
