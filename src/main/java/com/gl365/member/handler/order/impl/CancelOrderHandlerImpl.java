package com.gl365.member.handler.order.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.MaskUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.common.enums.PaymentTypeEnum;
import com.gl365.member.common.enums.message.MQMessageTypeEnum;
import com.gl365.member.common.enums.message.MQSystemTypeEnum;
import com.gl365.member.dto.MsgReq;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.merchant.MerchantInfo2Pay;
import com.gl365.member.dto.mq.push.PushMQ;
import com.gl365.member.dto.mq.push.app.AppBody;
import com.gl365.member.dto.mq.push.app.SystemBody;
import com.gl365.member.dto.mq.push.app.SystemContent;
import com.gl365.member.dto.payment.pay.CancelOrderDto;
import com.gl365.member.handler.order.CancelOrderHandler;
import com.gl365.member.mapper.UserMapper;
import com.gl365.member.model.User;
import com.gl365.member.mq.producer.JPushProducer;
import com.gl365.member.service.repo.MerchantInfoServiceRepo;
import com.gl365.member.service.repo.MessageService;
import com.gl365.member.web.CancelOrderController;

/**
 * 撤单处理类
 * 
 * @author dfs_508 2017年10月18日 下午6:10:37
 */
@Service
public class CancelOrderHandlerImpl implements CancelOrderHandler {

	private static final Logger logger = LoggerFactory.getLogger(CancelOrderController.class);

	@Autowired
	private MessageService messageService;

	@Autowired
	private JPushProducer jPushProducer;
	
	@Autowired
	private MerchantInfoServiceRepo merchantInfoServiceRepo;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public ResultDto<?> cancelorder(CancelOrderDto command, String title) {
		//组装商户名
		if(StringUtils.isBlank(command.getMerchantName()) && StringUtils.isNotBlank(command.getMerchantNo())){
			List<String> list = new ArrayList<>();
			list.add(command.getMerchantNo());
			List<MerchantInfo2Pay> info = merchantInfoServiceRepo.getMerchantByMerchantNoList(list);
			if(!CollectionUtils.isEmpty(info)){
					command.setMerchantName(info.get(0).getMerchantShortname());
				}
		}
		//组装用户名
		if(StringUtils.isNotBlank(command.getUserName())){
			User user = userMapper.selectByPrimaryKey(command.getUserName());
			if(user != null){
				command.setUserName(StringUtils.isBlank(user.getRealName())? user.getNickName() : MaskUtils.nameMask(user.getRealName()));
			}
		}
		
		PushMQ pushMQ = new PushMQ();
		String systemType = MQSystemTypeEnum.PAYMENT_APP.getCode();
		pushMQ.setApp("cfront");
		pushMQ.setUid(command.getAlias());
		pushMQ.setContent(getContent(command, systemType, title));
		buildMsg(pushMQ, MQMessageTypeEnum.MSG_00.getCode(), systemType);
		logger.info("========cancelorder=====>begin,param={}", JsonUtils.toJsonString(pushMQ));
		jPushProducer.send(pushMQ);
		logger.info("========cancelorder=====>end");
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
	}

	// 生成消息内容
	private String getContent(CancelOrderDto command, String systemType, String title) {
		String tranType = PaymentTypeEnum.WSXF_CX.getValue();
		String payType = "04";// 4和5是微信公众号(H5)支付
		String payTypeName = "微信支付";// 4和5是微信公众号(H5)支付
		AppBody body = new AppBody();
		body.setTranTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		body.setType(tranType);
		body.setTitle(title);
		body.setPayType(payType);
		body.setPayTypeName(payTypeName);
		body.setOrderType(empty(command.getOrderType()));
		body.setMerchantNo(empty(command.getMerchantNo()));
		body.setBillno(empty(command.getBillno()));
		body.setOrderNo(empty(command.getOrderNo()));
		body.setPayId(empty(command.getPayId()));
		body.setUser(empty(command.getUserName()));
		body.setOrigPayId(empty(command.getOrigPayId()));
		body.setMerchant(empty(command.getMerchantName()));
		body.setBeanAmt(command.getBeanAmt());
		body.setCashAmt(command.getCashAmt());
		body.setGiftAmt(command.getGiftAmt());
		body.setTotalAmt(command.getTotalAmt());
		body.setRefundOperatorName(empty(command.getRefundOperatorId()));
		SystemBody systemBody = new SystemBody(tranType, body);
		SystemContent result = new SystemContent(systemType, systemBody);
		return JsonUtils.toJsonString(result);
	}

	// 写message服务
	private void buildMsg(PushMQ command, String messageType, String messageResultType) {
		MsgReq req = new MsgReq();
		req.setAlias(command.getUid());
		req.setAppType(command.getApp());
		req.setMessage(command.getContent());
		req.setMessageType(messageType);
		req.setMessageResultType(messageResultType);
		logger.info("========cancelorder addMsg=====>begin,param={}", JsonUtils.toJsonString(req));
		messageService.addMsg(req);
		logger.info("========cancelorder addMsg=====>end");
	}

	// 空指针转换
	private String empty(String source) {
		return StringUtils.isEmpty(source) ? "" : source;
	}
}
