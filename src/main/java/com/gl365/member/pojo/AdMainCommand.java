package com.gl365.member.pojo;

import java.io.Serializable;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.enums.AdStatus;

public class AdMainCommand implements Serializable{

	private static final long serialVersionUID = 8273654953165271827L;
	
	private Integer id;
	
	private String adName;
	
	private String adImg;
	
	private String adUrl;
	
	private String adContacts;
	
	private String adPhone;
	
	private String adDetail;
	
	private AdStatus state;
	
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

	public AdStatus getState() {
		return state;
	}

	public void setState(AdStatus state) {
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
