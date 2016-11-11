package com.hmsgtech.web.model;

import com.hmsgtech.domain.BuyOrderItem;

public class GoodsRankModel extends BuyOrderItem {

	private long id;
	private String name;
	private int price;
	private int quantity;
	private int ratingVal;
	private String gdescription;
	private String image1;
	private String image2;
	private String image3;
	private String image1class;
	private String image2class;
	private String image3class;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getRatingVal() {
		return ratingVal;
	}

	public void setRatingVal(int ratingVal) {
		this.ratingVal = ratingVal;
	}

	public String getGdescription() {
		return gdescription;
	}

	public void setGdescription(String gdescription) {
		this.gdescription = gdescription;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage1class() {
		return image1class;
	}

	public void setImage1class(String image1class) {
		this.image1class = image1class;
	}

	public String getImage2class() {
		return image2class;
	}

	public void setImage2class(String image2class) {
		this.image2class = image2class;
	}

	public String getImage3class() {
		return image3class;
	}

	public void setImage3class(String image3class) {
		this.image3class = image3class;
	}

}
