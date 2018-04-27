package com.gl365.member.dto.users.command;

public class GetOperatorListCommand {

	private String merchantNo;

	private Integer[] roldId;

	public GetOperatorListCommand() {
		super();
	}

	public GetOperatorListCommand(String merchantNo, Integer... roldId) {
		super();
		this.merchantNo = merchantNo;
		this.roldId = roldId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Integer[] getRoldId() {
		return roldId;
	}

	public void setRoldId(Integer[] roldId) {
		this.roldId = roldId;
	}

}
