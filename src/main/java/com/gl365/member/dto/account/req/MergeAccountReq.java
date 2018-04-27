package com.gl365.member.dto.account.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合并账户请求类
 * @author Biggo
 *
 */

@ApiModel(value = "MergeAccountReq", description = "合并账户请求类")
public class MergeAccountReq {

	/**
	 * 待合并的用户ID
	 */
	@ApiModelProperty(value="待合并的用户ID", required = true)
	private String fromUserId;
	
	/**
	 * 合并后的用户ID
	 */
	@ApiModelProperty(value="合并后的用户ID", required = true)
	private String toUserId;
	
	/**
	 * 发行渠道ID
	 */
	@ApiModelProperty(value="发行渠道ID", required = true)
	private String agendId;
	
	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getAgendId() {
		return agendId;
	}

	public void setAgendId(String agendId) {
		this.agendId = agendId;
	}
	
	
}
