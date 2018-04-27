package com.gl365.member.handler.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl365.member.common.enums.PaymentTypeEnum;
import com.gl365.member.handler.payment.impl.PaymentBackHandlerImpl;
import com.gl365.member.handler.payment.impl.PaymentErrorHandlerImpl;
import com.gl365.member.handler.payment.impl.PaymentRecoverHandlerImpl;
import com.gl365.member.handler.payment.impl.PaymentSucceeHandlerImpl;
import com.gl365.member.mq.producer.JPushProducer;
import com.gl365.member.mq.producer.MerchantCommentGradeProducer;
import com.gl365.member.service.CommentService;
import com.gl365.member.service.UserService;
import com.gl365.member.service.repo.MessageService;
import com.gl365.member.service.repo.OperatorInfoServiceRepo;

@Component
public class FactoryPaymentHandler {

	@Autowired
	UserService userService;
	@Autowired
	JPushProducer jPushProducer;
	@Autowired
	CommentService commentService;
	@Autowired
	OperatorInfoServiceRepo operatorInfoServiceRepo;
	@Autowired
	MerchantCommentGradeProducer merchantCommentGradeProducer;
	@Autowired
	MessageService messageService;

	public static PaymentTypeEnum EM;

	@SuppressWarnings("static-access")
	public PaymentHandler distribute(String payType) {
		AbstractPaymentHandler baseHandler;
		if (typeContain(payType, EM.POS_XF, EM.WSXF, EM.DSZF, EM.SQQR))
			/*** POS消费、网上消费、打赏支付、预授权确认完成 */
			baseHandler = new PaymentSucceeHandlerImpl();
		else if (typeContain(payType, EM.POS_XF_CZ, EM.WSXF_CZ, EM.POS_CX, EM.WSXF_CX, EM.SQQR_CZ, EM.POS_TH, EM.POS_BF_TH, EM.WSXF_TH, EM.WSXF_BF_TH))
			/*** POS消费冲正、网上消费冲正、POS撤销、网上消费撤销、授权确认撤销、POS部分退货、网上消费退货、网上消费部分退货 */
			baseHandler = new PaymentBackHandlerImpl();
		else if (typeContain(payType, EM.POS_CX_CZ, EM.WSXF_CX_CZ, EM.POS_TH_CZ))
			/*** POS撤销冲正、网上消费撤销冲正、POS退货冲正 */
			baseHandler = new PaymentRecoverHandlerImpl();
		else
			return new PaymentErrorHandlerImpl();
		baseHandler.userService = userService;
		baseHandler.jPushProducer = jPushProducer;
		baseHandler.commentService = commentService;
		baseHandler.operatorInfoServiceRepo = operatorInfoServiceRepo;
		baseHandler.merchantCommentGradeProducer = merchantCommentGradeProducer;
		baseHandler.messageService = messageService;
		return baseHandler;
	}

	private boolean typeContain(String payType, PaymentTypeEnum... enums) {
		for (PaymentTypeEnum type : enums) {
			if (type.getValue().equals(payType))
				return true;
		}
		return false;
	}

}
