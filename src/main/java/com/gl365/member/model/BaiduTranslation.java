package com.gl365.member.model;

import java.time.LocalDateTime;
import java.util.Date;

public class BaiduTranslation {
    private String id;

    private String merchantShortname;

    private String searchName;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;
    
    private String translationName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantShortname() {
        return merchantShortname;
    }

    public void setMerchantShortname(String merchantShortname) {
        this.merchantShortname = merchantShortname;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getTranslationName() {
		return translationName;
	}

	public void setTranslationName(String translationName) {
		this.translationName = translationName;
	}

}