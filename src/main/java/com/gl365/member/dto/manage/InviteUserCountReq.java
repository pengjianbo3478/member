package com.gl365.member.dto.manage;

import java.util.List;


public class InviteUserCountReq {

	private List<String> recommendBys;// 推荐人userId
	
	private String merchantNo;// 商户号
	
	public List<String> getRecommendBys() {
		return recommendBys;
	}

	public void setRecommendBys(List<String> recommendBys) {
		this.recommendBys = recommendBys;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
}
