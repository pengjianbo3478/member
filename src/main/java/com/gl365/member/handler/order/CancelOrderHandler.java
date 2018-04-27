package com.gl365.member.handler.order;

import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.payment.pay.CancelOrderDto;

/**
 * 撤单处理类
 * @author dfs_508 2017年10月18日 下午6:10:51
 */
public interface CancelOrderHandler {

	/**
	 * 撤单主动推送极光
	 * @param command
	 * @return
	 */
	ResultDto<?> cancelorder(CancelOrderDto command, String title);
	
}
