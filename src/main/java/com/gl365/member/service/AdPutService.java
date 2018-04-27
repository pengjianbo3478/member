package com.gl365.member.service;

import com.github.pagehelper.PageInfo;
import com.gl365.member.dto.ad.AdDetailDto;
import com.gl365.member.dto.ad.AdPutDto;
import com.gl365.member.pojo.AdDetailCommand;
import com.gl365.member.pojo.AdPutCommand;
import com.gl365.member.pojo.AdPutQueryReq;
import com.gl365.member.pojo.AdPutUpdateReq;

public interface AdPutService {

	/**
	 * 广告投放
	 * @param command
	 * @return 1：投放成功，2：参数错误 ，其他：失败
	 */
	Integer add(AdPutCommand command);
	
	/**
	 * 查询
	 * @param req
	 * @return
	 */
	PageInfo<AdPutDto> query(AdPutQueryReq req);
	
	/**
	 * 更新
	 * @param req
	 * @return 1：成功，其他：失败
	 */
	Integer update(AdPutUpdateReq req);
	
	/**
	 * C端查询广告列表
	 * @param command
	 * @return
	 */
	PageInfo<AdDetailDto> queryDetailForC(AdDetailCommand command);
}
