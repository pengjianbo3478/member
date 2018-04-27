package com.gl365.member.dto.mq.push.app;

public class AppBody {

	private String tranTime;
	private String payId;
	private String origPayId;
	private String user;
	private String payType;
	private String payTypeName;
	private String merchant;
	private String merchantNo;
	private String totalAmt;
	private String cashAmt;
	private String beanAmt;
	private String giftAmt;
	private String type;
	private String title;
	private String billno;
	private String orderNo;
	private String orderType;
	private String refundOperatorName;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getOrigPayId() {
		return origPayId;
	}

	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getCashAmt() {
		return cashAmt;
	}

	public void setCashAmt(String cashAmt) {
		this.cashAmt = cashAmt;
	}

	public String getBeanAmt() {
		return beanAmt;
	}

	public void setBeanAmt(String beanAmt) {
		this.beanAmt = beanAmt;
	}

	public String getGiftAmt() {
		return giftAmt;
	}

	public void setGiftAmt(String giftAmt) {
		this.giftAmt = giftAmt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getRefundOperatorName() {
		return refundOperatorName;
	}

	public void setRefundOperatorName(String refundOperatorName) {
		this.refundOperatorName = refundOperatorName;
	}
}
