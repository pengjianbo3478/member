package com.gl365.member.service.repo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.users.UserForgotPwdDto;

@FeignClient("cfront")
public interface CfrontServiceRepo {
	
	/**
	 * 清除登录的token
	 * @return
	 */
	@RequestMapping(value = "/lifeAPI/user/clearToken", method = RequestMethod.POST)
	public ResultDto<?> clearToken(@RequestBody UserForgotPwdDto command);
}
