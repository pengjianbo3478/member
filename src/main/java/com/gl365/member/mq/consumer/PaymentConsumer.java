package com.gl365.member.mq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.dto.SecurityLog;
import com.gl365.member.dto.mq.payment.PaymentMQ;
import com.gl365.member.dto.mq.payment.model.PayMain;
import com.gl365.member.handler.payment.FactoryPaymentHandler;

/**
 * < 结账动作消费 >
 * 
 * @since hui.li 2017年5月10日 下午6:06:58
 */
@Component("payment-notify-consumer")
public class PaymentConsumer extends MQConsumer<PaymentMQ> {
	
	private static final Logger securityLog = LoggerFactory.getLogger("security Log");

	private static final Logger LOG = LoggerFactory.getLogger(PaymentConsumer.class);

	// 消息类型-推送通知
	private static final String MSG_LIMIT = "P";

	@Autowired
	private FactoryPaymentHandler factoryPaymentHandler;

	@Override
	public void execute(PaymentMQ command) {
		LOG.info("<mq-received>交易成功消费》》》入参：{}", JsonUtils.toJsonString(command));
		try {
			// 参数校验
			if (null == command || null == command.getPaymentBody() || !MSG_LIMIT.equals(command.getMsgCategory().trim()) || StringUtils.isEmpty(command.getTranType())) {
				LOG.warn("<mq-received>交易成功消费》》》警告,参数非法");
				return;
			}
			// 执行
			String type = command.getTranType();
			factoryPaymentHandler.distribute(type).build(command).push();
			try{
				securityLog.info(JsonUtils.toJsonString(SecurityLog.payMain2Log(command.getPaymentBody().getPayMain())));
			} catch (Exception e) {
				LOG.error("记录安全日志出错", e);
			}
		} catch (Exception e) {
			LOG.error("<mq-received>交易成功消费》》》错误：{}", e);
			throw new RuntimeException(e);
		}
	}


}
