package com.gl365.member.dto.mq.payment;

import java.util.List;

import com.gl365.member.dto.mq.payment.model.PayDetail;
import com.gl365.member.dto.mq.payment.model.PayMain;
import com.gl365.member.dto.mq.payment.model.PayPrepay;
import com.gl365.member.dto.mq.payment.model.PayReturn;
import com.gl365.member.dto.mq.payment.model.PayStream;

public class PaymentBody {
	private PayMain payMain;
	private PayReturn payReturn;
	private PayStream payStream;
	private List<PayDetail> payDetails;
	private PayPrepay payPrepay;
	private String payModifyStatus;

	/**
	 * @return the payMain
	 */
	public PayMain getPayMain() {
		return payMain;
	}

	/**
	 * @param payMain
	 *            the payMain to set
	 */
	public void setPayMain(PayMain payMain) {
		this.payMain = payMain;
	}

	/**
	 * @return the payReturn
	 */
	public PayReturn getPayReturn() {
		return payReturn;
	}

	/**
	 * @param payReturn
	 *            the payReturn to set
	 */
	public void setPayReturn(PayReturn payReturn) {
		this.payReturn = payReturn;
	}

	/**
	 * @return the payStream
	 */
	public PayStream getPayStream() {
		return payStream;
	}

	/**
	 * @param payStream
	 *            the payStream to set
	 */
	public void setPayStream(PayStream payStream) {
		this.payStream = payStream;
	}

	/**
	 * @return the payPrepay
	 */
	public PayPrepay getPayPrepay() {
		return payPrepay;
	}

	/**
	 * @param payPrepay
	 *            the payPrepay to set
	 */
	public void setPayPrepay(PayPrepay payPrepay) {
		this.payPrepay = payPrepay;
	}

	/**
	 * @return the payModifyStatus
	 */
	public String getPayModifyStatus() {
		return payModifyStatus;
	}

	/**
	 * @param payModifyStatus
	 *            the payModifyStatus to set
	 */
	public void setPayModifyStatus(String payModifyStatus) {
		this.payModifyStatus = payModifyStatus;
	}

	/**
	 * @return the payDetails
	 */
	public List<PayDetail> getPayDetails() {
		return payDetails;
	}

	/**
	 * @param payDetails
	 *            the payDetails to set
	 */
	public void setPayDetails(List<PayDetail> payDetails) {
		this.payDetails = payDetails;
	}
}
