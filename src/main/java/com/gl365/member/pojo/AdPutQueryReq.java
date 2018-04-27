package com.gl365.member.pojo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.member.common.enums.AdPutStatus;

public class AdPutQueryReq extends PageReq{

	private static final long serialVersionUID = -6810118864968385527L;

	private Integer id; 

	private String adName;
	
	private String province;
	
	private String city;
	
	private Integer place;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime queryBegin;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime queryEnd;
	
	private AdPutStatus putState;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

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

	public AdPutStatus getPutState() {
		return putState;
	}

	public void setPutState(AdPutStatus putState) {
		this.putState = putState;
	}
	
}
