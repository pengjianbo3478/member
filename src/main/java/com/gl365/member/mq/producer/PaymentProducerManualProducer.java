package com.gl365.member.mq.producer;

import java.math.BigDecimal;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl365.member.common.DBConstants;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.dto.comment.command.SaveCommentPersonal4MemberCommand;
import com.gl365.member.dto.merchant.command.SaveComment4MemberCommand;
import com.gl365.member.dto.payment.pay.PayManualDto;
import com.gl365.member.service.CommentService;

/**
 * < 模拟结账动作生产 >
 * 
 * @since hui.li 2017年5月10日 下午6:06:58
 */
@Component
@Deprecated
public class PaymentProducerManualProducer {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentProducerManualProducer.class);

	@Autowired
	private CommentService commentService;

	public void send(PayManualDto data) {
		LOG.info("<mq-send>模拟核心系统结账通知》》》入参：{}", JsonUtils.toJsonString(data));
		try {
			String paymentNo = UUID.randomUUID().toString().replace("-", "");
			// 保存商家初始评论
			saveMerchantComment(data, paymentNo);
			// 保存个人初始评论
			savePersonalComment(data, paymentNo);
		} catch (Exception e) {
			LOG.error("<mq-send>模拟核心系统结账通知》》》异常：{}", e);
		}
	}

	private void savePersonalComment(PayManualDto data, String paymentNo) {
		SaveCommentPersonal4MemberCommand command = new SaveCommentPersonal4MemberCommand();
		command.setPaymentNo(paymentNo);
		command.setMerchantNo(data.getMerchantNo());
		command.setUserId(data.getUserId());
		command.setGrade(BigDecimal.valueOf(DBConstants.MAX_GRADE));
		commentService.saveUpdateCommentPersonal(command);
	}

	private void saveMerchantComment(PayManualDto data, String paymentNo) {
		SaveComment4MemberCommand command = new SaveComment4MemberCommand();
		command.setPaymentNo(paymentNo);
		command.setMerchantNo(data.getMerchantNo());
		command.setUserId(data.getUserId());
		commentService.saveDefaultComment(command);
	}

}
