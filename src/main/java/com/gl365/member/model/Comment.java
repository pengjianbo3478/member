package com.gl365.member.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * < 商户评论Model >
 * 
 * @author hui.li 2017年4月24日 - 下午1:47:22
 * @Since 1.0
 */
/**
 * @author DELL
 *
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = -8605235904249497307L;

	private String id;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 商户编号
	 */
	private String merchantNo;

	/**
	 * 评分时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	/**
	 * 评分 0~5
	 */
	private Integer grade;

	/**
	 * 消费金额
	 */
	private BigDecimal account;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 支付订单编号
	 */
	private String paymentNo;

	/**
	 * '评论状态[0默认用户还没有评论] [1用户真实评论]'
	 */
	private Integer status;

	/**
	 * '删除标志[0可用] [1删除]'
	 */
	private Boolean delFlag;

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}