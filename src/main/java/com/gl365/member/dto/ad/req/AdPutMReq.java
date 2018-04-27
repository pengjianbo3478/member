package com.gl365.member.dto.ad.req;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.gl365.member.common.JsonUtils;

public class AdPutMReq implements Serializable{

	private static final long serialVersionUID = -1983124800341591557L;
	
	private Integer id; 

	private String adName;
	
	private String province;
	
	private String city;
	
	private Integer place;
	
	private LocalDateTime queryBegin;
	
	private LocalDateTime queryEnd;
	
	private Integer putState;

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

	public Integer getPutState() {
		return putState;
	}

	public void setPutState(Integer putState) {
		this.putState = putState;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
