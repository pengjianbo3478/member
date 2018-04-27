package com.gl365.member.dto.merchant.command;

public class GetMerchantCommentsCommand {

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 商户编号
	 */
	private String merchantNo;

	/**
	 * 是否评论
	 */
	private boolean unComment;

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

	public boolean isUnComment() {
		return unComment;
	}

	public void setUnComment(boolean unComment) {
		this.unComment = unComment;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
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
