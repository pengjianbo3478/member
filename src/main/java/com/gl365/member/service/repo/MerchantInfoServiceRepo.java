package com.gl365.member.service.repo;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.Industry.IndustryRltDto;
import com.gl365.member.dto.merchant.MerchantInfo;
import com.gl365.member.dto.merchant.MerchantInfo2Pay;
import com.gl365.member.dto.merchant.command.GetMerchantDetail4MerchantCommand;
import com.gl365.member.dto.merchant.command.RegistAddOperatorDto;
import com.gl365.member.dto.merchant.command.UpdateMerchantCommentCountCommand;
import com.gl365.member.dto.users.MerchantOperatorDto;

/**
 * < 商家详细信息接口消费 >
 */
 @FeignClient(name = "merchant", url = "${${env:}.url.merchant:}")
public interface MerchantInfoServiceRepo {

	/**
	 * 获取店长信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/merchantAPI/operator/queryOperatorAdmin/{merchantNo}", method = RequestMethod.GET)
	ResultDto<MerchantOperatorDto> queryOperatorAdmin(@PathVariable("merchantNo") String merchantNo);

	/**
	 * 累加商家评论统计信息
	 * 
	 * @param负数表示扣减
	 * @return
	 */
	@RequestMapping(value = "/merchant/comment/overCount", method = RequestMethod.POST)
	ResultDto<Integer> overMerchantCount(@RequestBody UpdateMerchantCommentCountCommand command);

	/**
	 * 获取商家详情,支持批量
	 * 
	 * @param command2Merchant
	 * @return
	 */
	@RequestMapping(value = "/merchant/detail", method = RequestMethod.POST)
	ResultDto<List<MerchantInfo>> getMerchantDetail(GetMerchantDetail4MerchantCommand command2Merchant);
	
	@RequestMapping(value = "/merchant/selectByParentCategoryId", method = RequestMethod.GET)
	ResultDto<List<IndustryRltDto>> selectByParentCategoryId(@RequestParam(name="parentCategoryId",required=false) String parentCategoryId);
	
	// B端注册后直接新增离线员工
	@RequestMapping(value = "/merchant/operator/saveOperatorForRegister", method = RequestMethod.POST)
	public ResultDto<?> saveOperatorForRegister(@RequestBody RegistAddOperatorDto command);
	
	/**
	 * 根据多个商户号获取商家信息
	 * 
	 * @param merchantNo
	 * @return
	 */
	@RequestMapping(value = "/merchant/getMerchantByMerchantNoList", method = RequestMethod.POST)
	List<MerchantInfo2Pay> getMerchantByMerchantNoList(@RequestBody List<String> merchantNo);
	
	/**
	 * 获取员工店长详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/merchant/operator/queryOperatorByUserId", method = RequestMethod.GET)
	ResultDto<MerchantOperatorDto> queryOperatorByUserId(@RequestParam("userId") String userId);
	
}
