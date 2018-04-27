package com.gl365.member.dto.users;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserSumFansDto {
	
    private String recommendBy;
    
    private String recommendAgentType;

    private String recommendAgentId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   	@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginTime; //注册开始时间
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endTime; //注册结束时间

	public String getRecommendBy() {
		return recommendBy;
	}

	public void setRecommendBy(String recommendBy) {
		this.recommendBy = recommendBy;
	}

	public String getRecommendAgentType() {
		return recommendAgentType;
	}

	public void setRecommendAgentType(String recommendAgentType) {
		this.recommendAgentType = recommendAgentType;
	}

	public String getRecommendAgentId() {
		return recommendAgentId;
	}

	public void setRecommendAgentId(String recommendAgentId) {
		this.recommendAgentId = recommendAgentId;
	}

	public LocalDate getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(LocalDate beginTime) {
		this.beginTime = beginTime;
	}

	public LocalDate getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
	}
}
