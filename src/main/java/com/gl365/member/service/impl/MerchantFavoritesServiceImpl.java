package com.gl365.member.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.PageDto;
import com.gl365.member.dto.PageResultDto;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.merchant.command.GetUserFavorites4MemberCommand;
import com.gl365.member.dto.merchant.command.IsFavoriteCommand;
import com.gl365.member.dto.merchant.command.SaveFavorite4MemberCommand;
import com.gl365.member.mapper.MerchantFavoritesMapper;
import com.gl365.member.model.MerchantFavorites;
import com.gl365.member.service.MerchantFavoritesService;

/**
 * < 商户收藏Service实现类 >
 * 
 * @author hui.li 2017年4月19日 - 下午5:08:51
 * @Since 1.0
 */
@Service("merchantFavortesService")
public class MerchantFavoritesServiceImpl implements MerchantFavoritesService {

	private static final Logger LOG = LoggerFactory.getLogger(MerchantFavoritesServiceImpl.class);

	@Autowired
	private MerchantFavoritesMapper merchantFavortesMapper;

	@Override
	public ResultDto<Integer> saveFavorites(SaveFavorite4MemberCommand command) {
		LOG.info("收藏商家 > > > 入参：{}", JsonUtils.toJsonString(command));
		try {
			// 防止重复提交,先查询一遍
			List<MerchantFavorites> result = merchantFavortesMapper.getByUserIdMerchantNo(command.getUserId(), command.getMerchantNo());
			if (CollectionUtils.isNotEmpty(result))
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			// 保存
			MerchantFavorites model = new MerchantFavorites();
			model.setCreateTime(LocalDateTime.now());
			model.setMerchantNo(command.getMerchantNo());
			model.setUserId(command.getUserId());
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, merchantFavortesMapper.insertSelective(model));
		} catch (Exception e) {
			LOG.error("收藏商家 > > > 错误：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public ResultDto<Integer> deleteFavorites(SaveFavorite4MemberCommand command) {
		LOG.info("取消收藏商家 > > > 入参：{}", JsonUtils.toJsonString(command));
		try {
			MerchantFavorites model = new MerchantFavorites();
			model.setMerchantNo(command.getMerchantNo());
			model.setUserId(command.getUserId());
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, merchantFavortesMapper.delete(model));
		} catch (Exception e) {
			LOG.error("取消收藏商家失败 > > > 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public PageResultDto<MerchantFavorites> getFavoritesList(GetUserFavorites4MemberCommand command) {
		LOG.info("获取收藏商家列表，入参:{}", JsonUtils.toJsonString(command));
		try {
			int curPage = command.getCurPage();
			int pageSize = command.getPageSize();
			Map<String, Object> param = new HashMap<>();
			param.put("userId", command.getUserId());
			param.put("begin", (curPage - 1) * pageSize);
			param.put("end", curPage * pageSize);
			// 取已收藏的商家
			int total = merchantFavortesMapper.getMerchantCountByUserId(param);
			PageDto<MerchantFavorites> page = new PageDto<>(total, curPage, pageSize, merchantFavortesMapper.getMerchantListByUserId(param));
			return new PageResultDto<>(ResultCodeEnum.System.SUCCESS, page);
		} catch (Exception e) {
			LOG.error("获取收藏商家列表失败 > > > 异常：{}", e);
			return new PageResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public ResultDto<MerchantFavorites> getByUserIdMerchantNo(IsFavoriteCommand command) {
		LOG.info("根据用户和商家编号获取收藏信息，入参:{}", JsonUtils.toJsonString(command));
		try {
			List<MerchantFavorites> result = merchantFavortesMapper.getByUserIdMerchantNo(command.getUserId(), command.getMerchantNo());
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, CollectionUtils.isEmpty(result) ? null : result.get(0));
		} catch (Exception e) {
			LOG.error("根据用户和商家编号获取收藏信息 > > > 失败，异常:{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getUserIdByMerchantNo(String merchantNo,Integer curPage,Integer pageSize) {
		LOG.info("通过商户号获取useridList，入参:{}", merchantNo);
		try {
			if(null == pageSize){
				pageSize = 10;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("merchantNo",merchantNo);
			map.put("begin", pageSize * (curPage - 1));
			map.put("end", pageSize);
			List<MerchantFavorites> favoriteList = merchantFavortesMapper.getUserIdByMerchantNo(map);
			Integer totalCount = merchantFavortesMapper.getUserIdByMerchantNoCount(map);
			List<String> result = new ArrayList<>();
			for (MerchantFavorites merchantFavorites : favoriteList) {
				result.add(merchantFavorites.getUserId());
			}
			return new ResultDto<PageDto>(ResultCodeEnum.System.SUCCESS,new PageDto(totalCount,curPage,pageSize,result));
		} catch (Exception e) {
			LOG.error("通过商户号获取useridList > > > 失败，异常:{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}
}
