package com.gl365.member.dto.Industry;

public class IndustryRltDto {

	private String categoryId;

	private String parentCategoryId;

	private String categoryName;

	private String categoryEvel;

	private String flag;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryEvel() {
		return categoryEvel;
	}

	public void setCategoryEvel(String categoryEvel) {
		this.categoryEvel = categoryEvel;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}