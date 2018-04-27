package com.gl365.member.service.repo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gl365.member.dto.account.req.ActBalanceInfoReq;
import com.gl365.member.dto.account.req.MergeAccountReq;
import com.gl365.member.dto.account.rlt.ActBalance;
import com.gl365.member.dto.account.rlt.ActServiceRlt;

/**
 * 账户系统服务
 * 
 */
@FeignClient(name="account",url="${${env:}.url.account:}")
public interface AccountService {


	/**
	 * 查询账户余额信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/account/queryAccountBalanceInfo", method = RequestMethod.POST)
	ActBalance queryAccountBalanceInfo(@RequestBody ActBalanceInfoReq req);

	/**
	 * 合并指定发行渠道的账户
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/account/mergeAccount", method = RequestMethod.POST)
	ActServiceRlt mergeAccount(@RequestBody MergeAccountReq req);
}
