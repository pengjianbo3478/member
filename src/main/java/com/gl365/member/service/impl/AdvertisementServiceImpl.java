package com.gl365.member.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.member.common.DBConstants;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.customize.AdActiveDto;
import com.gl365.member.dto.customize.AdBannerDto;
import com.gl365.member.dto.customize.AdvertisementDto;
import com.gl365.member.dto.customize.command.GetAdvertisementCommand;
import com.gl365.member.mapper.AdvertisementMapper;
import com.gl365.member.model.Advertisement;
import com.gl365.member.service.AdvertisementService;

/**
 * < 广告Service实现 >
 * 
 * @author hui.li 2017年4月21日 - 下午8:04:23
 * @Since 1.0
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	private static final Logger LOG = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

	@Autowired
	private AdvertisementMapper advertisementMapper;

	@Override
	public ResultDto<List<AdvertisementDto>> getAdvertisement(GetAdvertisementCommand command) {
		LOG.info("获取封面广告, 城市：{}", command.getCity());
		try {
			List<Advertisement> data = advertisementMapper.getAdvertisement(command.getCity(), DBConstants.AD_TYPE.AD.getValue(), LocalDateTime.now());
			if (CollectionUtils.isEmpty(data))
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			List<AdvertisementDto> resultDate = new ArrayList<>();
			for (Advertisement model : data) {
				AdvertisementDto dto = new AdvertisementDto();
				dto.setImageUrl(model.getImageUrl());
				dto.setBeginDatetime(model.getBeginDatetime());
				dto.setEndDatetime(model.getEndDatetime());
				resultDate.add(dto);
			}
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, resultDate);
		} catch (Exception e) {
			LOG.error("获取封面广告失败, 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public ResultDto<List<AdBannerDto>> getBanner(GetAdvertisementCommand command) {
		LOG.info("获取首页Banner广告, 城市：{}", command.getCity());
		try {
			List<Advertisement> data = advertisementMapper.getAdvertisement(command.getCity(), DBConstants.AD_TYPE.BANNER.getValue(), LocalDateTime.now());
			if (CollectionUtils.isEmpty(data))
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			List<AdBannerDto> resultDate = new ArrayList<>();
			for (Advertisement model : data) {
				AdBannerDto dto = new AdBannerDto();
				dto.setImageUrl(model.getImageUrl());
				dto.setUrl(model.getLinkUrl());
				resultDate.add(dto);
			}
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, resultDate);
		} catch (Exception e) {
			LOG.error("获取首页Banner广告失败, 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public ResultDto<List<AdActiveDto>> advertisActive(GetAdvertisementCommand command) {
		LOG.info("获取活动广告, 城市：{}", command.getCity());
		try {
			List<Advertisement> data = advertisementMapper.getAdvertisActive(command.getCity(), DBConstants.AD_TYPE.ACTIVE.getValue());
			if (CollectionUtils.isEmpty(data))
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			List<AdActiveDto> resultDate = new ArrayList<>();
			for (Advertisement model : data) {
				AdActiveDto dto = new AdActiveDto();
				dto.setImageUrl(model.getImageUrl());
				dto.setUrl(model.getLinkUrl());
				dto.setAdTitle(model.getAdTitle());
				resultDate.add(dto);
			}
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, resultDate);
		} catch (Exception e) {
			LOG.error("获取活动广告失败, 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}
}
