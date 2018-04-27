package com.gl365.member.service;

import java.util.Map;

import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.translation.BaiduTranslationDto;

/**
 * < 行业数据接口 >
 * 
 * @author hui.li 2017年4月25日 - 下午8:38:51
 * @Since 1.0
 */
public interface TranslationService {
	
	public ResultDto<Map<String,String>> selectBySearchName(String searchName);
	
	public ResultDto<Integer> saveTranslation(BaiduTranslationDto command);

	public ResultDto<Integer> deleteTranslation(String id);
	
	public ResultDto<Integer> updateTranslation(BaiduTranslationDto command);
}
