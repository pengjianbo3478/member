package com.gl365.member.dto.users;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.member.common.enums.manage.AgentTypeEnum;
import com.gl365.member.common.enums.manage.RegistTypeEnum;

public class UserForMDto {

	/**
	 * 会员id
	 */
	private String userId;
	
	/**
	 * 机构类型 AgentTypeEnum
	 */
	private AgentTypeEnum agentType;

	/**
	 * 机构Id
	 */
	private String agentNo;

	/**
	 * 注册类型 RegistTypeEnum
	 */
	private RegistTypeEnum registType;

	/**
	 * 活动Id
	 */
	private String activeId;

	/**
	 * 手机号
	 */
	private String mobilePhone;

	/**
	 * 名称
	 */
	private String realName;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 渠道
	 */
	private String channel;
	
	/**
	 * 是否绑定
	 */
	private String isBind;

	/**
	 * 注册时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime beginRegisterTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endRegisterTime;

	/**
	 * 当前页
	 */
	private Integer curPage;

	/**
	 * 页面大小
	 */
	private Integer pageSize;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public AgentTypeEnum getAgentType() {
		return agentType;
	}

	public void setAgentType(AgentTypeEnum agentType) {
		this.agentType = agentType;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public RegistTypeEnum getRegistType() {
		return registType;
	}

	public void setRegistType(RegistTypeEnum registType) {
		this.registType = registType;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public LocalDateTime getBeginRegisterTime() {
		return beginRegisterTime;
	}

	public void setBeginRegisterTime(LocalDateTime beginRegisterTime) {
		this.beginRegisterTime = beginRegisterTime;
	}

	public LocalDateTime getEndRegisterTime() {
		return endRegisterTime;
	}

	public void setEndRegisterTime(LocalDateTime endRegisterTime) {
		this.endRegisterTime = endRegisterTime;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}
}
