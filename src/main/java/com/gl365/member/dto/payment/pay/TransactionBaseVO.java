package com.gl365.member.dto.payment.pay;

public class TransactionBaseVO {

	private String operateType;

	private PayMain payMain;

	private PayReturn payReturn;

	private PayStream payStream;

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public PayMain getPayMain() {
		return payMain;
	}

	public void setPayMain(PayMain payMain) {
		this.payMain = payMain;
	}

	public PayReturn getPayReturn() {
		return payReturn;
	}

	public void setPayReturn(PayReturn payReturn) {
		this.payReturn = payReturn;
	}

	public PayStream getPayStream() {
		return payStream;
	}

	public void setPayStream(PayStream payStream) {
		this.payStream = payStream;
	}

}
