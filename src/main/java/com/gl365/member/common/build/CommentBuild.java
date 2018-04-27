package com.gl365.member.common.build;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;

import com.gl365.member.common.DBConstants;
import com.gl365.member.dto.comment.CommentDto;
import com.gl365.member.dto.merchant.UnCommentDto;
import com.gl365.member.dto.merchant.command.SaveComment4MemberCommand;
import com.gl365.member.dto.merchant.command.UpdateComment4MemberCommand;
import com.gl365.member.model.Comment;

public class CommentBuild {

	public static UnCommentDto UnCommentDtoBuild(Comment source, Object obj) {
		UnCommentDto target = new UnCommentDto();
		target.setComment(false);
		// target.setMerchantName(source.get);
		target.setMerchantNo(source.getMerchantNo());
		// target.setMoney(money);
		// target.setPaymentDate(paymentDate);
		target.setPaymentNo(source.getPaymentNo());
		// target.setScene(scene);
		return target;
	}

	public static Comment buildUpdatePaymentBean(SaveComment4MemberCommand source) {
		Comment updateDate = new Comment();
		updateDate.setUserId(source.getUserId());
		updateDate.setMerchantNo(source.getMerchantNo());
		updateDate.setPaymentNo(source.getPaymentNo());
		// updateDate.set(true);
		// updateDate.setCommentTime(LocalDateTime.now());
		return updateDate;
	}

	public static CommentDto CommentDtoBuild(Comment source) {
		CommentDto target = new CommentDto();
		target.setContent(source.getContent());
		target.setCreateTime(source.getCreateTime());
		target.setAccount(source.getAccount());
		target.setGrade(source.getGrade());
		target.setId(source.getId());
		target.setMerchantNo(source.getMerchantNo());
		target.setUserId(source.getUserId());
		target.setPaymentNo(source.getPaymentNo());
		return target;
	}

	public static List<CommentDto> CommentDtoBuild(List<Comment> source) {
		if (CollectionUtils.isEmpty(source))
			return new ArrayList<>();
		List<CommentDto> target = new ArrayList<>();
		for (Comment bean : source) {
			CommentDto dto = CommentDtoBuild(bean);
			target.add(dto);
		}
		return target;
	}

	public static Comment buildCreatComment(SaveComment4MemberCommand source) {
		Comment target = new Comment();
		target.setId(UUID.randomUUID().toString().replace("-", ""));
		target.setUserId(source.getUserId());
		target.setPaymentNo(source.getPaymentNo());
		target.setMerchantNo(source.getMerchantNo());
		target.setGrade(DBConstants.MAX_GRADE);
		target.setCreateTime(LocalDateTime.now());
		target.setAccount(source.getAccount());
		return target;
	}

	public static Comment buildUpdateComment(UpdateComment4MemberCommand source, String key) {
		// 只更新需要的字段
		Comment target = new Comment();
		target.setId(key);
		target.setGrade(source.getGrade());
		target.setCreateTime(LocalDateTime.now());
		target.setStatus(DBConstants.REAL_COMMENT);
		return target;
	}

}
