package com.gl365.member.dto.manage;

public class MerchantInfoForMDto {
	
	/**
	 * 查询字段
	 */
	private String planName;
	
	/**
	 * 每页记录数
	 */
	private Integer numPerPage;
	
	/**
	 * 页码
	 */
	private Integer pageNum;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
}
