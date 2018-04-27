package com.gl365.member.service.repo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl365.member.dto.ResultDto;

@FeignClient("validator")
public interface ValidatorService {

	/**
	 * 实名认证
	 * @param cardId 身份证
	 * @param name 名字
	 * @return ResultDto<Boolean>
	 */
	@RequestMapping(value = "validIdCard" , method= RequestMethod.POST)
	public ResultDto<Boolean> certification(@RequestParam("cardId") String cardId,@RequestParam("name") String name);
	
   /**
	* 验证银行卡
	* @param bankId 银行卡
	* @param cardId 身份证
	* @param name 名字
	* @return ResultDto<Boolean>
	*/
	@RequestMapping(value = "validBankCard" , method= RequestMethod.POST)
	public ResultDto<Boolean> validBankCard (@RequestParam("bankId") String bankId,@RequestParam("cardId") String cardId,
			@RequestParam("name") String name);
	
	/**
	 * 手机认证
	 * @param mobileNum 手机号
	 * @param idNum 身份证
	 * @param name 名字
	 * @return ResultDto<Boolean>
	 */
	@RequestMapping(value = "/validMobile" , method= RequestMethod.POST)
	public ResultDto<Boolean> phoneValidate(@RequestParam("mobileNum") String mobileNum, @RequestParam("idNum") String idNum, @RequestParam("name") String name);


}
