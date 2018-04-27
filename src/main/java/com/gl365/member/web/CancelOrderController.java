package com.gl365.member.web;

import org.apache.commons.lang.StringUtils;
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
import com.gl365.member.dto.payment.pay.CancelOrderDto;
import com.gl365.member.handler.order.CancelOrderHandler;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.ApiOperation;

/**
 * 撤单控制器
 * @author dfs_508 2017年10月18日 下午5:54:46
 */
@RestController
@RequestMapping("/member/cancelorder")
public class CancelOrderController {

	private static final Logger LOG = LoggerFactory.getLogger(CancelOrderController.class);
	
	@Autowired
	private CancelOrderHandler cancelOrderHandler;
	
	@ApiOperation(value = "C端撤单")
	@PostMapping("/cancelorder")
	@HystrixCommand(fallbackMethod = "cancelorderFallback")
	public ResultDto<?> cancelorder(@RequestBody CancelOrderDto command) {
		LOG.info("cancelorder begin param={}", JsonUtils.toJsonString(command));
    	Long beginTime = System.currentTimeMillis();
    	ResultDto<?> rlt = null;
		try {
			if (StringUtils.isBlank(command.getAlias())) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			String title = "退款成功通知";
			rlt = cancelOrderHandler.cancelorder(command, title);
		} catch (Exception e) {
			LOG.error("cancelorder exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("cancelorder end rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	@ApiOperation(value = "C端撤单中通知")
	@PostMapping("/cancelordering")
	@HystrixCommand(fallbackMethod = "cancelorderingFallback")
	public ResultDto<?> cancelordering(@RequestBody CancelOrderDto command) {
		LOG.info("cancelordering begin param={}", JsonUtils.toJsonString(command));
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			if (StringUtils.isBlank(command.getAlias())) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			String title = "退款发起通知";
			rlt = cancelOrderHandler.cancelorder(command, title);
		} catch (Exception e) {
			LOG.error("cancelordering exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("cancelordering end rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	
	public ResultDto<?> cancelorderFallback(@RequestBody CancelOrderDto command) {
		return new ResultDto<>(ResultCodeEnum.System.SYSTEM_TIME_OUT);
	}
	public ResultDto<?> cancelorderingFallback(@RequestBody CancelOrderDto command) {
		return new ResultDto<>(ResultCodeEnum.System.SYSTEM_TIME_OUT);
	}

}
