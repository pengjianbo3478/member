package com.gl365.member.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.payment.pay.PayManualDto;
import com.gl365.member.mq.producer.PaymentProducerManualProducer;

import io.swagger.annotations.ApiOperation;

@Deprecated
@RestController
@RequestMapping("/manual")
public class ManualController {

	private static final Logger LOG = LoggerFactory.getLogger(ManualController.class);

	@Autowired
	private PaymentProducerManualProducer paymentProducerManualProducer;

	@ApiOperation(value = "模拟交易接口,用来生成初始评论")
	@PostMapping("/payment")
	public Object paymentByManual(@RequestBody PayManualDto command) {
		LOG.info("手动模拟交易接口》》》入参：{}", JsonUtils.toJsonString(command));
		try {
			paymentProducerManualProducer.send(command);
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, 0);
		} catch (Exception e) {
			LOG.error("手动模拟交易接口》》》失败：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

}
