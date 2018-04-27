package com.gl365.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.member.common.FallbackHandlerUtils;
import com.gl365.member.dto.merchant.command.GetUserFavorites4MemberCommand;
import com.gl365.member.dto.merchant.command.IsFavoriteCommand;
import com.gl365.member.dto.merchant.command.SaveFavorite4MemberCommand;
import com.gl365.member.dto.merchant.command.SaveMerchantReport4MemberCommand;
import com.gl365.member.service.MerchantFavoritesService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/member/merchant")
public class MerchantController {

	@Autowired
	private MerchantFavoritesService merchantFavoritesService;


	/**
	 * 是否收藏商家
	 * 
	 * @param command
	 * @return
	 */
	@PostMapping("/getIsFavorites")
	@HystrixCommand(fallbackMethod = "getIsFavoritesFallback")
	public Object getIsFavorites(@RequestBody IsFavoriteCommand command) {
		return merchantFavoritesService.getByUserIdMerchantNo(command);
	}

	/**
	 * 收藏商家
	 * 
	 * @param command
	 * @return
	 */
	@PostMapping("/saveFavorites")
	@HystrixCommand(fallbackMethod = "saveFavoritesFallback")
	public Object saveFavorites(@RequestBody SaveFavorite4MemberCommand command) {
		return merchantFavoritesService.saveFavorites(command);
	}

	/**
	 * 取消收藏商家
	 * 
	 * @param command
	 * @return
	 */
	@PostMapping("/deleteFavorites")
	@HystrixCommand(fallbackMethod = "deleteFavoritesFallback")
	public Object deleteFavorites(@RequestBody SaveFavorite4MemberCommand command) {
		return merchantFavoritesService.deleteFavorites(command);
	}

	/**
	 * 获取用户收藏的商家列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@PostMapping("/getUserFavorites")
	@HystrixCommand(fallbackMethod = "getUserFavoritesFallback")
	public Object getUserFavorites(@RequestBody GetUserFavorites4MemberCommand command) {
		return merchantFavoritesService.getFavoritesList(command);
	}
	
	/**
	 * 获取userIdList
	 * 
	 * @return
	 */
	@PostMapping("/getUserIdByMerchantNo/{merchantNo}/{curPage}/{pageSize}")
	@HystrixCommand(fallbackMethod = "getUserIdByMerchantNoFallback")
	public Object getUserIdByMerchantNo(@PathVariable("merchantNo") String merchantNo, @PathVariable("curPage") Integer curPage, @PathVariable("pageSize") Integer pageSize) {
		return merchantFavoritesService.getUserIdByMerchantNo(merchantNo,curPage,pageSize);
	}
	
	public Object getUserIdByMerchantNoFallback(@PathVariable("merchantNo") String merchantNo, @PathVariable("curPage") Integer curPage, @PathVariable("pageSize") Integer pageSize) {
		return FallbackHandlerUtils.timeOutResult();
	}
	
	public Object getIsFavoritesFallback(@RequestBody IsFavoriteCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object saveFavoritesFallback(@RequestBody SaveFavorite4MemberCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object deleteFavoritesFallback(@RequestBody SaveFavorite4MemberCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getUserFavoritesFallback(@RequestBody GetUserFavorites4MemberCommand command) {
		return FallbackHandlerUtils.timeOutResult(command.getCurPage());
	}

	public Object saveReportFallback(@RequestBody SaveMerchantReport4MemberCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

}
