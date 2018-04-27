package com.gl365.member.dto.merchant.command;

public class GetUserFavorites4MemberCommand {

	/**
	 * 用户Id
	 */
	private String userId;
	/**
	 * 当前页
	 */
	private Integer curPage;

	/**
	 * 页数据大小
	 */
	private Integer pageSize;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

}
