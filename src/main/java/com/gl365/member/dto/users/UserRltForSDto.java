package com.gl365.member.dto.users;

public class UserRltForSDto {

	private String recommendAgentT; //机构类型    
	
	private String recommendAgentId; //机构ID  
	
	private Integer totalCount; //个数  

	public String getRecommendAgentT() {
		return recommendAgentT;
	}

	public void setRecommendAgentT(String recommendAgentT) {
		this.recommendAgentT = recommendAgentT;
	}

	public String getRecommendAgentId() {
		return recommendAgentId;
	}

	public void setRecommendAgentId(String recommendAgentId) {
		this.recommendAgentId = recommendAgentId;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
