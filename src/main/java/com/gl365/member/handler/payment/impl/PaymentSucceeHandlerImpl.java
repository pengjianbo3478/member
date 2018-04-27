package com.gl365.member.handler.payment.impl;

import com.gl365.member.common.enums.PaymentOrderTypeEnum;
import com.gl365.member.handler.payment.AbstractPaymentHandler;
import com.gl365.member.handler.payment.PaymentHandler;

/**
 * < 交易确认 >
 * 
 * @since hui.li 2017年5月26日 下午6:28:25
 */
public class PaymentSucceeHandlerImpl extends AbstractPaymentHandler {

	@Override
	public PaymentHandler execute() {
		if (PaymentOrderTypeEnum.NORMAL.getValue().equals(orderType) || PaymentOrderTypeEnum.EXPERT.getValue().equals(orderType) || PaymentOrderTypeEnum.ONLINE_SHOPPING.getValue().equals(orderType)) {
			/* 更新初次交易标识 */
			// updateUserTransFlag();
		}
		return this;
	}

}
