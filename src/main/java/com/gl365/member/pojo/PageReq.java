package com.gl365.member.pojo;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;

public class PageReq implements Serializable{

	private static final long serialVersionUID = 2798848134998641365L;

	//当前页码
	private Integer curPage;
	
	//每页数量
	private Integer pageSize;
	
	//页码数量
	private Integer navigatePages;

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

	public Integer getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(Integer navigatePages) {
		this.navigatePages = navigatePages;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
