package com.gl365.member.dto.config;

import java.io.Serializable;
import java.util.List;

public class IndustryDto implements Serializable {

	private static final long serialVersionUID = 7256218448777282709L;

	private Integer id;

	private String name;

	private List<IndustryDto> sub;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<IndustryDto> getSub() {
		return sub;
	}

	public void setSub(List<IndustryDto> sub) {
		this.sub = sub;
	}

}