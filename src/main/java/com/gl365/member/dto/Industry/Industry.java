package com.gl365.member.dto.Industry;

import java.io.Serializable;

public class Industry implements Serializable {

	private static final long serialVersionUID = 6915008608472074805L;

	private Integer id;

	private Integer parentId;

	private String name;

	private String level;

	private String flag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}