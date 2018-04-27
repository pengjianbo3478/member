package com.gl365.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.member.common.DBConstants;
import com.gl365.member.common.DBConstants.GRADE_LEAVE;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.ResultCodeEnum;
import com.gl365.member.common.build.CommentBuild;
import com.gl365.member.common.build.CommentPersonalBuild;
import com.gl365.member.dto.PageDto;
import com.gl365.member.dto.PageResultDto;
import com.gl365.member.dto.PersonalCommentStatus;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.comment.CommentDto;
import com.gl365.member.dto.comment.CommentPersonalDto;
import com.gl365.member.dto.comment.command.GetOperatorScoreCommand;
import com.gl365.member.dto.comment.command.GetOperatorScoreDetailed4MerchantCommand;
import com.gl365.member.dto.comment.command.GetScoreDetailed4MerchantCommand;
import com.gl365.member.dto.comment.command.SaveCommentPersonal4MemberCommand;
import com.gl365.member.dto.customize.command.GetCommentTempleteCommand;
import com.gl365.member.dto.merchant.MerchantInfo;
import com.gl365.member.dto.merchant.command.GetMerchantCommentsCommand;
import com.gl365.member.dto.merchant.command.GetMerchantDetail4MerchantCommand;
import com.gl365.member.dto.merchant.command.SaveComment4MemberCommand;
import com.gl365.member.dto.merchant.command.UpdateComment4MemberCommand;
import com.gl365.member.dto.merchant.command.UpdateMerchantCommentCountCommand;
import com.gl365.member.dto.users.MerchantOperatorDto;
import com.gl365.member.mapper.CommentLabelsMapper;
import com.gl365.member.mapper.CommentLabelsTemplateMapper;
import com.gl365.member.mapper.CommentMapper;
import com.gl365.member.mapper.CommentPersonalMapper;
import com.gl365.member.model.Comment;
import com.gl365.member.model.CommentGradeLeave;
import com.gl365.member.model.CommentLabels;
import com.gl365.member.model.CommentLabelsTemplate;
import com.gl365.member.model.CommentPersonal;
import com.gl365.member.model.User;
import com.gl365.member.mq.producer.MerchantCommentGradeProducer;
import com.gl365.member.service.CommentService;
import com.gl365.member.service.UserService;
import com.gl365.member.service.repo.MerchantInfoServiceRepo;
import com.gl365.member.service.repo.OperatorInfoServiceRepo;

