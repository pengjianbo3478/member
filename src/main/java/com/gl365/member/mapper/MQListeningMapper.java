package com.gl365.member.mapper;

import com.gl365.member.model.MQListening;

public interface MQListeningMapper {
    int deleteByPrimaryKey(String id);

    int insert(MQListening record);

    int insertSelective(MQListening record);

    MQListening selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MQListening record);

    int updateByPrimaryKey(MQListening record);
}