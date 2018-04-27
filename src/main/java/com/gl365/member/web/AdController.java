package com.gl365.member.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gl365.member.common.FallbackHandlerUtils;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.dto.PageDto;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.ad.AdDetailDto;
import com.gl365.member.dto.ad.AdMainDto;
import com.gl365.member.dto.ad.AdPutDto;
import com.gl365.member.pojo.AdDetailCommand;
import com.gl365.member.pojo.AdMainCommand;
import com.gl365.member.pojo.AdMainQueryReq;
import com.gl365.member.pojo.AdPutCommand;
import com.gl365.member.pojo.AdPutQueryReq;
import com.gl365.member.pojo.AdPutUpdateReq;
import com.gl365.member.service.AdMainService;
import com.gl365.member.service.AdPutService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/member/ad")
public class AdController {
	
	private static final int _0 = 0;

	private static final int _2 = 2;

	private static final int _1 = 1;
	
	private static final int param_error = -1;

	private static final Logger logger = LoggerFactory.getLogger(AdController.class);

	@Autowired
	private AdMainService adMainService;
	
	@Autowired
	private AdPutService adPutService;
	
	@PostMapping("/add")
	@HystrixCommand(fallbackMethod = "addFallback")
	public Object add(@RequestBody AdMainCommand command){
		logger.info("新增广告 begin,reqParam:{}", command);
		ResultDto<?> rlt = null;
		
		int addResult = adMainService.add(command);
		if(_1 == addResult){
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		}else if(_2 == addResult){
			rlt = ResultDto.result(ResultCodeEnum.Advertisment.AD_NAME_HAS_EXIST);
		}else{
			rlt = ResultDto.result(ResultCodeEnum.Advertisment.ADD_AD_FAIL);
		}
		
		logger.info("新增广告 end,rlt:{}", JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	@PostMapping("/update")
	@HystrixCommand(fallbackMethod = "updateFallback")
	public Object update(@RequestBody AdMainCommand command){
		logger.info("update ad begin,reqParam:{}", command);
		ResultDto<?> rlt = null;
		
		int updateResult = adMainService.update(command);
		if(_1 == updateResult){
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		}else if(_2 == updateResult){
			rlt = ResultDto.result(ResultCodeEnum.Advertisment.AD_NAME_HAS_EXIST);
		}else{
			rlt = ResultDto.result(ResultCodeEnum.Advertisment.UPDATE_AD_FAIL);
		}
		
		logger.info("update ad end,rlt:{}", JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	@PostMapping("/list")
	@HystrixCommand(fallbackMethod = "queryListFallback")
	public Object queryList(@RequestBody AdMainQueryReq req){
		logger.info("查询广告列表  begin,reqParam:{}",req);
		ResultDto<?> rlt = null;
		
		PageInfo<AdMainDto> page = adMainService.query(req);
		if(null == page){
			rlt = ResultDto.result(ResultCodeEnum.Advertisment.QUERY_AD_LIST_FAIL);
		}else{
			PageDto<AdMainDto> pd = new PageDto<>();
			pd.setCurPage(page.getPageNum());
			pd.setTotalCount((int)page.getTotal());
			pd.setTotalPage(page.getPages());
			pd.setPageSize(page.getPageSize());
			pd.setList(page.getList());
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, pd);
		}
		
		logger.info("查询广告列表  end,rlt:{}", JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	@PostMapping("/putAd")
	@HystrixCommand(fallbackMethod = "putAdFallback")
	public Object putAd(@RequestBody AdPutCommand command){
		logger.info("putAd begin,reqParam:{}",command);
		ResultDto<?> rlt = null;
		
		int addResult = adPutService.add(command);
		if(param_error == addResult){
			rlt = new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR);
		}else if(_0 == addResult){
			rlt = ResultDto.result(ResultCodeEnum.Advertisment.PUT_AD_FAIL);
		}else{
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		}
		
		logger.info("putAd end,rlt:{}",JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	@PostMapping("/putList")
	@HystrixCommand(fallbackMethod = "putListFallback")
	public Object queryPutList(@RequestBody AdPutQueryReq req){
		logger.info("查询投放广告列表 begin,reqParam:{}",req);
		ResultDto<?> rlt = null;
		
		PageInfo<AdPutDto> page = adPutService.query(req);
		if(null == page){
			rlt = ResultDto.result(ResultCodeEnum.Advertisment.QUERY_PUTAD_LIST_FAIL);
		}else{
			PageDto<AdPutDto> pd = new PageDto<>();
			pd.setCurPage(page.getPageNum());
			pd.setTotalCount((int)page.getTotal());
			pd.setTotalPage(page.getPages());
			pd.setPageSize(page.getPageSize());
			pd.setList(page.getList());
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, pd);
		}
		
		logger.info("查询投放广告列表  end,rlt:{}", JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	@PostMapping("/updatePutAd")
	@HystrixCommand(fallbackMethod = "updatePutAdFallback")
	public Object updatePutAd(@RequestBody AdPutUpdateReq req){
		logger.info("updatePutAd begin,reqParam:{}", req);
		ResultDto<?> rlt = null;
		
		int updateResult = adPutService.update(req);
		if(_1 == updateResult){
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		}else{
			rlt = ResultDto.result(ResultCodeEnum.Advertisment.UPDATE_PUTAD_FAIL);
		}
		
		logger.info("update ad end,rlt:{}", JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	@PostMapping("/queryDetailList")
	@HystrixCommand(fallbackMethod = "queryDetailListFallback")
	public Object queryDetailList(@RequestBody AdDetailCommand command){
		logger.info("查询当前生效广告列表  begin,reqParam:{}",command);
		ResultDto<?> rlt = null;
		
		PageInfo<AdDetailDto> page = adPutService.queryDetailForC(command);
		if(null == page){
			rlt = ResultDto.result(ResultCodeEnum.Advertisment.AD_DETAIL_LIST_FAIL);
		}else{
			List<AdDetailDto> list = new ArrayList<>();
			list = page.getList();
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, list);
		}
		
		logger.info("查询当前生效广告列表   end,rlt:{}", JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	public Object addFallback(@RequestBody AdMainCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object updateFallback(@RequestBody AdMainCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object queryListFallback(@RequestBody AdMainQueryReq req) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object putAdFallback(@RequestBody AdPutCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object putListFallback(@RequestBody AdPutQueryReq req) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object updatePutAdFallback(@RequestBody AdPutUpdateReq req) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object queryDetailListFallback(@RequestBody AdDetailCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
}
