package com.gl365.member.dto.merchant;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * < 未评论订单Dto >
 * 
 * @author hui.li 2017年5月5日 - 上午11:41:44
 * @Since 1.0
 */
public class UnCommentDto implements Serializable {

	private static final long serialVersionUID = 5477145713109535200L;

	private String paymentNo;// 支付订单号

	private String merchantNo;// 商户编号

	private String merchantName;// 商户名称

	private String paymentDate;// 支付日期

	private String scene;// 支付场景 01 线上支付 02 线下支付（POS支付）

	private BigDecimal money;// 支付金额

	private boolean comment;// 是否已经发表评论

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public boolean isComment() {
		return comment;
	}

	public void setComment(boolean comment) {
		this.comment = comment;
	}

}
