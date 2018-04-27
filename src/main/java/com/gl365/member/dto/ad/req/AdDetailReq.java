package com.gl365.member.dto.ad.req;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AdDetailReq implements Serializable{

	private static final long serialVersionUID = 1462046928793213648L;

	private String city;
	
	private Integer place;
	
	private LocalDateTime queryBegin;
	
	private LocalDateTime queryEnd;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public LocalDateTime getQueryBegin() {
		return queryBegin;
	}

	public void setQueryBegin(LocalDateTime queryBegin) {
		this.queryBegin = queryBegin;
	}

	public LocalDateTime getQueryEnd() {
		return queryEnd;
	}

	public void setQueryEnd(LocalDateTime queryEnd) {
		this.queryEnd = queryEnd;
	}
	
}
