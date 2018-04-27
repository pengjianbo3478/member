package com.gl365.member.dto.mq.push;

public class PushBody {
	private String retTime;
	private String origPayId;
	private String user;
	private String payType;
	private String merchant;
	private String totalAmt;
	private String retCashAmt;
	private String retBeanAmt;
	private String type;
	private String title;
	private String billno;

	public String getRetTime() {
		return retTime;
	}

	public void setRetTime(String retTime) {
		this.retTime = retTime;
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

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getRetCashAmt() {
		return retCashAmt;
	}

	public void setRetCashAmt(String retCashAmt) {
		this.retCashAmt = retCashAmt;
	}

	public String getRetBeanAmt() {
		return retBeanAmt;
	}

	public void setRetBeanAmt(String retBeanAmt) {
		this.retBeanAmt = retBeanAmt;
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

}
