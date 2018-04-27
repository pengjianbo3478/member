package com.gl365.member.mapper;

import org.apache.ibatis.annotations.Param;

import com.gl365.member.model.BaiduTranslation;

public interface BaiduTranslationMapper {
    int deleteByPrimaryKey(String id);

    int insert(BaiduTranslation record);

    int insertSelective(BaiduTranslation record);

    BaiduTranslation selectByPrimaryKey(String id);
    
    BaiduTranslation selectBySearchName(@Param("searchName")String searchName);

    int updateByPrimaryKeySelective(BaiduTranslation record);

    int updateByPrimaryKey(BaiduTranslation record);
}