package com.gl365.member.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gl365.member.model.Advertisement;


@Repository
public interface AdvertisementMapper {
	
    int deleteByPrimaryKey(String id);

    int insertSelective(Advertisement record);

    Advertisement selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Advertisement record);


	/**
	 * 获取广告信息
	 * @param city 城市
	 * @param type 类型
	 * @param date 间隔时间 天
	 * @return
	 */
	List<Advertisement> getAdvertisement(@Param("city") String city,@Param("type") int type,@Param("date") LocalDateTime date);

	/**
	 * 获取活动广告信息
	 * @param city 城市
	 * @param type 类型
	 * @return
	 */
	List<Advertisement> getAdvertisActive(@Param("city") String city,@Param("type") int type);
}