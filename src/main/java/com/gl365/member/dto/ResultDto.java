package com.gl365.member.dto;

import java.io.Serializable;

import com.gl365.member.common.ResultCodeEnum;

/**
 * < 基础结果DTO响应 >
 */
public class ResultDto<T> implements Serializable {

	private static final long serialVersionUID = -8938751868672313574L;

	/**
	 * result : 响应码
	 */
	private String result;

	/**
	 * description ： 响应描述
	 */
	private String description;

	/**
	 * data : 结果数据
	 */
	private T data;

	public ResultDto() {
	}

	public ResultDto(T data) {
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.System source) {
		this.result = source.getCode();
		this.description = source.getMsg();
	}

	public ResultDto(ResultCodeEnum.System source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.System source, String message, T data) {
		this.result = source.getCode();
		this.description = message;
		this.data = data;
	}
	
	public ResultDto(String result, String message, T data) {
		this.result = result;
		this.description = message;
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.User source) {
		this.result = source.getCode();
		this.description = source.getMsg();
	}

	public ResultDto(ResultCodeEnum.User source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.User source, String message, T data) {
		this.result = source.getCode();
		this.description = message;
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.Customize source) {
		this.result = source.getCode();
		this.description = source.getMsg();
	}

	public ResultDto(ResultCodeEnum.Customize source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.Customize source, String message, T data) {
		this.result = source.getCode();
		this.description = message;
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.Merchant source) {
		this.result = source.getCode();
		this.description = source.getMsg();
	}

	public ResultDto(ResultCodeEnum.Merchant source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.Merchant source, String message, T data) {
		this.result = source.getCode();
		this.description = message;
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.Sms source) {
		this.result = source.getCode();
		this.description = source.getMsg();
	}

	public ResultDto(ResultCodeEnum.Sms source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.Sms source, String message, T data) {
		this.result = source.getCode();
		this.description = message;
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.Payment source) {
		this.result = source.getCode();
		this.description = source.getMsg();
	}

	public ResultDto(ResultCodeEnum.Payment source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.Payment source, String message, T data) {
		this.result = source.getCode();
		this.description = message;
		this.data = data;
	}

	public static ResultDto<?> getErrInfo() {
		ResultDto<?> result = new ResultDto<>();
		result.setResult(ResultCodeEnum.System.SYSTEM_ERROR.getCode());
		result.setDescription(ResultCodeEnum.System.SYSTEM_ERROR.getMsg());
		return result;
	}
	
	public static ResultDto<?> result(ResultCodeEnum.Advertisment source) {
		ResultDto<?> result = new ResultDto<>();
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}
	
	public static ResultDto<?> result(ResultCodeEnum.System source, Object data) {
		ResultDto<?> result = new ResultDto<>(data);
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
