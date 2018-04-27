package com.gl365.member.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gl365.member.dto.ad.AdMainDto;
import com.gl365.member.mapper.AdMainMapper;
import com.gl365.member.pojo.AdMainCommand;
import com.gl365.member.pojo.AdMainQueryReq;
import com.gl365.member.service.AdMainService;

@Service
public class AdMainServiceImpl implements AdMainService{

	private static final Logger logger = LoggerFactory.getLogger(AdMainServiceImpl.class);
	
	@Autowired
	private AdMainMapper adMainMapper;
	
	@Override
	public Integer add(AdMainCommand command) {
		AdMainDto record = new AdMainDto();
		BeanUtils.copyProperties(command, record);
		if(null != command.getState()){
			record.setState(command.getState().getValue());
		}
		
		Integer result = null; 
		try{
			result = adMainMapper.insert(record);
		}catch(DuplicateKeyException ee){
			logger.error("add ===> adMainMapper.insert 新增广告失败,广告名称已存在,e:",ee);
			result = 2;
		}catch(Exception e){
			logger.error("add ===> adMainMapper.insert 新增广告失败,e:",e);
		}
		return result;
	}

	@Override
	public PageInfo<AdMainDto> query(AdMainQueryReq req) {
		AdMainDto qp = new AdMainDto();
		qp.setAdName(req.getAdName());
		if(null != req.getState()){
			qp.setState(req.getState().getValue());
		}
		
		PageInfo<AdMainDto> page = null;
		try{
			PageHelper.startPage(req.getCurPage(), req.getPageSize());
			page = new PageInfo<>(adMainMapper.select(qp), req.getNavigatePages());
		}catch(Exception e){
			logger.error("query ===> adMainMapper.select exception,e:",e);
			PageHelper.clearPage();
		}
		return page;
	}
	
	@Override
	public Integer update(AdMainCommand command) {
		AdMainDto record = new AdMainDto();
		BeanUtils.copyProperties(command, record);
		if(null != command.getState()){
			record.setState(command.getState().getValue());
		}
		
		Integer result = null;
		try{
			result = adMainMapper.updateById(record);
		}catch(DuplicateKeyException ee){
			logger.error("update ===> adMainMapper.updateById 更新广告失败,广告名称已存在,e:",ee);
			result = 2;
		}catch(Exception e){
			logger.error("update ===> adMainMapper.updateById exception,e:",e);
		}
		return result;
	}

}
