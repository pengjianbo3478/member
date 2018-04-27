package com.gl365.member.dto.ad;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;

public class AdMainDto implements Serializable{

	private static final long serialVersionUID = 7002547503265847905L;

	private Integer id;
	
	private String adName;
	
	private String adImg;
	
	private String adUrl;
	
	private String adContacts;
	
	private String adPhone;
	
	private String adDetail;
	
	/**
	 * 审核状态0待审核，1审核通过，2审核不通过
	 */
	private Integer state;
	
	private String auditInfor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAdImg() {
		return adImg;
	}

	public void setAdImg(String adImg) {
		this.adImg = adImg;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public String getAdContacts() {
		return adContacts;
	}

	public void setAdContacts(String adContacts) {
		this.adContacts = adContacts;
	}

	public String getAdPhone() {
		return adPhone;
	}

	public void setAdPhone(String adPhone) {
		this.adPhone = adPhone;
	}

	public String getAdDetail() {
		return adDetail;
	}

	public void setAdDetail(String adDetail) {
		this.adDetail = adDetail;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAuditInfor() {
		return auditInfor;
	}

	public void setAuditInfor(String auditInfor) {
		this.auditInfor = auditInfor;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
