package com.gl365.member.dto.payment.pay;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayStream implements Serializable {
	private String payId;

	private String requestId;

	private LocalDateTime requestDate;

	private String origRequestId;

	private LocalDateTime origPayDate;

	private String organCode;

	private String organMerchantNo;

	private String terminal;

	private String operator;

	private String transType;

	private BigDecimal totalAmount;

	private BigDecimal returnAmount;

	private String dealStatus;

	private LocalDateTime createTime;

	private String createBy;

	private LocalDateTime modifyTime;

	private String modifyBy;

	private static final long serialVersionUID = 1L;

	/** 给乐原单id */
	private String origPayId;

	/** 给乐原单新状态 */
	private String payModifyStatus;

	/** 原交易状态变更时间 */
	private LocalDateTime payModifyTime;

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

	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

	public String getOrigRequestId() {
		return origRequestId;
	}

	public void setOrigRequestId(String origRequestId) {
		this.origRequestId = origRequestId;
	}

	public LocalDateTime getOrigPayDate() {
		return origPayDate;
	}

	public void setOrigPayDate(LocalDateTime origPayDate) {
		this.origPayDate = origPayDate;
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

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public LocalDateTime getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getOrigPayId() {
		return origPayId;
	}

	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId;
	}

	public String getPayModifyStatus() {
		return payModifyStatus;
	}

	public void setPayModifyStatus(String payModifyStatus) {
		this.payModifyStatus = payModifyStatus;
	}

	public LocalDateTime getPayModifyTime() {
		return payModifyTime;
	}

	public void setPayModifyTime(LocalDateTime payModifyTime) {
		this.payModifyTime = payModifyTime;
	}

}