package com.gl365.member.service;

import com.github.pagehelper.PageInfo;
import com.gl365.member.dto.ad.AdMainDto;
import com.gl365.member.pojo.AdMainCommand;
import com.gl365.member.pojo.AdMainQueryReq;

/**
 * 广告管理服务
 * @author dfs_519
 *2017年7月25日下午4:28:43
 */
public interface AdMainService {
	
	/**
	 * 新增广告
	 * @param command
	 * @return 1：新增成功，2：广告名称已存在新增失败，其他：新增失败
	 */
	Integer add(AdMainCommand command);
	
	/**
	 * 查询
	 * @param command
	 * @return 
	 */
	PageInfo<AdMainDto> query(AdMainQueryReq req);

	/**
	 * 更新
	 * @param command
	 * @return 1：成功，其他：失败
	 */
	Integer update(AdMainCommand command);
}
