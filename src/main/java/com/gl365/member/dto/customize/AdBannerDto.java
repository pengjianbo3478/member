package com.gl365.member.dto.customize;

import java.io.Serializable;

/**
 * < Banner广告Dto >
 * 
 * @author hui.li 2017年4月21日 - 下午7:28:33
 * @Since 1.0
 */
public class AdBannerDto implements Serializable {

	private static final long serialVersionUID = -6316181987903058196L;

	private String imageUrl;

	private String url;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
