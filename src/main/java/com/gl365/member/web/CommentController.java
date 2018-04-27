package com.gl365.member.web;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.member.common.FallbackHandlerUtils;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.common.enums.CommentPersonalStatusEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.comment.command.GetOperatorScoreCommand;
import com.gl365.member.dto.comment.command.GetOperatorScoreDetailed4MerchantCommand;
import com.gl365.member.dto.comment.command.GetScoreDetailed4MerchantCommand;
import com.gl365.member.dto.comment.command.SaveCommentPersonal4MemberCommand;
import com.gl365.member.dto.merchant.command.GetCommentStatusReq;
import com.gl365.member.dto.merchant.command.GetMerchantCommentsCommand;
import com.gl365.member.dto.merchant.command.SaveComment4MemberCommand;
import com.gl365.member.dto.merchant.command.UpdateComment4MemberCommand;
import com.gl365.member.model.Comment;
import com.gl365.member.service.CommentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * < 商家评论Controller >
 * 
 * @since hui.li 2017年5月15日 下午1:49:18
 */
@RestController
@RequestMapping("/member/comment")
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private CommentService commentService;

	/**
	 * 获取员工综合评价分数
	 * 
	 * @param merchantNo
	 * @return
	 */
	@PostMapping("/getOperatorScore")
	@HystrixCommand(fallbackMethod = "getOperatorScoreFallback")
	public Object getOperatorScore(@RequestBody GetOperatorScoreCommand command) {
		return commentService.getOperatorScore(command);
	}

	/**
	 * 获取员工综合评价明细
	 * 
	 * @param getOperatorScoreDetailed4MerchantCommand
	 * @return
	 */
	@PostMapping("/getOperatorScoreDetailed")
	@HystrixCommand(fallbackMethod = "getOperatorScoreDetailedFallback")
	public Object getOperatorScoreDetailed(@RequestBody GetOperatorScoreDetailed4MerchantCommand getOperatorScoreDetailed4MerchantCommand) {
		return commentService.getOperatorScoreDetailed(getOperatorScoreDetailed4MerchantCommand);
	}

	/**
	 * 获取商家综合评价明细
	 * 
	 * @param getScoreDetailed4MerchantCommand
	 * @return
	 */
	@PostMapping("/getScoreDetailed")
	@HystrixCommand(fallbackMethod = "getScoreDetailedFallback")
	public Object getScoreDetailed(@RequestBody GetScoreDetailed4MerchantCommand getScoreDetailed4MerchantCommand) {
		return commentService.getScoreDetailed(getScoreDetailed4MerchantCommand);
	}

	/**
	 * 保存初始评论
	 * 
	 * @param command
	 * @return
	 */
	@PostMapping("saveCommentPersonal")
	public Object saveCommentPersonal(@RequestBody SaveCommentPersonal4MemberCommand command) {
		command.setStatus(CommentPersonalStatusEnum.ING.getValue());
		return commentService.saveUpdateCommentPersonal(command);
	}

	/**
	 * 保存初始评论
	 * 
	 * @param command
	 * @return
	 */
	@PostMapping("/saveComment")
	@HystrixCommand(fallbackMethod = "saveDefaultCommentFallback")
	public Object saveDefaultComment(@RequestBody SaveComment4MemberCommand command) {
		return commentService.saveDefaultComment(command);
	}

	/**
	 * 更新评论信息
	 * 
	 * @param command
	 * @return
	 */
	@PostMapping("/updateComment")
	@HystrixCommand(fallbackMethod = "updateCommentFallback")
	public Object updateComment(@RequestBody UpdateComment4MemberCommand command) {
		return commentService.updateComment(command);
	}

	/**
	 * 获取评论列表
	 * 
	 * @param command
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@PostMapping("/getCommentList")
	@HystrixCommand(fallbackMethod = "getCommentListFallback")
	public Object getCommentList(@RequestBody GetMerchantCommentsCommand command) {
		return commentService.getCommentList(command);
	}

	/**
	 * 获取评论状态
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping("/getCommentStatus")
	@HystrixCommand(fallbackMethod = "getCommentStatusFallback")
	public Object getCommentStatus(@RequestBody GetCommentStatusReq req) {
		logger.info("getCommentStatus begin,req={}", JsonUtils.toJsonString(req));
		ResultDto<?> resp = null;
		if (null == req || StringUtils.isEmpty(req.getMerchantNo()) || StringUtils.isEmpty(req.getPaymentNo()) || StringUtils.isEmpty(req.getUserId())) {
			logger.error("getCommentStatus req's param is null");
			resp = new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
		} else {
			try {
				Comment comment = commentService.getCommentInfo(req.getUserId(), req.getMerchantNo(), req.getPaymentNo());
				if (comment == null) {
					resp = new ResultDto<Integer>(ResultCodeEnum.System.SUCCESS, null);
				} else {
					resp = new ResultDto<Integer>(ResultCodeEnum.System.SUCCESS, comment.getStatus());
				}
			} catch (Exception e) {
				logger.error("getCommentStatus ===> commentService.getCommentStatus exception,e:"+ e);
				resp = ResultDto.getErrInfo();
			}
		}
		logger.info("getCommentStatus end,resp:{}", JsonUtils.toJsonString(resp));
		return resp;
	}

	@PostMapping("/personalComment/status")
	@HystrixCommand(fallbackMethod = "personalCommentStatusFallback")
	public Object getPersonalCommentStatus(@RequestBody String req) {
		logger.info("getPersonalCommentStatus begin,req={}", req);
		if (StringUtils.isBlank(req)) {
			return null;
		}
		List<String> paymentNos = Arrays.asList(req.split(","));
		return commentService.getCommentPersonalStatus(paymentNos);
	}

	public Object saveDefaultCommentFallback(@RequestBody SaveComment4MemberCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object updateCommentFallback(@RequestBody UpdateComment4MemberCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getCommentListFallback(@RequestBody GetMerchantCommentsCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getCommentStatusFallback(@RequestBody GetCommentStatusReq req) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getScoreDetailedFallback(@RequestBody GetScoreDetailed4MerchantCommand getScoreDetailed4MerchantCommand) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getOperatorScoreFallback(@RequestBody GetOperatorScoreCommand command) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object getOperatorScoreDetailedFallback(@RequestBody GetOperatorScoreDetailed4MerchantCommand getOperatorScoreDetailed4MerchantCommand) {
		return FallbackHandlerUtils.timeOutResult();
	}

	public Object personalCommentStatusFallback(@RequestBody String req) {
		return FallbackHandlerUtils.timeOutResult();
	}
}
