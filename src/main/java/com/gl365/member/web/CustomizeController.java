package com.gl365.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.member.common.FallbackHandlerUtils;
import com.gl365.member.dto.customize.command.GetAdvertisementCommand;
import com.gl365.member.dto.customize.command.GetNewestVersionCommand;
import com.gl365.member.service.AdvertisementService;
import com.gl365.member.service.IndustryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * < 模板、广告定制Conroller >
 * 
 * @author hui.li 2017年4月21日 - 上午11:40:54
 * @Since 1.0
 */
@RestController
@RequestMapping("/member")
public class CustomizeController {

	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private IndustryService industryService;

	/**
	 * 获取封面广告
	 * 
	 * @param city
	 * @return
	 */
	@PostMapping("/ad")
	@HystrixCommand(fallbackMethod = "advertisementFallback")
	public Object advertisement(@RequestBody GetAdvertisementCommand command) {
		return advertisementService.getAdvertisement(command);
	}

	/**
	 * 获取Banner广告
	 * 
	 * @param city
	 * @return
	 */
	@PostMapping("/ad/banner")
	@HystrixCommand(fallbackMethod = "bannerFallback")
	public Object banner(@RequestBody GetAdvertisementCommand command) {
		return advertisementService.getBanner(command);
	}
	
	/**
	 * 获取活动广告
	 * 
	 * @param city
	 * @return
	 */
	@PostMapping("/ad/active")
	@HystrixCommand(fallbackMethod = "advertisActiveFallback")
	public Object advertisActive(@RequestBody GetAdvertisementCommand command) {
		return advertisementService.advertisActive(command);
	}

	/**
	 * 获取行业数据
	 * 
	 * @param industry
	 * @return
	 */
	@PostMapping("/comment/industry")
	@HystrixCommand(fallbackMethod = "industryFallback")
	public Object industry() {
		return industryService.getIndustry();
	}

	public Object versionCheckedFallback(@RequestBody GetNewestVersionCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object advertisementFallback(@RequestBody GetAdvertisementCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object bannerFallback(@RequestBody GetAdvertisementCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}
	public Object advertisActiveFallback(@RequestBody GetAdvertisementCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object industryFallback() {
		return FallbackHandlerUtils.timeOutResult();
	}

}
