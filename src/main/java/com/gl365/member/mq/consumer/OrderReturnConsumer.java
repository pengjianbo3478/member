package com.gl365.member.mq.consumer;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl365.aliyun.ons.OnsListener;
import com.gl365.member.common.GsonUtils;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.mq.order.OrderMain;
import com.gl365.member.dto.mq.order.OrderRefund;
import com.gl365.member.dto.payment.pay.CancelOrderDto;
import com.gl365.member.dto.users.MerchantOperatorDto;
import com.gl365.member.service.repo.OperatorInfoServiceRepo;
import com.gl365.member.web.CancelOrderController;

@Component("order-return-notify-consumer")
public class OrderReturnConsumer implements OnsListener {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CancelOrderController cancelOrderController;
	
	@Autowired
	private OperatorInfoServiceRepo operatorInfoServiceRepo;

	@Override
	public void receive(byte[] arg0) {
		String message=new String(arg0);
		logger.info("========order-return-notify-consumer=====>receive:{}", message);
		try {
			OrderMain command = GsonUtils.getGson().fromJson(message, OrderMain.class);
			logger.info("========order-return-notify-consumer=====>", JsonUtils.toJsonString(command));
			if(null != command){
				OrderRefund orderRefund = command.getRefund();
				logger.info("========order-return-notify-consumer=====>orderRefund={}", JsonUtils.toJsonString(orderRefund));
				if (null != orderRefund) {
					if ("refundIng".equals(command.getTranType())) {
						// 退款发起消息
						logger.info("========order-return-notify-consumer=====>refundIng,id={},orderRefund={}", orderRefund.getMemberId(), JsonUtils.toJsonString(orderRefund));
						cancelOrderController.cancelordering(handleCancelOrderData(orderRefund));
					} else if ("refund".equals(command.getTranType())) {
						// 退款完成消息
						logger.info("========order-return-notify-consumer=====>refund,id={},orderRefund={}", orderRefund.getMemberId(), JsonUtils.toJsonString(orderRefund));
						cancelOrderController.cancelorder(handleCancelOrderData(orderRefund));
					} else {
						// 其他消息
						logger.info("========order-return-notify-consumer=====>receive message not refund type,message={}", message);
					}
				}
			}else{
				logger.info("========order-return-notify-consumer=====>receive message not refund type,message={}", message);
			}
		} catch(Exception e) {
			logger.error("========order-return-notify-consumer=====>deal failure {}", e);
		}
	}
	
	private CancelOrderDto handleCancelOrderData(OrderRefund orderRefund){
		CancelOrderDto cancelOrderDto = new CancelOrderDto();
		cancelOrderDto.setAlias(orderRefund.getMemberId());
		String origOrderSn = orderRefund.getOrigOrderSn();
		if(StringUtils.isNotBlank(origOrderSn)){
			cancelOrderDto.setOrderType("1");
			if(origOrderSn.startsWith("5") || origOrderSn.startsWith("6")){
				cancelOrderDto.setOrderType("7");
			}
		}
		cancelOrderDto.setMerchantNo(orderRefund.getMerchantNo());
		cancelOrderDto.setBillno(orderRefund.getOrderSn());
		cancelOrderDto.setOrderNo(orderRefund.getOrigOrderSn());
		cancelOrderDto.setUserName(orderRefund.getMemberId());
		cancelOrderDto.setOperator(orderRefund.getOperatorId());
		cancelOrderDto.setRefundOperatorId(getoperatorName(orderRefund.getRefundOperatorId()));
		cancelOrderDto.setBeanAmt(empty(orderRefund.getBeanAmount()));
		cancelOrderDto.setGiftAmt(empty(orderRefund.getGiftAmount()));
		cancelOrderDto.setCashAmt(empty(orderRefund.getCashAmount()));
		cancelOrderDto.setTotalAmt(empty(orderRefund.getTotalAmount()));
		cancelOrderDto.setPayId(null);
		cancelOrderDto.setOrigPayId(null);
		cancelOrderDto.setMerchantName(null);
		logger.info("========order-return-notify-consumer=====>handleCancelOrderData={}", JsonUtils.toJsonString(cancelOrderDto));
		return cancelOrderDto;
	}
	
	private String getoperatorName(String operatorId) {
		String rlt = "";
		if(StringUtils.isNotBlank(operatorId)){
			ResultDto<MerchantOperatorDto> result = operatorInfoServiceRepo.queryOperatorByOperatorId(operatorId);
			if((ResultCodeEnum.System.SUCCESS.getCode().equals(result.getResult())) || (null != result.getData())){
				rlt = result.getData().getOperatorName();
			}
		}
		return rlt;
	}

	// 空指针转换
	private String empty(BigDecimal source) {
		String rlt = "";
		if(null != source){
			rlt = String.valueOf(source);
		}
		return rlt;
	}
}
