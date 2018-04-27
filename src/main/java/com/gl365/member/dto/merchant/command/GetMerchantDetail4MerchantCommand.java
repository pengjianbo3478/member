package com.gl365.member.dto.merchant.command;

import java.util.List;

public class GetMerchantDetail4MerchantCommand {
	/**
	 * 商家编号 , 支持批量
	 */
	private List<String> merchantNo;

	public List<String> getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(List<String> merchantNo) {
		this.merchantNo = merchantNo;
	}
}
