package com.gl365.member.service.repo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gl365.member.dto.MsgReq;
import com.gl365.member.dto.ResultDto;

@FeignClient(name="message",url="${${env:}.url.message:}")
public interface MessageService {

	/**
	 * 添加一条推送消息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/message/addMsg", method = RequestMethod.POST)
	public ResultDto<?> addMsg(@RequestBody MsgReq req);

}
