package com.gl365.member.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gl365.member.dto.mq.payment.model.PayMain;

public class SecurityLog implements  Serializable {
	
	private String logtype="payment";
	
	private LocalDateTime operation_time=LocalDateTime.now();
	
	private String src_ip;
	//会员id 填写：会员UID 
	private String Operation_UID;
	//机构代码
	private String organCode;
	//填写：标识账号名
	private String operation_user;
	//填写：系统生成的该笔交易的唯一标识号
	private String payId;
	//原交易流水
	private String origPayId;
	//付费通流水号
	private String requestId;
	//原付费通请求流水号
	private String origRequestId;
	//原交易日期
	private LocalDateTime origTxnDate;
	//交易类型
	private String trans_type;
	//订单标题
	private String merchantOrderTitle;
	//订单描述
	private String merchantOrderDesc;
	//消费金额
	private BigDecimal totalAmount;
    //	不可返利金额
	private BigDecimal noBenefitAmount;
	//营销费
	private BigDecimal marketFee;
	//乐豆
	private BigDecimal beanAmount;
	//实扣金额
	private BigDecimal cashMoney;
	//赠送金额
	private BigDecimal giftAmount;
	//返回代码
	private String payStatus;
	//返回描述
	private String payDesc;
	public String getLogtype() {
		return logtype;
	}
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	public LocalDateTime getOperation_time() {
		return operation_time;
	}
	public void setOperation_time(LocalDateTime operation_time) {
		this.operation_time = operation_time;
	}
	public String getSrc_ip() {
		return src_ip;
	}
	public void setSrc_ip(String src_ip) {
		this.src_ip = src_ip;
	}
	public String getOperation_UID() {
		return Operation_UID;
	}
	public void setOperation_UID(String operation_UID) {
		Operation_UID = operation_UID;
	}
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	public String getOperation_user() {
		return operation_user;
	}
	public void setOperation_user(String operation_user) {
		this.operation_user = operation_user;
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
	public LocalDateTime getOrigTxnDate() {
		return origTxnDate;
	}
	public void setOrigTxnDate(LocalDateTime origTxnDate) {
		this.origTxnDate = origTxnDate;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public String getMerchantOrderTitle() {
		return merchantOrderTitle;
	}
	public void setMerchantOrderTitle(String merchantOrderTitle) {
		this.merchantOrderTitle = merchantOrderTitle;
	}
	public String getMerchantOrderDesc() {
		return merchantOrderDesc;
	}
	public void setMerchantOrderDesc(String merchantOrderDesc) {
		this.merchantOrderDesc = merchantOrderDesc;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getNoBenefitAmount() {
		return noBenefitAmount;
	}
	public void setNoBenefitAmount(BigDecimal noBenefitAmount) {
		this.noBenefitAmount = noBenefitAmount;
	}
	public BigDecimal getMarketFee() {
		return marketFee;
	}
	public void setMarketFee(BigDecimal marketFee) {
		this.marketFee = marketFee;
	}
	public BigDecimal getBeanAmount() {
		return beanAmount;
	}
	public void setBeanAmount(BigDecimal beanAmount) {
		this.beanAmount = beanAmount;
	}
	public BigDecimal getCashMoney() {
		return cashMoney;
	}
	public void setCashMoney(BigDecimal cashMoney) {
		this.cashMoney = cashMoney;
	}
	public BigDecimal getGiftAmount() {
		return giftAmount;
	}
	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayDesc() {
		return payDesc;
	}
	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	
	public static SecurityLog payMain2Log(PayMain pay){
		SecurityLog log=new SecurityLog();
		log.setPayId(pay.getPayId());
		log.setOperation_UID(null!=pay.getOperator()?pay.getOperator():"");
		log.setOrganCode(null!=pay.getOrganCode()?pay.getOrganCode():"");
		
		log.setPayId(pay.getMerchantNo());
		log.setBeanAmount(null!=pay.getBeanAmount()?pay.getBeanAmount():null);

		log.setCashMoney(null!=pay.getCashAmount()?pay.getCashAmount():null);
		
		log.setGiftAmount(null!=pay.getGiftAmount()?pay.getGiftAmount():null);

		log.setMerchantOrderTitle(null!=pay.getMerchantOrderTitle()?pay.getMerchantOrderTitle():null);
		log.setOrigPayId(null!=pay.getOrigPayId()?pay.getOrigPayId():null);
		log.setOperation_user(null!=pay.getUserName()?pay.getUserName():null);
		log.setTrans_type(pay.getTransType());
		log.setTotalAmount(pay.getTotalAmount());
		log.setMarketFee(null!=pay.getMarcketFee()?pay.getMarcketFee():null);
		
		return log;
		
	}
}
