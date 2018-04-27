package com.gl365.member.service;

import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.config.IndustryDto;

/**
 * < 行业数据接口 >
 * 
 * @author hui.li 2017年4月25日 - 下午8:38:51
 * @Since 1.0
 */
public interface IndustryService {

	/**
	 * 获取行业数据
	 * 
	 * @param industry
	 * @return
	 */
	ResultDto<IndustryDto> getIndustry();

}
