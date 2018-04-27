package com.gl365.member.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.translation.BaiduTranslationDto;
import com.gl365.member.mapper.BaiduTranslationMapper;
import com.gl365.member.model.BaiduTranslation;
import com.gl365.member.service.TranslationService;

/**
 * < 行业数据接口实现类 >
 * 
 * @author hui.li 2017年4月25日 - 下午8:40:43
 * @Since 1.0
 */
@Service
public class TranslationServiceImpl implements TranslationService {

	private static final Logger LOG = LoggerFactory.getLogger(TranslationServiceImpl.class);

	@Autowired
	private BaiduTranslationMapper baiduTranslationMapper;
	
	public ResultDto<Map<String,String>> selectBySearchName(String searchName){
		LOG.info("百度查询 > > > in searchName：{}", searchName);
		try {
			BaiduTranslation bt=baiduTranslationMapper.selectBySearchName(searchName);
			
			if(null!=bt){
				Map<String,String> map =new HashMap<String,String>();
				map.put("translationName", bt.getTranslationName());
				ResultDto result=new ResultDto();
				result.setData(map);
				result.setDescription(ResultCodeEnum.System.SUCCESS.getMsg());
				result.setResult(ResultCodeEnum.System.SUCCESS.getCode());
				LOG.info("百度查询 > > > out searchName：{}", bt.getSearchName());
				return result;
			}
		} catch (Exception e) {
			LOG.error("百度查询 > > > 失败, 错误：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		
	}
	
	
	@Override
	public ResultDto<Integer> saveTranslation(BaiduTranslationDto command) {
		LOG.info("百度添加查询 > > > 入参：{}", JsonUtils.toJsonString(command));
		try {
			// 防止重复提交,先查询一遍

			// 保存
			BaiduTranslation model = new BaiduTranslation();
			model.setCreateTime(LocalDateTime.now());
			model.setId(UUID.randomUUID().toString());
			model.setMerchantShortname(command.getMerchantShortname());
			model.setSearchName(command.getSearchName());
			model.setTranslationName(command.getTranslationName());
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, baiduTranslationMapper.insertSelective(model));
		} catch (Exception e) {
			LOG.error("百度添加查询 > > > 错误：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}
	
	
	@Override
	public ResultDto<Integer> updateTranslation(BaiduTranslationDto command) {
		LOG.info("百度修改查询 > > > 入参：{}", JsonUtils.toJsonString(command));
		try {
			// 防止重复提交,先查询一遍

			// 保存
			BaiduTranslation model = new BaiduTranslation();
			model.setModifyTime(LocalDateTime.now());
			model.setId(command.getId());
			model.setMerchantShortname(command.getMerchantShortname());
			model.setSearchName(command.getSearchName());
			model.setTranslationName(command.getTranslationName());
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, baiduTranslationMapper.updateByPrimaryKeySelective(model));
		} catch (Exception e) {
			LOG.error("百度修改查询 > > > 错误：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public ResultDto<Integer> deleteTranslation(String id) {
		LOG.info("百度删除查询 > > > 入参：{}", JsonUtils.toJsonString(id));
		try {
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, baiduTranslationMapper.deleteByPrimaryKey(id));
		} catch (Exception e) {
			LOG.error("百度删除查询 > > > 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

}
