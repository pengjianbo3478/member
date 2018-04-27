package com.gl365.member.dto.customize;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * < 首页广告Dto >
 * 
 * @author hui.li 2017年4月21日 - 下午7:28:33
 * @Since 1.0
 */
public class AdvertisementDto implements Serializable {

	private static final long serialVersionUID = -8533898156266505488L;

	private String imageUrl;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime beginDatetime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDatetime;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDateTime getBeginDatetime() {
		return beginDatetime;
	}

	public void setBeginDatetime(LocalDateTime beginDatetime) {
		this.beginDatetime = beginDatetime;
	}

	public LocalDateTime getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(LocalDateTime endDatetime) {
		this.endDatetime = endDatetime;
	}

}
