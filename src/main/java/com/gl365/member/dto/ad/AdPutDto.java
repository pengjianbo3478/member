package com.gl365.member.dto.ad;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.gl365.member.common.JsonUtils;

public class AdPutDto implements Serializable{

	private static final long serialVersionUID = -7183445122188600653L;
	
	private Integer id;
	
	private Integer adMainID;
	
	private LocalDateTime startTime;
	
	private LocalDateTime endTime;
	
	//投放位置：0启动页，1首页banner
	private Integer place;
	
	//投放描述
	private String putDetail;
	
	private String province;
	
	private String city;
	
	//投放状态：1正常投放0解除投放
	private Integer putState;
	
	private String adName;
	
	private String remark;

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

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public String getPutDetail() {
		return putDetail;
	}

	public void setPutDetail(String putDetail) {
		this.putDetail = putDetail;
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

	public Integer getPutState() {
		return putState;
	}

	public void setPutState(Integer putState) {
		this.putState = putState;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}

}
