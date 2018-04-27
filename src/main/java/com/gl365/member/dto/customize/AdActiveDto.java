package com.gl365.member.dto.customize;

import java.io.Serializable;

public class AdActiveDto implements Serializable {

	private static final long serialVersionUID = -6316181987903058196L;

	private String imageUrl;

	private String url;
	
	private String adTitle;

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

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}
}
