package com.gl365.member.pojo;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.enums.AdPutStatus;

public class AdPutUpdateReq implements Serializable{

	private static final long serialVersionUID = 1285463612253094703L;
	
	private Integer id;
	
	private AdPutStatus putState;
	
	private String province;
	
	private String city;
	
	private Integer place;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AdPutStatus getPutState() {
		return putState;
	}

	public void setPutState(AdPutStatus putState) {
		this.putState = putState;
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

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
