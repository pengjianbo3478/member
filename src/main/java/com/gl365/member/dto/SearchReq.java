package com.gl365.member.dto;

import java.io.Serializable;

public class SearchReq implements Serializable{

	private static final long serialVersionUID = 288197881857747406L;
	
    private String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

}
