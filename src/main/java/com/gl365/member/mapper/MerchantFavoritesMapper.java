package com.gl365.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gl365.member.model.MerchantFavorites;

@Repository
public interface MerchantFavoritesMapper {

	Integer insertSelective(MerchantFavorites record);

	Integer delete(MerchantFavorites model);

	/**
	 * 获取是否收藏
	 * 
	 * @param userId
	 * @param merchantNo
	 * @return
	 */
	List<MerchantFavorites> getByUserIdMerchantNo(@Param("userId") String userId, @Param("merchantNo") String merchantNo);

	/**
	 * 获取已收藏的商家列表
	 * 
	 * @param userId
	 * @return
	 */
	List<MerchantFavorites> getMerchantListByUserId(Map<String, Object> param);

	/**
	 * 获取已收藏商家总数
	 * 
	 * @param param
	 * @return
	 */
	Integer getMerchantCountByUserId(Map<String, Object> param);
	
	/**
	 * 通过商户号获取useridList
	 * 
	 * @param userId
	 * @param map
	 * @return
	 */
	List<MerchantFavorites> getUserIdByMerchantNo(Map<String, Object> map);
	Integer getUserIdByMerchantNoCount (Map<String, Object> map);
}