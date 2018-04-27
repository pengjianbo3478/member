package com.gl365.member.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gl365.member.dto.ad.AdDetailDto;
import com.gl365.member.dto.ad.AdPutDto;
import com.gl365.member.dto.ad.req.AdDetailReq;
import com.gl365.member.dto.ad.req.AdPutMReq;
import com.gl365.member.mapper.AdPutMapper;
import com.gl365.member.pojo.AdDetailCommand;
import com.gl365.member.pojo.AdPutCommand;
import com.gl365.member.pojo.AdPutQueryReq;
import com.gl365.member.pojo.AdPutUpdateReq;
import com.gl365.member.service.AdPutService;

@Service
public class AdPutServiceImpl implements AdPutService{
	
	private static final Logger logger = LoggerFactory.getLogger(AdPutServiceImpl.class);
	
	private static final LocalTime begintime = LocalTime.of(0,0,0);
	private static final LocalTime endtime = LocalTime.of(23,59,59);
	
	@Autowired
	private AdPutMapper adPutMapper;

	@Override
	public Integer add(AdPutCommand command) {
		List<AdPutDto> list = buildInsertParam(command);
		if(null == list || list.isEmpty()){
			logger.error("add ===> buildInsertParam error,list:{}",list);
			return -1;
		}
		
		Integer result = null;
		try{
			result = adPutMapper.insertBatch(list);
		}catch(Exception e){
			logger.error("add ===> adPutMapper.insertBatch exception,e:",e);
		}
		return result;
	}

	@Override
	public PageInfo<AdPutDto> query(AdPutQueryReq req) {
		AdPutMReq command = new AdPutMReq();
		BeanUtils.copyProperties(req, command);
		if(null != req.getPutState()){
			command.setPutState(req.getPutState().getValue());
		}
		
		PageInfo<AdPutDto> page = null;
		try{
			PageHelper.startPage(req.getCurPage(), req.getPageSize());
			page = new PageInfo<>(adPutMapper.queryByM(command), req.getNavigatePages());
		}catch(Exception e){
			logger.error("query ===> adPutMapper.queryByM exception,e:",e);
			PageHelper.clearPage();
		}
		return page;
	}



	@Override
	public Integer update(AdPutUpdateReq req) {
		AdPutDto command = new AdPutDto();
		BeanUtils.copyProperties(req, command);
		if(null != req.getPutState()){
			command.setPutState(req.getPutState().getValue());
		}
		
		Integer result = null;
		try{
			result = adPutMapper.updateById(command);
		}catch(Exception e){
			logger.error("update ===> adPutMapper.updateById exception,e:",e);
		}
		return result;
		
	}

	
	private List<AdPutDto> buildInsertParam(AdPutCommand command){
		List<AdPutDto> list = new ArrayList<>();
		if(StringUtils.isBlank(command.getProvinces()) || StringUtils.isBlank(command.getCitys())){
			return null;
		}
		
		try{
			List<String> proList = Arrays.asList(command.getProvinces().split(","));
			List<String> cityList = Arrays.asList(command.getCitys().split(","));
			if(proList.size() != cityList.size()){
				return null;
			}
			for (int i=0; i<proList.size(); i++) {
				AdPutDto req = new AdPutDto();
				BeanUtils.copyProperties(command, req);
				if(null != command.getPutState()){
					req.setPutState(command.getPutState().getValue());
				}
				req.setProvince(proList.get(i));
				req.setCity(cityList.get(i));
				list.add(req);
			}
		}catch(Exception e){
			logger.error("buildInsertParam exception,e:",e);
			return null;
		}
		return list;
	}

	@Override
	public PageInfo<AdDetailDto> queryDetailForC(AdDetailCommand command) {
		AdDetailReq req = new AdDetailReq();
		BeanUtils.copyProperties(command, req);
		req.setQueryBegin(LocalDateTime.of(command.getQueryData(), begintime));
		req.setQueryEnd(LocalDateTime.of(command.getQueryData(), endtime));
		
		PageInfo<AdDetailDto> page = null;
		try{
			PageHelper.startPage(command.getCurPage(), command.getPageSize());
			page = new PageInfo<>(adPutMapper.queryByC(req), command.getNavigatePages());
		}catch(Exception e){
			logger.error("queryDetailForC ===> adPutMapper.queryByC exception,e:",e);
			PageHelper.clearPage();
		}
		return page;
	}
	
}
