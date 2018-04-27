package com.gl365.member.dto.payment.pay;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 退货信息
 */
public class PayReturn implements Serializable {

	/**
	 * 给乐流水号
	 */
	private String payId;

	/**
	 * 请求交易流水号
	 */
	private String requestId;

	/**
	 * 请求交易日期
	 */
	private LocalDate requestDate;

	/**
	 * 原交易给乐流水号
	 */
	private String origPayId;

	/**
	 * 原交易日期
	 */
	private LocalDate origPayDate;

	/**
	 * 支付机构代码
	 */
	private String organCode;

	/**
	 * 机构商户ID
	 */
	private String organMerchantNo;

	/**
	 * 商户ID
	 */
	private String merchantNo;

	/**
	 * 商户名称
	 */
	private String merchantName;

	/**
	 * 终端号
	 */
	private String terminal;

	/**
	 * 操作员
	 */
	private String operator;

	/**
	 * 发展商户机构
	 */
	private String merchantAgentNo;

	/**
	 * 发展会员机构类型(1代理商，2商家)
	 */
	private String userAgentType;

	/**
	 * 发展会员机构
	 */
	private String userAgentNo;

	/**
	 * 发展会员商家店长
	 */
	private String userDevMmanager;

	/**
	 * 发展会员商家员工
	 */
	private String userDevStaff;

	/**
	 * 商家所在省
	 */
	private Short province;

	/**
	 * 商家所在市
	 */
	private Short city;

	/**
	 * 商家所在区
	 */
	private Short district;

	/**
	 * 交易类型
	 */
	private String transType;

	/**
	 * 会员ID
	 */
	private String userId;

	/**
	 * 会员姓名
	 */
	private String userName;

	/**
	 * 绑卡索引号
	 */
	private String cardIndex;

	/**
	 * 交易总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 不可返利金额
	 */
	private BigDecimal noBenefitAmount;

	/**
	 * 现金支付金额
	 */
	private BigDecimal cashAmount;

	/**
	 * 乐豆
	 */
	private BigDecimal beanAmount;

	/**
	 * 乐币
	 */
	private BigDecimal coinAmount;

	/**
	 * 支付手续费类型
	 */
	private BigDecimal payFeeRate;

	/**
	 * 支付手续费
	 */
	private BigDecimal payFee;

	/**
	 * 支付手续费类型
	 */
	private String payFeeType;

	/**
	 * 佣金率
	 */
	private BigDecimal commRate;

	/**
	 * 支付手续费上限值
	 */
	private BigDecimal maxPayFee;

	/**
	 * 返利率
	 */
	private BigDecimal giftRate;

	/**
	 * 返佣金额
	 */
	private BigDecimal commAmount;

	/**
	 * 营销费
	 */
	private BigDecimal marcketFee;

	/**
	 * 返利金额
	 */
	private BigDecimal giftAmount;

	/**
	 * 赠送积分
	 */
	private BigDecimal giftPoint;

	/**
	 * 商户实得金额
	 */
	private BigDecimal merchantSettleAmount;

	/**
	 * 交易状态
	 */
	private String payStatus;

	/**
	 * 交易描述
	 */
	private String payDesc;

	/**
	 * 支付确认时间），为退货完成时间
	 */
	private LocalDateTime payTime;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 修改时间
	 */
	private LocalDateTime modifyTime;

	/**
	 * 修改人
	 */
	private String modifyBy;

	/** 给乐原单新状态 */
	private String payModifyStatus;

	/** 原交易状态变更时间 */
	private LocalDateTime payModifyTime;

