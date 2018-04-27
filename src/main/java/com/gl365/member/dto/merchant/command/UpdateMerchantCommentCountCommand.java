package com.gl365.member.dto.merchant.command;

import com.gl365.member.dto.merchant.MerchantOrder;
import com.gl365.member.dto.mq.MQCommand;

public class UpdateMerchantCommentCountCommand extends MQCommand {

	private static final long serialVersionUID = -2916917032645946491L;

	private String merchantNo;

	private Integer count;

	private Integer score;

	private MerchantOrder order;

	public UpdateMerchantCommentCountCommand() {
		super();
	}

	public UpdateMerchantCommentCountCommand(String merchantNo, Integer score, Integer count) {
		super(true);
		this.merchantNo = merchantNo;
		this.score = score;
		this.count = count;
	}

	public UpdateMerchantCommentCountCommand(MerchantOrder order) {
		super(true);
		this.order = order;
	}

	public MerchantOrder getOrder() {
		return order;
	}

	public void setOrder(MerchantOrder order) {
		this.order = order;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
