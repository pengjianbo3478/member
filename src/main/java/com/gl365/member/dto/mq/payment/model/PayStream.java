package com.gl365.member.dto.mq.payment.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayStream implements Serializable {

	private static final long serialVersionUID = -2982546632608485415L;

	/**
	 * 给乐流水号
	 */
	private String payId;
	/**
	 * 请求流水号
	 */
	private String requestId;
	// /**
	// * 请求交易日期
	// */
	// @JsonFormat(pattern = "yyyy-MM-dd")
	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	// private LocalDate requestDate;
	/**
	 * 原请求流水号
	 */
	private String origRequestId;
	/**
	 * 原交易日期
	 */
	// @JsonFormat(pattern = "yyyy-MM-dd")
	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	// private LocalDate origPayDate;
	/**
	 * 支付机构代码
	 */
	private String organCode;
	/**
	 * 机构商户号
	 */
	private String organMerchantNo;
	/**
	 * 终端号
	 */
	private String terminal;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 交易类型
	 */
	private String transType;
	/**
	 * 交易总金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 退货金额
	 */
	private BigDecimal returnAmount;
	/**
	 * 处理状态
	 */
	private String dealStatus;
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// private LocalDateTime createTime;
	private String createBy;
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// private LocalDateTime modifyTime;
	private String modifyBy;
	private String uniqueSerial;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getOrigRequestId() {
		return origRequestId;
	}

	public void setOrigRequestId(String origRequestId) {
		this.origRequestId = origRequestId;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getOrganMerchantNo() {
		return organMerchantNo;
	}

	public void setOrganMerchantNo(String organMerchantNo) {
		this.organMerchantNo = organMerchantNo;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getUniqueSerial() {
		return uniqueSerial;
	}

	public void setUniqueSerial(String uniqueSerial) {
		this.uniqueSerial = uniqueSerial;
	}

}