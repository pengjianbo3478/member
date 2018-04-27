package com.gl365.member.handler.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.StringUtils;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.enums.CommentPersonalStatusEnum;
import com.gl365.member.common.enums.PaymentTypeEnum;
import com.gl365.member.common.enums.message.MQMessageTypeEnum;
import com.gl365.member.common.enums.message.MQSystemTypeEnum;
import com.gl365.member.dto.MsgReq;
import com.gl365.member.dto.comment.command.SaveCommentPersonal4MemberCommand;
import com.gl365.member.dto.merchant.MerchantOrder;
import com.gl365.member.dto.merchant.command.SaveComment4MemberCommand;
import com.gl365.member.dto.merchant.command.UpdateComment4MemberCommand;
import com.gl365.member.dto.merchant.command.UpdateMerchantCommentCountCommand;
import com.gl365.member.dto.mq.payment.PaymentMQ;
import com.gl365.member.dto.mq.payment.model.PayMain;
import com.gl365.member.dto.mq.payment.model.PayReturn;
import com.gl365.member.dto.mq.payment.model.PayStream;
import com.gl365.member.dto.mq.push.PushMQ;
import com.gl365.member.dto.mq.push.app.AppBody;
import com.gl365.member.dto.mq.push.app.SystemBody;
import com.gl365.member.dto.mq.push.app.SystemContent;
import com.gl365.member.dto.payment.pay.PaySceneEnum;
import com.gl365.member.mq.producer.JPushProducer;
import com.gl365.member.mq.producer.MerchantCommentGradeProducer;
import com.gl365.member.service.CommentService;
import com.gl365.member.service.UserService;
import com.gl365.member.service.repo.MessageService;
import com.gl365.member.service.repo.OperatorInfoServiceRepo;

/**
 * < 交易通知基础处理 >
 * 
 * @since hui.li 2017年5月27日 上午10:22:47
 */
public abstract class AbstractPaymentHandler implements PaymentHandler {

	protected String tranType, preTranType/* 原订单交易类型 */, paymentNo, prePaymentNo, merchantNo, orderType, billNo, orderNo, userId;

	protected BigDecimal account;

	protected PayMain payMain;/* 交易订单 */

	protected PayReturn payReturn;/* 退货订单 */

	protected PayStream payStream;/* 订单（撤销） */

	/* new instance by distribute */
	public UserService userService;

	public JPushProducer jPushProducer;

	public CommentService commentService;

	public OperatorInfoServiceRepo operatorInfoServiceRepo;

	public MerchantCommentGradeProducer merchantCommentGradeProducer;

	public MessageService messageService;
	// 核心字段构建
	@Override
	public PaymentHandler build(PaymentMQ command) {
		payMain = command.getPaymentBody().getPayMain();
		tranType = preTranType = command.getTranType(); // 当前订单交易类型，原订单交易类型默与当前订单一致
		orderType = payMain.getOrderType().trim();// 订单类型1：正常订单（默认为1）2：打赏订单3：达人订单4:网购订单7:群买单
		merchantNo = payMain.getMerchantNo();// 商户编号
		// operator = getOperator(); // 商家操作员ID
		userId = payMain.getUserId(); // 会员ID
		billNo = payMain.getRequestId(); // 用户订单号
		orderNo = payMain.getMerchantOrderNo(); // 给乐订单号
		paymentNo = payMain.getPayId(); // 当前支付订单
		account = payMain.getTotalAmount(); // 全部交易金额
		// 部分退货bug修改czw
		if (PaymentTypeEnum.containTH(tranType)) {
			payReturn = command.getPaymentBody().getPayReturn();
			preTranType = payMain.getTransType(); // 原订单交易类型,从原订单体中获取
			billNo = payReturn.getRequestId(); // 用户订单号
			paymentNo = payReturn.getPayId(); // 当前支付订单
			prePaymentNo = payReturn.getOrigPayId();// 原交易订单号
			account = payReturn.getTotalAmount(); // 全部交易金额
		}
		if (PaymentTypeEnum.containCX(tranType)) {
			payStream = command.getPaymentBody().getPayStream();
			preTranType = payMain.getTransType(); // 原订单交易类型,从原订单体中获取
			prePaymentNo = payMain.getPayId();// 原交易订单号取原订单信息
			billNo = payStream.getRequestId(); // 用户订单号
			paymentNo = payStream.getPayId(); // 当前支付订单
			account = payMain.getTotalAmount(); // 全部交易金额
		}
		return this;
	}

