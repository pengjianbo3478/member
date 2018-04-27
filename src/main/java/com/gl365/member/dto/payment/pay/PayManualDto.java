package com.gl365.member.dto.payment.pay;

/**
 * < 模拟交易成功的回调对象用来生成初始评论 >
 * 
 * @since hui.li 2017年5月10日 下午8:11:33
 */
@Deprecated
public class PayManualDto {

	/**
	 * 商家编号
	 */
	private String merchantNo;

	/**
	 * 用户ID
	 */
	private String userId;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
