package com.gl365.member.dto.merchant;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * < 商户用户评论Dto >
 * 
 * @author hui.li 2017年4月24日 - 下午1:47:22
 * @Since 1.0
 */
public class CommentDto implements Serializable {

	private static final long serialVersionUID = -476273815540069764L;

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
	 * 内容
	 */
	private String content;

	/**
	 * 支付订单编号
	 */
	private String paymentNo;
	
	/**
	 * 用户真实姓名
	 */
	private String nickName;

	/**
	 * 评论标签集合
	 */
	private Integer[] labels;

	public Integer[] getLabels() {
		return labels;
	}

	public void setLabels(Integer[] labels) {
		this.labels = labels;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}