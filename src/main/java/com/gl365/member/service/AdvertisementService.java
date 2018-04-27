package com.gl365.member.service;

import java.util.List;

import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.customize.AdActiveDto;
import com.gl365.member.dto.customize.AdBannerDto;
import com.gl365.member.dto.customize.AdvertisementDto;
import com.gl365.member.dto.customize.command.GetAdvertisementCommand;

/**
 * < 广告Service >
 * 
 * @author hui.li 2017年4月21日 - 下午8:35:17
 * @Since 1.0
 */
public interface AdvertisementService {

	/**
	 * 获取广告
	 * 
	 * @param command
	 * @return
	 */
	ResultDto<List<AdvertisementDto>> getAdvertisement(GetAdvertisementCommand command);

	/**
	 * 获取Banner广告
	 * 
	 * @param command
	 * @return
	 */
	ResultDto<List<AdBannerDto>> getBanner(GetAdvertisementCommand command);

	/**
	 * 获取活动广告
	 * 
	 * @param command
	 * @return
	 */
	ResultDto<List<AdActiveDto>> advertisActive(GetAdvertisementCommand command);

}
