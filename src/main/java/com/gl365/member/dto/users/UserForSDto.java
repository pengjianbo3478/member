package com.gl365.member.dto.users;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserForSDto {

	private String recommendAgentT; //机构类型    
	
	private String recommendAgentId; //机构ID  
	
	private String mobilePhone; //用户手机 
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
   	@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginTime; //注册开始时间
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endTime; //注册结束时间
	
	private Integer curPage; //当前页  
	
	private Integer pageSize; //页面大小  
	
	private List<String> recommendAgentIdList; 

	public String getRecommendAgentT() {
		return recommendAgentT;
	}

	public void setRecommendAgentT(String recommendAgentT) {
		this.recommendAgentT = recommendAgentT;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRecommendAgentId() {
		return recommendAgentId;
	}

	public void setRecommendAgentId(String recommendAgentId) {
		this.recommendAgentId = recommendAgentId;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<String> getRecommendAgentIdList() {
		return recommendAgentIdList;
	}

	public void setRecommendAgentIdList(List<String> recommendAgentIdList) {
		this.recommendAgentIdList = recommendAgentIdList;
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
