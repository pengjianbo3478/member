package com.gl365.member.mapper;

import java.util.List;
import com.gl365.member.dto.ad.AdMainDto;
import com.gl365.member.dto.ad.req.AdmainReq;

public interface AdMainMapper {

    int insert(AdMainDto record);

    AdMainDto selectById(int id);
    
    List<AdMainDto> select(AdMainDto record);

    int updateById(AdMainDto record);
    
}