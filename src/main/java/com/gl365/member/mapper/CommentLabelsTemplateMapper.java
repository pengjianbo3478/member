package com.gl365.member.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gl365.member.model.CommentLabelsTemplate;

@Repository
public interface CommentLabelsTemplateMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(CommentLabelsTemplate record);

	int insertSelective(CommentLabelsTemplate record);

	CommentLabelsTemplate selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CommentLabelsTemplate record);

	int updateByPrimaryKey(CommentLabelsTemplate record);

	/**
	 * 根据行业ID获取模板
	 * 
	 * @param industry
	 * @return
	 */
	List<CommentLabelsTemplate> getTempleteByIndustry(String industry);
}