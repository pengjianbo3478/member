package com.gl365.member.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.enums.AdPutStatus;

public class AdPutCommand implements Serializable{

	private static final long serialVersionUID = -4742474956635043333L;

	private Integer id; 
	
	private Integer adMainID;

	private String adName;
	
	/**
	 * 广告投放省份，多个逗号分隔
	 */
	private String provinces;
	
	/**
	 * 广告投放城市，多个逗号分隔
	 */
	private String citys;
	
	private Integer place;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;
	
	private AdPutStatus putState;
	
	//投放描述
	private String putDetail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdMainID() {
		return adMainID;
	}

	public void setAdMainID(Integer adMainID) {
		this.adMainID = adMainID;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getProvinces() {
		return provinces;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public String getCitys() {
		return citys;
	}

	public void setCitys(String citys) {
		this.citys = citys;
	}

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public AdPutStatus getPutState() {
		return putState;
	}

	public void setPutState(AdPutStatus putState) {
		this.putState = putState;
	}
	
	public String getPutDetail() {
		return putDetail;
	}

	public void setPutDetail(String putDetail) {
		this.putDetail = putDetail;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
