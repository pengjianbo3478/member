package com.gl365.member.dto.merchant.command;

/**
 * @author Administrator
 *
 */
public class GetUnComments4MemberCommand {

	/**
	 * 用户ID
	 */
	private String useId;

	/**
	 * 商户编号
	 */
	private String merchantNo;

	/**
	 * 当前页
	 */
	private Integer curPage;

	/**
	 * 页数据大小
	 */
	private Integer pageSize;

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
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
