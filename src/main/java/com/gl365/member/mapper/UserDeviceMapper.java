package com.gl365.member.mapper;

import java.util.List;

import com.gl365.member.model.UserDevice;

public interface UserDeviceMapper {
    int insert(UserDevice record);

    int insertSelective(UserDevice record);

	List<UserDevice> selectByPrimaryKey(String userId);
}