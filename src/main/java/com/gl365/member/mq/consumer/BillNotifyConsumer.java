package com.gl365.member.mq.consumer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl365.aliyun.ons.OnsListener;
import com.gl365.member.common.GsonUtils;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.enums.message.MQMessageTypeEnum;
import com.gl365.member.common.enums.message.MQSystemTypeEnum;
import com.gl365.member.dto.MsgReq;
import com.gl365.member.dto.mq.account.BillNotifyCommand;
import com.gl365.member.dto.mq.account.BillNotifyCommandResp;
import com.gl365.member.dto.mq.push.PushMQ;
import com.gl365.member.mapper.UserMapper;
import com.gl365.member.model.User;
import com.gl365.member.mq.producer.JPushProducer;
import com.gl365.member.service.repo.MessageService;

@Component("bill-notify-consumer")
public class BillNotifyConsumer  implements OnsListener {//extends MQConsumer<BillNotifyCommand> {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JPushProducer jPushProducer;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MessageService messageService;

	@Override
	public void receive(byte[] arg0) {
		String message=new String(arg0);
		logger.info("========bill-notify-consumer=====>receive:{}", message);
		try {
			BillNotifyCommand command = GsonUtils.getGson().fromJson(message, BillNotifyCommand.class);
			execute(command);
		} catch(Exception e) {
			logger.error("========bill-notify-consumer=====>deal failure {}", e);
		}
	}
	
	public void execute(BillNotifyCommand command) {
		logger.info("========bill-notify-consumer=====>begin:{}", JsonUtils.toJsonString(command));
		String userId= command.getUserId();
		if (StringUtils.isNotBlank(userId)) {
			// 判断账单消息是否推送,只推送有手机的,但是都要存储
			User user = userMapper.selectByPrimaryKey(userId);
			if (user != null) {
				PushMQ pushMQ = new PushMQ();
				String systemType = MQSystemTypeEnum.MONTHLY_BILLS.getCode();
				pushMQ.setApp("cfront");
				pushMQ.setUid(userId);
				command.setSystemType(systemType);
				
				//处理时间格式
				BillNotifyCommandResp target = new BillNotifyCommandResp();
				BeanUtils.copyProperties(command, target);
				if(command.getBillDate() != null){
					target.setBillDate(command.getBillDate().format(DateTimeFormatter.ofPattern("yyyy-MM")));
					target.setSendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				}
				
				pushMQ.setContent(JsonUtils.toJsonString(target));
				buildMsg(pushMQ, MQMessageTypeEnum.MSG_01.getCode(), systemType);
				if (StringUtils.isNotBlank(user.getMobilePhone())) {
					logger.info("========bill-notify-consumer=====>end:{}", JsonUtils.toJsonString(pushMQ));
					jPushProducer.send(pushMQ);
				} else {
					logger.info("========bill-notify-consumer=====>end,user don't have mobile,userId={}", userId);
				}
			} else {
				logger.info("========bill-notify-consumer=====>end,user not exist,userId={}", userId);
			}
		} else {
			logger.info("========bill-notify-consumer=====>end,error result:{}", JsonUtils.toJsonString(command));
		}
	}

	// 写message服务
	private void buildMsg(PushMQ command, String messageType, String systemType) {
		MsgReq req = new MsgReq();
		req.setAlias(command.getUid());
		req.setAppType(command.getApp());
		req.setMessage(command.getContent());
		req.setMessageType(messageType);
		req.setMessageResultType(systemType);
		messageService.addMsg(req);
	}

}
