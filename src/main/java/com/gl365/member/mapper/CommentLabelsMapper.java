package com.gl365.member.mapper;

import java.util.List;

import com.gl365.member.model.CommentLabels;

public interface CommentLabelsMapper {

    int insertSelective(CommentLabels record);
    
    List<CommentLabels> selectByCommentId(String commentId);
}