	// 推送通知会员
	@Override
	public void push() {
		if (PaymentTypeEnum.containCZ(tranType))
			return;
		PushMQ command = new PushMQ();
		command.setApp("cfront");
		command.setUid(userId);
		command.setContent(getContent(MQSystemTypeEnum.PAYMENT_APP.getCode()));
		buildMsg(command,MQMessageTypeEnum.MSG_00.getCode(),MQSystemTypeEnum.PAYMENT_APP.getCode());
		jPushProducer.send(command);
	}

	
	// 获取商家管理员
	// private String getOperator() {
	// operator = payMain.getOperator();
	// if (PaymentTypeEnum.containPOS(preTranType) ||
	// StringUtils.isEmpty(operator)) { // 退货,操作员为空时取管理员
	// ResultDto<List<MerchantOperatorDto>> result =
	// operatorInfoServiceRepo.getMerchantOpertatorList(new
	// GetOperatorListCommand(merchantNo,
	// OperatorRoleTypeEnum.ADMIN.getValue()));
	// if (null == result || CollectionUtils.isEmpty(result.getData())) {
	// LOG.warn("警告 取商家下管理员失败》》》报文不合法：{}", JsonUtils.toJsonString(result));
	// return operator;
	// }
	// MerchantOperatorDto operatorDto = result.getData().get(0);
	// if (null == operatorDto) {
	// LOG.warn("警告 取商家下管理员失败》》》管理员不存在, 报文:{}", JsonUtils.toJsonString(result));
	// return operator;
	// }
	// operator = operatorDto.getOperatorId();
	// }
	// return operator;
	// }

	// 保存商家评论
	protected void saveMerchantComment() {
		SaveComment4MemberCommand command = new SaveComment4MemberCommand(userId, merchantNo, paymentNo, account);
		commentService.saveDefaultComment(command);
	}
	
	//写message服务
	private void buildMsg(PushMQ command, String messageType, String messageResultType) {
		MsgReq req = new MsgReq();
		req.setAlias(command.getUid());
		req.setAppType(command.getApp());
		req.setMessage(command.getContent());
		req.setMessageType(messageType);
		req.setMessageResultType(messageResultType);
		messageService.addMsg(req);
	}

	// 保存打赏评论
	protected void savePersonalComment() {
		SaveCommentPersonal4MemberCommand command = new SaveCommentPersonal4MemberCommand();
		command.setUserId(userId);
		command.setMerchantNo(merchantNo);
		command.setPaymentNo(paymentNo); // 原支付订单号
		command.setStatus(CommentPersonalStatusEnum.NONE.getValue());
		commentService.saveUpdateCommentPersonal(command);
	}

	// 回滚打赏评论
	protected void backPersonalComment() {
		SaveCommentPersonal4MemberCommand command = new SaveCommentPersonal4MemberCommand(userId, merchantNo, paymentNo);
		command.setPaymentNo(prePaymentNo);// 原支付订单号
		command.setStatus(CommentPersonalStatusEnum.ING.getValue());// 打赏中
		commentService.saveUpdateCommentPersonal(command);
	}

	// 完成打赏评论
	protected void donePersonalComment() {
		SaveCommentPersonal4MemberCommand command = new SaveCommentPersonal4MemberCommand(userId, merchantNo, paymentNo);
		command.setPaymentNo(prePaymentNo);// 原支付订单号
		command.setStatus(CommentPersonalStatusEnum.DONE.getValue());// 打赏成功
		commentService.saveUpdateCommentPersonal(command);
	}

	// 删除打赏评论
	protected void removePersonalComment() {
		SaveCommentPersonal4MemberCommand command = new SaveCommentPersonal4MemberCommand(userId, merchantNo, paymentNo);
		command.setPaymentNo(prePaymentNo);// 原支付订单号
		command.setDelete(true);
		commentService.saveUpdateCommentPersonal(command);
	}

	// 还原打赏评论
	protected void unRemovePersonalComment() {
		SaveCommentPersonal4MemberCommand command = new SaveCommentPersonal4MemberCommand(userId, merchantNo, paymentNo);
		command.setPaymentNo(prePaymentNo);// 原支付订单号
		command.setDelete(false);
		commentService.saveUpdateCommentPersonal(command);
	}

	// 回滚商家评论
	protected void backMerchantComment() {
		UpdateComment4MemberCommand command = new UpdateComment4MemberCommand(userId, merchantNo, prePaymentNo);
		command.setDelete(true);
		commentService.backRecoverComment(command);
	}

	// 恢复商家评论
	protected void recoverMerchantComment() {
		UpdateComment4MemberCommand command = new UpdateComment4MemberCommand(userId, merchantNo, prePaymentNo);
		command.setDelete(false);
		commentService.backRecoverComment(command);
	}

	// 更新会员初次交易标识
	@Deprecated
	protected void updateUserTransFlag() {
		if (StringUtils.isNotEmpty(userId))
			userService.updateUserTransFlag(userId);
	}

	// 保存商家账单
	@Deprecated
	protected void saveMerchantOrder() {
		MerchantOrder order = new MerchantOrder(userId, merchantNo, paymentNo);
		merchantCommentGradeProducer.send(new UpdateMerchantCommentCountCommand(order));
	}

