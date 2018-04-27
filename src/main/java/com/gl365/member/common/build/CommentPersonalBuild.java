package com.gl365.member.common.build;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gl365.member.dto.comment.command.SaveCommentPersonal4MemberCommand;
import com.gl365.member.model.CommentPersonal;

public class CommentPersonalBuild {

	public static CommentPersonal buildCreateCommentPersonal(SaveCommentPersonal4MemberCommand source) {
		CommentPersonal target = new CommentPersonal();
		target.setId(UUID.randomUUID().toString().replace("-", ""));
		target.setUserId(source.getUserId());
		target.setPaymentNo(source.getPaymentNo());
		target.setMerchantNo(source.getMerchantNo());

		target.setTipPaymentNo(source.getTipPaymentNo());
		target.setGrade(source.getGrade());
		target.setOperatorId(source.getOperatorId());
		target.setTip(source.getTip());
		target.setStatus(source.getStatus());
		target.setCreateTime(LocalDateTime.now());
		return target;
	}

	public static CommentPersonal buildUpdateCommentPersonal(SaveCommentPersonal4MemberCommand source) {
		CommentPersonal target = new CommentPersonal();
		target.setUserId(source.getUserId());
		target.setPaymentNo(source.getPaymentNo());
		target.setMerchantNo(source.getMerchantNo());

		target.setTipPaymentNo(source.getTipPaymentNo());
		target.setGrade(source.getGrade());
		target.setOperatorId(source.getOperatorId());
		target.setTip(source.getTip());
		target.setStatus(source.getStatus());
		return target;
	}

}