/**
 * < 商家评论Service >
 * 
 * @author hui.li 2017年4月20日 - 下午3:00:42
 * @Since 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

	private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Autowired
	private UserService userService;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private CommentLabelsMapper commentLabelsMapper;
	@Autowired
	private CommentPersonalMapper commentPersonalMapper;
	@Autowired
	private MerchantInfoServiceRepo merchantInfoServiceRepo;
	@Autowired
	private OperatorInfoServiceRepo operatorInfoServiceRepo;
	@Autowired
	private CommentLabelsTemplateMapper commentLabelsTemplateMapper;
	@Autowired
	private MerchantCommentGradeProducer merchantCommentGradeProducer;

	@Override
	public ResultDto<List<CommentLabelsTemplate>> getTemplete(GetCommentTempleteCommand command) {
		LOG.info("获取商家评论模板 > > > 行业ID：{}", command.getIndustry());
		try {
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, commentLabelsTemplateMapper.getTempleteByIndustry(command.getIndustry()));
		} catch (Exception e) {
			LOG.error("获取商家评论模板 > > > 失败, 错误：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public PageResultDto<CommentDto> getCommentList(GetMerchantCommentsCommand command) {
		LOG.info("获取商家评论列表 > > > 入参：{}", JsonUtils.toJsonString(command));
		try {
			// 查询评论列表
			PageDto<Comment> page = getCommentBeanPage(command);
			// 构建列表Dto 【已评论】绑定会员信息【未评论】绑定账户信息
			PageDto<CommentDto> result = command.isUnComment() ? getUserCommentDto(page) : getPaymentCommentDto(page);
			LOG.debug("获取商家评论列表结果 > > > 出参：{}", result);
			return new PageResultDto<>(ResultCodeEnum.System.SUCCESS, result);
		} catch (Exception e) {
			LOG.error("获取商家评论列表失败 > > > 异常：{}", e);
			return new PageResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public ResultDto<Integer> saveUpdateCommentPersonal(SaveCommentPersonal4MemberCommand command) {
		LOG.info("保存/更新打赏评论》》》入参：{}", JsonUtils.toJsonString(command));
		try {
			// 原支付订单号查询已存在的订单
			List<CommentPersonal> result = commentPersonalMapper.selectByUidMnoPno(command.getUserId(), command.getMerchantNo(), command.getPaymentNo());
			if (CollectionUtils.isEmpty(result))
				commentPersonalMapper.insertSelective(CommentPersonalBuild.buildCreateCommentPersonal(command));
			else
				commentPersonalMapper.updateByUidMnoPno(CommentPersonalBuild.buildUpdateCommentPersonal(command));
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		} catch (Exception e) {
			LOG.error("保存/更新打赏评论》》》错误：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}

	}

	@Override
	public ResultDto<?> saveDefaultComment(SaveComment4MemberCommand command) {
		LOG.info("保存商家初始评论 > > > 入参：{}", JsonUtils.toJsonString(command));
		try {
			// 保存初始评论
			commentMapper.insertSelective(CommentBuild.buildCreatComment(command));
			// 同步商家评论统计信息
			merchantCommentGradeProducer.send(new UpdateMerchantCommentCountCommand(command.getMerchantNo(), DBConstants.MAX_GRADE, DBConstants.AUTO_INCREASE_1));
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		} catch (Exception e) {
			LOG.error("保存商家初始评论失败 > > > 异常：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public ResultDto<?> updateComment(UpdateComment4MemberCommand command) {
		LOG.info("更新商家评论 > > > 入参：{}", JsonUtils.toJsonString(command));
		try {
			// 获取初始评论
			Comment comment = commentMapper.selectByUidMnoPno(command.getUserId(), command.getMerchantNo(), command.getPaymentNo());
			if (null == comment) {
				LOG.error("更新商家评论 > > > 未查询到对应评论!");
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			}
			// 判断重复评论
			if (DBConstants.REAL_COMMENT.equals(comment.getStatus()))
				return new ResultDto<>(ResultCodeEnum.Merchant.REAL_COMMENT.getMsg());
			// 更新评论信息
			commentMapper.updateByPrimaryKeySelective(CommentBuild.buildUpdateComment(command, comment.getId()));
			// 保存评论标签
			saveCommentLabels(command, comment.getId());
			// 同步商家评论统计信息
			merchantCommentGradeProducer.send(new UpdateMerchantCommentCountCommand(command.getMerchantNo(), command.getGrade() - DBConstants.MAX_GRADE, DBConstants.AUTO_INCREASE_0));
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		} catch (Exception e) {
			LOG.info("更新商家评论失败 > > > 错误：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public ResultDto<?> backRecoverComment(UpdateComment4MemberCommand command) {
		// 获取评论ID
		LOG.error("更新商家评论 > > > 入参：{}", JsonUtils.toJsonString(command));
		try {
			Comment comment = commentMapper.selectByUidMnoPno(command.getUserId(), command.getMerchantNo(), command.getPaymentNo());
			if (null == comment) {
				LOG.error("更新商家评论 > > > 未查询到对应评论!");
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			}
			Integer addGrade = command.isDelete() ? -comment.getGrade() : comment.getGrade();
			Integer addAccount = command.isDelete() ? -DBConstants.AUTO_INCREASE_1 : DBConstants.AUTO_INCREASE_1;
			Integer status = command.isDelete() ? DBConstants.CX : DBConstants.XF;
			// 更新评论信息
			Comment updateBean = new Comment();
			updateBean.setId(comment.getId());
			updateBean.setUserId(command.getUserId());
			updateBean.setMerchantNo(command.getMerchantNo());
			updateBean.setPaymentNo(command.getPaymentNo());
			updateBean.setStatus(status);
			commentMapper.updateByPrimaryKeySelective(updateBean);
			// 同步商家评论统计信息
			merchantCommentGradeProducer.send(new UpdateMerchantCommentCountCommand(command.getMerchantNo(), addGrade, addAccount));
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
		} catch (Exception e) {
			LOG.error("更新商家评论 > > > 错误：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public ResultDto<List<CommentPersonalDto>> getOperatorScore(GetOperatorScoreCommand command) {
		LOG.info("获取用户对员工评价分数》》》入参：{}", JsonUtils.toJsonString(command));
		try {
			List<CommentPersonal> data = commentPersonalMapper.selectOperatorScoreByMerchantNoOperatorId(command.getMerchantNo(), command.getOperatorId());
			if (CollectionUtils.isEmpty(data))
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS, null);
			List<CommentPersonalDto> result = new ArrayList<>();
			for (CommentPersonal bean : data) {
				CommentPersonalDto dto = new CommentPersonalDto();
				BeanUtils.copyProperties(bean, dto);
				result.add(dto);
				MerchantOperatorDto operator = null;
				if (StringUtils.isEmpty(bean.getOperatorId()) || null == (operator = operatorInfoServiceRepo.queryOperator(bean.getOperatorId()).getData()))
					continue;
				dto.setOperatorName(operator.getOperatorName());
				dto.setRoleId(operator.getRoleId());
				// 操作员的会员基本信息
				User user = userService.getUserInfoByUserId(operator.getUserId());
				if (null == user) {
					LOG.debug("获取用户对员工评价分数》》》警告：会员[{}]不存在", dto.getUserId());
					continue;
				}
				dto.setOperatorImage(userService.addImagePrefix(user.getPhoto()));
			}
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, result);
		} catch (Exception e) {
			LOG.error("获取用户对员工评价分数》》》错误：{}", e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public PageResultDto<CommentDto> getScoreDetailed(GetScoreDetailed4MerchantCommand command) {
		LOG.info("获取本店评价明细》》》入参：{}", JsonUtils.toJsonString(command));
		try {
			// 查询评论列表
			PageDto<Comment> page = getCommentBeanPage(command);
			// 构建评论Dto,绑定用户信息
			PageDto<CommentDto> result = getUserCommentDto(page);
			LOG.debug("获取本店评价明细》》》出参：{}", JsonUtils.toJsonString(result));
			return new PageResultDto<>(ResultCodeEnum.System.SUCCESS, result);
		} catch (Exception e) {
			LOG.error("获取本店评价明细 > > > 异常：{}", e);
			return new PageResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public PageResultDto<CommentPersonalDto> getOperatorScoreDetailed(GetOperatorScoreDetailed4MerchantCommand command) {
		LOG.info("获取用户对员工评价明细》》》入参：{}", JsonUtils.toJsonString(command));
		try {
			// 查询评论列表
			PageDto<CommentPersonal> page = getCommentPersonalBeanPage(command);
			PageDto<CommentPersonalDto> result = getOperatorCommentDto(page);
			return new PageResultDto<>(ResultCodeEnum.System.SUCCESS, result);
		} catch (Exception e) {
			LOG.error("获取商家评论列表失败 > > > 异常：{}", e);
			return new PageResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
	}

	@Override
	public Comment getCommentInfo(String userId, String merchantNo, String paymentNo) {
		try {
			if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(merchantNo) && StringUtils.isEmpty(paymentNo)) {
				LOG.error("reqParams are null,return null");
				return null;
			}
			return commentMapper.selectByUidMnoPno(userId, merchantNo, paymentNo);
		} catch (Exception e) {
			LOG.error("reqParams are null,return null：exception :{}", e);
			return null;
		}
	}

	@Override
	public List<PersonalCommentStatus> getCommentPersonalStatus(List<String> paymentNos) {
		LOG.info("getCommentStatus begin,paymentNos={}", JsonUtils.toJsonString(paymentNos));
		List<PersonalCommentStatus> rlt = new ArrayList<>();
		if (null == paymentNos) {
			return rlt;
		}
		try {
			rlt = commentPersonalMapper.queryCommentStatus(paymentNos);
		} catch (Exception e) {
			LOG.error("getCommentStatus ===> commentPersonalMapper.queryCommentStatus exception:", e);
		}
		LOG.info("getCommentStatus end,rlt:{}", rlt);
		return rlt;
	}

	private PageDto<CommentPersonalDto> getOperatorCommentDto(PageDto<CommentPersonal> page) {
		if (CollectionUtils.isEmpty(page.getList())) {
			return new PageDto<>(page.getTotalCount(), page.getCurPage(), page.getPageSize(), page.getDataMap(), null);
		}
		List<CommentPersonalDto> result = new ArrayList<>();
		for (CommentPersonal comment : page.getList()) {
			CommentPersonalDto dto = new CommentPersonalDto();
			BeanUtils.copyProperties(comment, dto);
			// 构建操作员信息
			MerchantOperatorDto operator = operatorInfoServiceRepo.queryOperator(dto.getOperatorId()).getData();
			if (null != operator) {
				dto.setOperatorName(operator.getOperatorName());
				dto.setRoleId(operator.getRoleId());
			}
			result.add(dto);
		}
		return new PageDto<>(page.getTotalCount(), page.getCurPage(), page.getPageSize(), page.getDataMap(), result);
	}

	@SuppressWarnings("serial")
	private PageDto<CommentDto> getPaymentCommentDto(PageDto<Comment> page) {
		if (CollectionUtils.isEmpty(page.getList())) {
			return new PageDto<>(page.getTotalCount(), page.getCurPage(), page.getPageSize(), null);
		}
		List<CommentDto> resultList = new ArrayList<>();
		for (Comment comment : page.getList()) {
			CommentDto dto = CommentBuild.CommentDtoBuild(comment);
			resultList.add(dto);
			// 商家详情信息构建
			GetMerchantDetail4MerchantCommand command = new GetMerchantDetail4MerchantCommand();
			command.setMerchantNo(new ArrayList<String>() {
				{
					add(dto.getMerchantNo());
				}
			});
			// TODO 为了两个字段不需要取带关联信息的商家详情
			ResultDto<List<MerchantInfo>> merchantInfo = merchantInfoServiceRepo.getMerchantDetail(command);
			if (null != merchantInfo && CollectionUtils.isNotEmpty(merchantInfo.getData())) {
				dto.setUserImage(merchantInfo.getData().get(0).getMainImage());
				dto.setRealName(merchantInfo.getData().get(0).getMerchantShortname());
			}

		}
		return new PageDto<>(page.getTotalCount(), page.getCurPage(), page.getPageSize(), resultList);
	}

	private PageDto<CommentDto> getUserCommentDto(PageDto<Comment> page) {
		if (CollectionUtils.isEmpty(page.getList())) {
			return new PageDto<>(page.getTotalCount(), page.getCurPage(), page.getPageSize(), page.getDataMap(), null);
		}
		List<CommentDto> resultList = new ArrayList<>();
		for (Comment comment : page.getList()) {
			CommentDto dto = CommentBuild.CommentDtoBuild(comment);
			// 用户头像 昵称
			User user = userService.getUserInfoByUserId(comment.getUserId());
			if (null != user) {
				dto.setRealName(user.getNickName());
				dto.setUserImage(userService.addImagePrefix(user.getPhoto()));
			}
			// 评论标签
			List<CommentLabels> labels = commentLabelsMapper.selectByCommentId(comment.getId());
			if (CollectionUtils.isNotEmpty(labels)) {
				List<String> labelsId = new ArrayList<>();
				for (CommentLabels bean : labels) {
					labelsId.add(bean.getCommentLabelName());
				}
				dto.setLabels(labelsId);
			}
			resultList.add(dto);
		}
		return new PageDto<>(page.getTotalCount(), page.getCurPage(), page.getPageSize(), page.getDataMap(), resultList);
	}

	private PageDto<Comment> getCommentBeanPage(GetMerchantCommentsCommand command) {
		int curPage = command.getCurPage();
		int pageSize = command.getPageSize();
		Map<String, Object> param = new HashMap<>();
		param.put("merchantNo", command.getMerchantNo());
		param.put("status", command.isUnComment());
		param.put("userId", command.getUserId());
		param.put("begin", (curPage - 1) * pageSize);
		param.put("end", curPage * pageSize);
		int total = commentMapper.selectCountByMerchantNo(param);
		List<Comment> data = commentMapper.selectByMerchantNo(param);
		return new PageDto<>(total, curPage, pageSize, data);
	}

	private PageDto<Comment> getCommentBeanPage(GetScoreDetailed4MerchantCommand command) {
		int curPage = command.getCurPage();
		int pageSize = command.getPageSize();
		Map<String, Object> param = new HashMap<>();
		param.put("merchantNo", command.getMerchantNo());
		param.put("status", null); // 是否为真实评论,默认全部带出
		param.put("type", DBConstants.GRADE_MAP.get(command.getCommentType()));
		param.put("begin", (curPage - 1) * pageSize);
		param.put("end", curPage * pageSize);
		int total = commentMapper.selectCountByMerchantNo(param);
		List<Comment> data = commentMapper.selectByMerchantNo(param);
		// 评分等级统计
		param.put("good", DBConstants.GRADE_MAP.get(GRADE_LEAVE.GOOD.getValue()));
		param.put("medium", DBConstants.GRADE_MAP.get(GRADE_LEAVE.MEDIUM.getValue()));
		param.put("bad", DBConstants.GRADE_MAP.get(GRADE_LEAVE.BAD.getValue()));
		List<CommentGradeLeave> leaves = commentMapper.selectGradeLeaveByMerchantNo(param);
		Map<String, Object> dataMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(leaves)) {
			for (CommentGradeLeave leave : leaves) {
				dataMap.put("goodCount", leave.getGood());
				dataMap.put("differenceCount", leave.getBad());
				dataMap.put("mediumCount", leave.getMedium());
			}
		}
		return new PageDto<>(total, curPage, pageSize, dataMap, data);
	}

	private PageDto<CommentPersonal> getCommentPersonalBeanPage(GetOperatorScoreDetailed4MerchantCommand command) {
		int curPage = command.getCurPage();
		int pageSize = command.getPageSize();
		Map<String, Object> param = new HashMap<>();
		param.put("merchantNo", command.getMerchantNo());
		param.put("operatorId", command.getOperatorId());
		param.put("type", DBConstants.GRADE_MAP.get(command.getCommentType()));
		param.put("begin", (curPage - 1) * pageSize);
		param.put("end", curPage * pageSize);
		int total = commentPersonalMapper.selectCommentPersonalCount(param);
		List<CommentPersonal> data = commentPersonalMapper.selectCommentPersonalPage(param);
		// 评分等级统计
		param.put("good", DBConstants.GRADE_MAP.get(GRADE_LEAVE.GOOD.getValue()));
		param.put("medium", DBConstants.GRADE_MAP.get(GRADE_LEAVE.MEDIUM.getValue()));
		param.put("bad", DBConstants.GRADE_MAP.get(GRADE_LEAVE.BAD.getValue()));
		List<CommentGradeLeave> leaves = commentPersonalMapper.selectGradeLeaveByMerchantNo(param);
		Map<String, Object> dataMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(leaves)) {
			for (CommentGradeLeave leave : leaves) {
				dataMap.put("goodCount", leave.getGood());
				dataMap.put("differenceCount", leave.getBad());
				dataMap.put("mediumCount", leave.getMedium());
			}
		}
		return new PageDto<>(total, curPage, pageSize, dataMap, data);
	}

	private void saveCommentLabels(UpdateComment4MemberCommand command, String id) {
		Integer[] labels = command.getLabels();
		if (null != labels && labels.length > 0) {
			// 保存商家评论标签
			for (Integer label : labels) {
				CommentLabels insertLabel = new CommentLabels();
				insertLabel.setCommentLabelId(label);
				insertLabel.setMerchantNo(command.getMerchantNo());
				insertLabel.setCommentId(id);
				commentLabelsMapper.insertSelective(insertLabel);
			}
		}
	}

}
