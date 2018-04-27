package com.gl365.member.model;

public class Merchant {
	private double saleRate;

	private String merchantNo;

	private String shortName;

	private double longitude;

	private double latitude;
	
	private String[] specialService;

	private int perCapita;

	private double commentGrade;

	private String categoryName;

	private String areaName;

	private String[] image;

	public double getSaleRate() {
		return saleRate;
	}

	public void setSaleRate(double saleRate) {
		this.saleRate = saleRate;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String[] getSpecialService() {
		return specialService;
	}

	public void setSpecialService(String[] specialService) {
		this.specialService = specialService;
	}

	public int getPerCapita() {
		return perCapita;
	}

	public void setPerCapita(int perCapita) {
		this.perCapita = perCapita;
	}

	public double getCommentGrade() {
		return commentGrade;
	}

	public void setCommentGrade(double commentGrade) {
		this.commentGrade = commentGrade;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String[] getImage() {
		return image;
	}

	public void setImage(String[] image) {
		this.image = image;
	}

}
