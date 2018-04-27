package com.gl365.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.Industry.Industry;
import com.gl365.member.dto.Industry.IndustryRltDto;
import com.gl365.member.dto.config.IndustryDto;
import com.gl365.member.service.IndustryService;
import com.gl365.member.service.repo.MerchantInfoServiceRepo;

/**
 * < 行业数据接口实现类 >
 * 
 * @author hui.li 2017年4月25日 - 下午8:40:43
 * @Since 1.0
 */
@Service
public class IndustryServiceImpl implements IndustryService {

	private static final Logger LOG = LoggerFactory.getLogger(IndustryServiceImpl.class);

	@Autowired
	private MerchantInfoServiceRepo merchantInfoServiceRepo;

	@Override
	public ResultDto<IndustryDto> getIndustry() {
		LOG.info("获取行业数据 > > >");
		try {
			IndustryDto resultDate = new IndustryDto();
			// 查询全部
			ResultDto<List<IndustryRltDto>> rlt = merchantInfoServiceRepo.selectByParentCategoryId(null);
			List<Industry> data = null;
			if(ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())){
				List<IndustryRltDto> result = rlt.getData();
				if(null != result && result.size()>0){
					data = new ArrayList<>();
					for (IndustryRltDto industryRltDto : result) {
						Industry industry = new Industry();
						industry.setFlag(industryRltDto.getFlag());
						industry.setId(Integer.valueOf(industryRltDto.getCategoryId()));
						industry.setLevel(industryRltDto.getCategoryEvel());
						industry.setName(industryRltDto.getCategoryName());
						industry.setParentId(Integer.valueOf(industryRltDto.getParentCategoryId()));
						data.add(industry);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(data)) {
				// 第一级标签
				List<IndustryDto> industryDtos = new ArrayList<>();
				Integer parentId = null;
				for (Industry ind : data) {
					if (null == parentId) {
						// 拿到初始级别
						parentId = ind.getParentId();
					}
					if (parentId == ind.getParentId()) {
						// 构建第一级标签
						IndustryDto industryDto = new IndustryDto();
						industryDto.setId(ind.getId());
						industryDto.setName(ind.getName());
						industryDto.setSub(industryDtos);
						industryDtos.add(industryDto);
					} else {
						break;
					}
				}
				// 第二级标签
				parentId = null;
				for (IndustryDto industryDto : industryDtos) {
					List<IndustryDto> temp = new ArrayList<>();
					for (Industry ind : data) {
						if (ind.getParentId() == industryDto.getId()) {
							IndustryDto tempDto = new IndustryDto();
							tempDto.setId(ind.getId());
							tempDto.setName(ind.getName());
							temp.add(tempDto);
						}
					}
					industryDto.setSub(temp);
				}
				resultDate.setSub(industryDtos);
			}
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, resultDate);
		} catch (Exception e) {
			LOG.error("获取行业数据 > > >失败, 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}
}
