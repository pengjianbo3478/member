package com.gl365.member.service;

import com.gl365.member.dto.PageResultDto;
import com.gl365.member.dto.comment.CommentDto;
import com.gl365.member.dto.merchant.command.GetUnComments4MemberCommand;

public interface CommentPaymentService {

	/**
	 * 获取未评论集合
	 * 
	 * @param command
	 * @return
	 */
	public PageResultDto<CommentDto> getUnCommentList(GetUnComments4MemberCommand command);
}
