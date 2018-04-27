package com.gl365.member.service;

import com.gl365.member.dto.PageResultDto;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.merchant.command.GetUserFavorites4MemberCommand;
import com.gl365.member.dto.merchant.command.IsFavoriteCommand;
import com.gl365.member.dto.merchant.command.SaveFavorite4MemberCommand;
import com.gl365.member.model.MerchantFavorites;

/**
 * < 商家收藏Service >
 * 
 * @author hui.li 2017年4月19日 - 下午5:03:25
 * @Since 1.0
 */
public interface MerchantFavoritesService {

	/**
	 * 收藏商家
	 * 
	 * @param command
	 * @return
	 */
	ResultDto<Integer> saveFavorites(SaveFavorite4MemberCommand command);

	/**
	 * 取消收藏
	 * 
	 * @param command
	 * @return
	 */
	ResultDto<Integer> deleteFavorites(SaveFavorite4MemberCommand command);

	/**
	 * 用户收藏的商家列表
	 * 
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageResultDto<MerchantFavorites> getFavoritesList(GetUserFavorites4MemberCommand command);

	/**
	 * 通过用户、商户获取收藏信息
	 * 
	 * @param userId
	 * @param merchantNo
	 * @return
	 */
	ResultDto<MerchantFavorites> getByUserIdMerchantNo(IsFavoriteCommand command);
	
	/**
	 * 通过商户号获取useridList
	 * 
	 * @param merchantNo
	 * @return
	 */
	Object getUserIdByMerchantNo(String merchantNo,Integer curPage,Integer pageSize);

}