	// 更新商家账单
	@Deprecated
	protected void updateMerchantOrder() {
		MerchantOrder order = new MerchantOrder(userId, merchantNo, paymentNo);
		// order.setOrderType(isDelete ? DBConstants.CX : DBConstants.XF);
		merchantCommentGradeProducer.send(new UpdateMerchantCommentCountCommand(order));
	}

	// 生成消息内容
	private String getContent(String systemType) {
		AppBody body = new AppBody();
		body.setTranTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		body.setType(empty(tranType));
		body.setMerchantNo(merchantNo);
		body.setBillno(empty(billNo));
		body.setOrderNo(empty(orderNo));
		body.setOrderType(empty(orderType));
		body.setPayId(empty(paymentNo));
		body.setTitle(empty(getTitle()));
		body.setUser(empty(payMain.getUserName()));
		body.setPayType(empty(payMain.getScene()));
		body.setPayTypeName(empty(PaySceneEnum.getDescByCode(payMain.getScene())));
		body.setOrigPayId(empty(prePaymentNo));
		body.setMerchant(empty(payMain.getMerchantName()));
		
		//交易系统群买单与其它不同,要特殊处理。区分群买单和其它订单
		String beanAmt = empty(payMain.getBeanAmount()); // 个人扣豆
		String cashAmt = empty(payMain.getCashAmount()); // 个人现金支付
		String giftAmt = empty(payMain.getGiftAmount()); // 个人返豆
		String totalAmt = empty(payMain.getTotalAmount());// 个人支付总金额
		if("7".equals(orderType)){
			if("0".equals(payMain.getSplitFlag())){
				beanAmt = empty(payMain.getGroupMainuserPayBean());
				cashAmt = empty(payMain.getGroupMainuserPay().subtract(payMain.getGroupMainuserPayBean()));
				giftAmt = empty(payMain.getGroupGiftAmount());
				totalAmt = empty(payMain.getGroupMainuserPay());
			}else if("1".equals(payMain.getSplitFlag())){
				giftAmt = empty(payMain.getGroupGiftAmount());
			}
		}
		body.setBeanAmt(beanAmt); // 个人扣豆
		body.setCashAmt(cashAmt); // 个人现金支付
		body.setGiftAmt(giftAmt); // 个人返豆
		body.setTotalAmt(totalAmt); // 个人支付总金额
		
		// 退货
		if (PaymentTypeEnum.containTH(tranType)) {
			body.setBeanAmt(empty(payReturn.getBeanAmount()));
			body.setCashAmt(empty(payReturn.getCashAmount()));
			body.setGiftAmt(empty(payReturn.getGiftAmount()));
			body.setTotalAmt(empty(payReturn.getTotalAmount()));
		}
		// 撤销
		if (PaymentTypeEnum.containCX(tranType)) {
			body.setTotalAmt(empty(payMain.getTotalAmount()));
		}

		SystemBody systemBody = new SystemBody(tranType, body);
		SystemContent result = new SystemContent(systemType, systemBody);
		return JsonUtils.toJsonString(result);
	}

	// 空指针转换
	private String empty(BigDecimal source) {
		if (null == source)
			return "0.0";
		return source.toString();
	}

	// 空指针转换
	private String empty(String source) {
		return StringUtils.isEmpty(source) ? "" : source;
	}

	// 业务类型映射推送消息标题
	private String getTitle() {
		if (PaymentTypeEnum.POS_XF.getValue().equals(tranType) || PaymentTypeEnum.WSXF.getValue().equals(tranType))
			return "支付成功";
		else if (PaymentTypeEnum.POS_CX_CZ.getValue().equals(tranType) || PaymentTypeEnum.WSXF_CZ.getValue().equals(tranType))
			return "支付冲正成功";
		else if (PaymentTypeEnum.DSZF.getValue().equals(tranType))
			return "打赏成功";
		else if (PaymentTypeEnum.POS_CX.getValue().equals(tranType) || PaymentTypeEnum.WSXF_CX.getValue().equals(tranType))
			return "支付撤单成功";
		else if (PaymentTypeEnum.POS_CX_CZ.getValue().equals(tranType) || PaymentTypeEnum.WSXF_CX_CZ.getValue().equals(tranType))
			return "支付撤单冲正成功";
		else if (PaymentTypeEnum.POS_TH.getValue().equals(tranType) || PaymentTypeEnum.POS_BF_TH.getValue().equals(tranType) || PaymentTypeEnum.WSXF_TH.getValue().equals(tranType) || PaymentTypeEnum.WSXF_BF_TH.getValue().equals(tranType))
			return "退款成功";
		else if (PaymentTypeEnum.POS_TH_CZ.getValue().equals(tranType) || PaymentTypeEnum.WSXF_TH_CZ.getValue().equals(tranType))
			return "退款冲正成功";
		else if (PaymentTypeEnum.SQQR.getValue().equals(tranType))
			return "预授权成功";
		else if (PaymentTypeEnum.SQQR_CZ.getValue().equals(tranType))
			return "预授权冲正成功";
		else
			return "";
	}
}
