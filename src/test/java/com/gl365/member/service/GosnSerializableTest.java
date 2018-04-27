package com.gl365.member.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.member.common.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GosnSerializableTest {

	public static void main(String[] args) {

		// 基本转换
		try {
			Dto dto = new Dto();
			dto.setTime(LocalDateTime.now());
			dto.setTimeString(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			String message = JsonUtils.toJsonString(dto);
			new Gson().fromJson(message, Dto.class);
		} catch (JsonSyntaxException e) {
			System.out.println(e);
		}

		// 实现序列化的转换
		try {
			DtoWithSeri dto = new DtoWithSeri();
			dto.setTime(LocalDateTime.now());
			dto.setTimeString(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			String message = JsonUtils.toJsonString(dto);
			new Gson().fromJson(message, DtoWithSeri.class);
		} catch (JsonSyntaxException e) {
			System.out.println(e);
		}

		// 区别LocalDateTime类型的转换
		try {
			Dto dto = new Dto();
			dto.setTime(LocalDateTime.now());
			dto.setTimeString(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			String message = JsonUtils.toJsonString(dto);
			new Gson().fromJson(message, DtoReceived.class);
		} catch (JsonSyntaxException e) {
			System.out.println(e);
		}
		// LocalDateTime 转String >>>
		// {"date":{"year":2017,"month":6,"day":5},"time":{"hour":17,"minute":37,"second":54,"nano":478000000}
	}
}

class DtoWithSeri implements Serializable {

	private static final long serialVersionUID = -2656098051246475079L;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime time;

	private String timeString;

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

}

class Dto {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime time;

	private String timeString;

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

}

class DtoReceived {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime time;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timeString;

	public LocalDateTime getTimeString() {
		return timeString;
	}

	public void setTimeString(LocalDateTime timeString) {
		this.timeString = timeString;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
