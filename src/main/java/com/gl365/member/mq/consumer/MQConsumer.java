package com.gl365.member.mq.consumer;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.alibaba.druid.util.StringUtils;
import com.gl365.aliyun.ons.OnsListener;
import com.gl365.member.dto.mq.MQCommand;
import com.gl365.member.mapper.MQListeningMapper;
import com.gl365.member.model.MQListening;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * < MQ Consumer Helper >
 * 
 * @since hui.li 2017年5月27日 上午11:54:56
 */
public abstract class MQConsumer<T extends MQCommand> implements OnsListener {

	private static final Logger LOG = LoggerFactory.getLogger(MQConsumer.class);

	@Autowired
	private MQListeningMapper mapper;

	@Override
	public void receive(byte[] message) {
		try {
			T command = newInstance(message);
			if (validateMessageRepetion(command))
				execute(command);
		} catch (Exception e) {
			LOG.warn("<mq-received> error, exception : {}", e);
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private T newInstance(byte[] message) {
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		try {
			return new Gson().fromJson(new String(message), entityClass);
		} catch (JsonSyntaxException e) {
			LOG.warn("<mq-received> error : GsonString to Object convert error, message : [{}]", new String(message));
			throw new RuntimeException("<mq-received> error");
		}
	}

	public abstract void execute(T command);

	private boolean validateMessageRepetion(T message) {
		MQCommand command = (MQCommand) message;
		if (StringUtils.isEmpty(command.getMessageId())) {
			LOG.warn("<mq-received> error : messageId empty uniqueness constraint stop");
			return false;
		}
		// 校验MQ消息是否重复
		if (!StringUtils.isEmpty(command.getMessageId())) {
			MQListening createBean = new MQListening();
			createBean.setId(command.getMessageId());
			createBean.setCreateTime(LocalDateTime.now());
			try {
				mapper.insertSelective(createBean);
			} catch (DuplicateKeyException e) {
				LOG.info("<mq-received> message id exists :[{}]", command.getMessageId());
				return false;
			} catch (Exception e1) {
				LOG.info("<mq-received> message id  :[{}] error,  cause by:{}", command.getMessageId(), e1.getMessage());
			}
			return true;
		}
		return false;
	}

}
