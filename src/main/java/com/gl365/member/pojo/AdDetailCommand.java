package com.gl365.member.pojo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.member.common.JsonUtils;

public class AdDetailCommand extends PageReq{

	private static final long serialVersionUID = 7401788795890452151L;

	private String city;
	
	private int place;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate queryData;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public LocalDate getQueryData() {
		return queryData;
	}

	public void setQueryData(LocalDate queryData) {
		this.queryData = queryData;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