	private static final long serialVersionUID = 1L;

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

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public String getOrigPayId() {
		return origPayId;
	}

	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId;
	}

	public LocalDate getOrigPayDate() {
		return origPayDate;
	}

	public void setOrigPayDate(LocalDate origPayDate) {
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

	public String getMerchantAgentNo() {
		return merchantAgentNo;
	}

	public void setMerchantAgentNo(String merchantAgentNo) {
		this.merchantAgentNo = merchantAgentNo;
	}

	public String getUserAgentType() {
		return userAgentType;
	}

	public void setUserAgentType(String userAgentType) {
		this.userAgentType = userAgentType;
	}

	public String getUserAgentNo() {
		return userAgentNo;
	}

	public void setUserAgentNo(String userAgentNo) {
		this.userAgentNo = userAgentNo;
	}

	public String getUserDevMmanager() {
		return userDevMmanager;
	}

	public void setUserDevMmanager(String userDevMmanager) {
		this.userDevMmanager = userDevMmanager;
	}

	public String getUserDevStaff() {
		return userDevStaff;
	}

	public void setUserDevStaff(String userDevStaff) {
		this.userDevStaff = userDevStaff;
	}

	public Short getProvince() {
		return province;
	}

	public void setProvince(Short province) {
		this.province = province;
	}

	public Short getCity() {
		return city;
	}

	public void setCity(Short city) {
		this.city = city;
	}

	public Short getDistrict() {
		return district;
	}

	public void setDistrict(Short district) {
		this.district = district;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
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

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getBeanAmount() {
		return beanAmount;
	}

	public void setBeanAmount(BigDecimal beanAmount) {
		this.beanAmount = beanAmount;
	}

	public BigDecimal getCoinAmount() {
		return coinAmount;
	}

	public void setCoinAmount(BigDecimal coinAmount) {
		this.coinAmount = coinAmount;
	}

	public BigDecimal getPayFeeRate() {
		return payFeeRate;
	}

	public void setPayFeeRate(BigDecimal payFeeRate) {
		this.payFeeRate = payFeeRate;
	}

	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}

	public String getPayFeeType() {
		return payFeeType;
	}

	public void setPayFeeType(String payFeeType) {
		this.payFeeType = payFeeType;
	}

	public BigDecimal getCommRate() {
		return commRate;
	}

	public void setCommRate(BigDecimal commRate) {
		this.commRate = commRate;
	}

	public BigDecimal getMaxPayFee() {
		return maxPayFee;
	}

	public void setMaxPayFee(BigDecimal maxPayFee) {
		this.maxPayFee = maxPayFee;
	}

	public BigDecimal getGiftRate() {
		return giftRate;
	}

	public void setGiftRate(BigDecimal giftRate) {
		this.giftRate = giftRate;
	}

	public BigDecimal getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(BigDecimal commAmount) {
		this.commAmount = commAmount;
	}

	public BigDecimal getMarcketFee() {
		return marcketFee;
	}

	public void setMarcketFee(BigDecimal marcketFee) {
		this.marcketFee = marcketFee;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public BigDecimal getGiftPoint() {
		return giftPoint;
	}

	public void setGiftPoint(BigDecimal giftPoint) {
		this.giftPoint = giftPoint;
	}

	public BigDecimal getMerchantSettleAmount() {
		return merchantSettleAmount;
	}

	public void setMerchantSettleAmount(BigDecimal merchantSettleAmount) {
		this.merchantSettleAmount = merchantSettleAmount;
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

	public LocalDateTime getPayTime() {
		return payTime;
	}

	public void setPayTime(LocalDateTime payTime) {
		this.payTime = payTime;
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

	@Override
	public String toString() {
		return "PayReturn [payId=" + payId + ", requestId=" + requestId + ", requestDate=" + requestDate + ", origPayId=" + origPayId + ", origPayDate=" + origPayDate + ", organCode=" + organCode + ", organMerchantNo=" + organMerchantNo + ", merchantNo=" + merchantNo + ", merchantName=" + merchantName + ", terminal=" + terminal + ", operator=" + operator + ", merchantAgentNo=" + merchantAgentNo + ", userAgentType=" + userAgentType + ", userAgentNo=" + userAgentNo + ", userDevMmanager=" + userDevMmanager + ", userDevStaff=" + userDevStaff + ", province=" + province + ", city=" + city + ", district=" + district + ", transType=" + transType + ", userId=" + userId + ", userName=" + userName + ", cardIndex=" + cardIndex + ", totalAmount=" + totalAmount + ", noBenefitAmount=" + noBenefitAmount + ", cashAmount=" + cashAmount + ", beanAmount=" + beanAmount + ", coinAmount=" + coinAmount + ", payFeeRate=" + payFeeRate + ", payFee=" + payFee + ", payFeeType=" + payFeeType + ", commRate=" + commRate + ", maxPayFee=" + maxPayFee + ", giftRate=" + giftRate + ", commAmount=" + commAmount + ", marcketFee=" + marcketFee + ", giftAmount=" + giftAmount + ", giftPoint=" + giftPoint + ", merchantSettleAmount=" + merchantSettleAmount + ", payStatus=" + payStatus + ", payDesc=" + payDesc + ", payTime=" + payTime + ", createTime=" + createTime + ", createBy=" + createBy + ", modifyTime=" + modifyTime + ", modifyBy=" + modifyBy + ", payModifyStatus=" + payModifyStatus + ", payModifyTime=" + payModifyTime + "]";
	}

}