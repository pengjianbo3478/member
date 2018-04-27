package com.gl365.member.dto.payment.pay;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayMain implements Serializable {
	/**
	 * 给乐流水号
	 */
	private String payId;
	/**
	 * 请求流水号
	 */
	private String requestId;
	/**
	 * 请求交易日期
	 */
	private LocalDateTime requestDate;
	/**
	 * 原消费查询、原预授权完成查询的pay_id
	 */
	private String prePayId;
	/**
	 * 支付机构代码
	 */
	private String organCode;
	/**
	 * 支付机构商户号
	 */
	private String organMerchantNo;
	/**
	 * 给乐商户号
	 */
	private String merchantNo;
	/**
	 * 给乐商户名称
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
	 * 发展机构
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
	private String userDevManager;
	/**
	 * 发展会员商家员工
	 */
	private String userDevStaff;
	/**
	 * 省
	 */
	private Short province;
	/**
	 * 城市
	 */
	private Short city;
	/**
	 * 地区
	 */
	private Short district;
	/**
	 * 
	 */
	private String transType;
	/**
	 * 快捷支付
	 */
	private String scene;
	/**
	 * 订单标题
	 */
	private String merchantOrderTitle;

	/**
	 * 订单描述
	 */
	private String merchentOrderDesc;

	/**
	 * 商户订单号
	 */
	private String merchantOrderNo;

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
	 * 支付手续费
	 */
	private BigDecimal payFee;
	/**
	 * 现金支付金额
	 */
	private BigDecimal cashAmount;
	/**
	 * 乐豆金额
	 */
	private BigDecimal beanAmount;
	/**
	 * 乐币金额
	 */
	private BigDecimal coinAmount;
	/**
	 * 支付手续费率
	 */
	private BigDecimal payFeeRate;
	/**
	 * 支付手续费类型，D:借记卡，C：贷记卡
	 */
	private String payFeeType;
	/**
	 * 佣金率
	 */
	private BigDecimal commRate;
	/**
	 * 支付手续费上限值，借记卡有封顶值
	 */
	private BigDecimal maxPayFee;
	/**
	 * 营销费=返佣-支付手续费
	 */
	private BigDecimal marcketFee;
	/**
	 * 返佣金额
	 */
	private BigDecimal commAmount;
	/**
	 * 返利率
	 */
	private BigDecimal giftRate;

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
	 * 支付确认时间
	 */
	private LocalDateTime payTime;

	/**
	 * 交易状态
	 */
	private String payStatus;

	/**
	 * 交易描述
	 */
	private String payDesc;

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

	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

	public String getPrePayId() {
		return prePayId;
	}

	public void setPrePayId(String prePayId) {
		this.prePayId = prePayId;
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

	public String getUserDevManager() {
		return userDevManager;
	}

	public void setUserDevManager(String userDevManager) {
		this.userDevManager = userDevManager;
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

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getMerchantOrderTitle() {
		return merchantOrderTitle;
	}

	public void setMerchantOrderTitle(String merchantOrderTitle) {
		this.merchantOrderTitle = merchantOrderTitle;
	}

	public String getMerchentOrderDesc() {
		return merchentOrderDesc;
	}

	public void setMerchentOrderDesc(String merchentOrderDesc) {
		this.merchentOrderDesc = merchentOrderDesc;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
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

	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
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

	public BigDecimal getMarcketFee() {
		return marcketFee;
	}

	public void setMarcketFee(BigDecimal marcketFee) {
		this.marcketFee = marcketFee;
	}

	public BigDecimal getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(BigDecimal commAmount) {
		this.commAmount = commAmount;
	}

	public BigDecimal getGiftRate() {
		return giftRate;
	}

	public void setGiftRate(BigDecimal giftRate) {
		this.giftRate = giftRate;
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

	public LocalDateTime getPayTime() {
		return payTime;
	}

	public void setPayTime(LocalDateTime payTime) {
		this.payTime = payTime;
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

	@Override
	public String toString() {
		return "PayMain [payId=" + payId + ", requestId=" + requestId + ", requestDate=" + requestDate + ", prePayId=" + prePayId + ", organCode=" + organCode + ", organMerchantNo=" + organMerchantNo + ", merchantNo=" + merchantNo + ", merchantName=" + merchantName + ", terminal=" + terminal + ", operator=" + operator + ", merchantAgentNo=" + merchantAgentNo + ", userAgentType=" + userAgentType + ", userAgentNo=" + userAgentNo + ", userDevManager=" + userDevManager + ", userDevStaff=" + userDevStaff + ", province=" + province + ", city=" + city + ", district=" + district + ", transType=" + transType + ", scene=" + scene + ", merchantOrderTitle=" + merchantOrderTitle + ", merchentOrderDesc=" + merchentOrderDesc + ", merchantOrderNo=" + merchantOrderNo + ", userId=" + userId + ", userName=" + userName + ", cardIndex=" + cardIndex + ", totalAmount=" + totalAmount + ", noBenefitAmount=" + noBenefitAmount + ", payFee=" + payFee + ", cashAmount=" + cashAmount + ", beanAmount=" + beanAmount + ", coinAmount=" + coinAmount + ", payFeeRate=" + payFeeRate + ", payFeeType=" + payFeeType + ", commRate=" + commRate + ", maxPayFee=" + maxPayFee + ", marcketFee=" + marcketFee + ", commAmount=" + commAmount + ", giftRate=" + giftRate + ", giftAmount=" + giftAmount + ", giftPoint=" + giftPoint + ", merchantSettleAmount=" + merchantSettleAmount + ", payTime=" + payTime + ", payStatus=" + payStatus + ", payDesc=" + payDesc + ", createTime=" + createTime + ", createBy=" + createBy + ", modifyTime=" + modifyTime + ", modifyBy=" + modifyBy + ", getPayId()=" + getPayId() + ", getRequestId()=" + getRequestId() + ", getRequestDate()=" + getRequestDate() + ", getPrePayId()=" + getPrePayId() + ", getOrganCode()=" + getOrganCode() + ", getOrganMerchantNo()=" + getOrganMerchantNo() + ", getMerchantNo()=" + getMerchantNo() + ", getMerchantName()=" + getMerchantName() + ", getTerminal()=" + getTerminal() + ", getOperator()=" + getOperator() + ", getMerchantAgentNo()=" + getMerchantAgentNo() + ", getUserAgentType()=" + getUserAgentType() + ", getUserAgentNo()=" + getUserAgentNo() + ", getUserDevManager()=" + getUserDevManager() + ", getUserDevStaff()=" + getUserDevStaff() + ", getProvince()=" + getProvince() + ", getCity()=" + getCity() + ", getDistrict()=" + getDistrict() + ", getTransType()=" + getTransType() + ", getScene()=" + getScene() + ", getMerchantOrderTitle()=" + getMerchantOrderTitle() + ", getMerchentOrderDesc()=" + getMerchentOrderDesc() + ", getMerchantOrderNo()=" + getMerchantOrderNo() + ", getUserId()=" + getUserId() + ", getUserName()=" + getUserName() + ", getCardIndex()=" + getCardIndex() + ", getTotalAmount()=" + getTotalAmount() + ", getNoBenefitAmount()=" + getNoBenefitAmount() + ", getPayFee()=" + getPayFee() + ", getCashAmount()=" + getCashAmount() + ", getBeanAmount()=" + getBeanAmount() + ", getCoinAmount()=" + getCoinAmount() + ", getPayFeeRate()=" + getPayFeeRate() + ", getPayFeeType()=" + getPayFeeType() + ", getCommRate()=" + getCommRate() + ", getMaxPayFee()=" + getMaxPayFee() + ", getMarcketFee()=" + getMarcketFee() + ", getCommAmount()=" + getCommAmount() + ", getGiftRate()=" + getGiftRate() + ", getGiftAmount()=" + getGiftAmount() + ", getGiftPoint()=" + getGiftPoint() + ", getMerchantSettleAmount()=" + getMerchantSettleAmount() + ", getPayTime()=" + getPayTime() + ", getPayStatus()=" + getPayStatus() + ", getPayDesc()=" + getPayDesc() + ", getCreateTime()=" + getCreateTime() + ", getCreateBy()=" + getCreateBy() + ", getModifyTime()=" + getModifyTime() + ", getModifyBy()=" + getModifyBy() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}