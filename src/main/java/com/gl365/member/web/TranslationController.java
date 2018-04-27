package com.gl365.member.web;

import java.util.Map;

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
import com.gl365.member.dto.SearchReq;
import com.gl365.member.dto.payment.pay.CancelOrderDto;
import com.gl365.member.handler.order.CancelOrderHandler;
import com.gl365.member.service.TranslationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.ApiOperation;

/**
 * 撤单控制器
 * @author dfs_508 2017年10月18日 下午5:54:46
 */
@RestController
@RequestMapping("/member/translation")
public class TranslationController {

	private static final Logger LOG = LoggerFactory.getLogger(TranslationController.class);
	
	@Autowired
	private TranslationService translationService;
	
	@ApiOperation(value = "百度翻译转换")
	@PostMapping("/searchName")
	public ResultDto<?> selectBySearchName(@RequestBody SearchReq searchName) {
//		public ResultDto<?> selectBySearchName() {
//		String searchName="";
		LOG.info("selectBySearchName begin param={}", JsonUtils.toJsonString(searchName));
    	Long beginTime = System.currentTimeMillis();
    	ResultDto<?> rlt = null;
		try {
			if (StringUtils.isBlank(searchName.getSearchName())) {
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			}
			rlt = translationService.selectBySearchName(searchName.getSearchName());
		} catch (Exception e) {
			LOG.error("selectBySearchName exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("selectBySearchName end rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	

}
