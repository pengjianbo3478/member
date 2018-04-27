package com.gl365.member.dto.users.relation;

import io.swagger.annotations.ApiModelProperty;

public class CreateUserReq {

	@ApiModelProperty("必填,第三方支付的id,如微信的openId,支付宝的aliPayId")
	private String payOrganId;

	@ApiModelProperty("必填,第三方支付渠道。wx：微信，zfb：支付宝")
	private String channel;

	@ApiModelProperty("推荐机构类型")
	private String recommendAgentType;

	@ApiModelProperty("第三方头像")
	private String photo;

	@ApiModelProperty("第三方昵称")
	private String nickName;

	@ApiModelProperty("机构ID")
	private String recommendAgentId;

	@ApiModelProperty("推荐人userId")
	private String recommendBy;

	@ApiModelProperty("推荐人店长userId")
	private String recommendShopManager;

	public String getPayOrganId() {
		return payOrganId;
	}

	public void setPayOrganId(String payOrganId) {
		this.payOrganId = payOrganId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRecommendAgentType() {
		return recommendAgentType;
	}

	public void setRecommendAgentType(String recommendAgentType) {
		this.recommendAgentType = recommendAgentType;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRecommendAgentId() {
		return recommendAgentId;
	}

	public void setRecommendAgentId(String recommendAgentId) {
		this.recommendAgentId = recommendAgentId;
	}

	public String getRecommendBy() {
		return recommendBy;
	}

	public void setRecommendBy(String recommendBy) {
		this.recommendBy = recommendBy;
	}

	public String getRecommendShopManager() {
		return recommendShopManager;
	}

	public void setRecommendShopManager(String recommendShopManager) {
		this.recommendShopManager = recommendShopManager;
	}
}
