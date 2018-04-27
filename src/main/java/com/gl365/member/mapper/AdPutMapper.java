package com.gl365.member.mapper;

import java.util.List;

import com.gl365.member.dto.ad.AdDetailDto;
import com.gl365.member.dto.ad.AdPutDto;
import com.gl365.member.dto.ad.req.AdDetailReq;
import com.gl365.member.dto.ad.req.AdPutMReq;

public interface AdPutMapper {
	
	int insertBatch(List<AdPutDto> list);

	List<AdPutDto> queryByM(AdPutMReq req);
	
	int updateById(AdPutDto req);
	
	List<AdDetailDto> queryByC(AdDetailReq req);
}