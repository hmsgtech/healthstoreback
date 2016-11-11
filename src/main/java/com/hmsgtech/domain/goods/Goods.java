package com.hmsgtech.domain.goods;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by 晓丰 on 2016/5/10. 商品
 */
@Entity
public class Goods {
	@Id
	@GeneratedValue
	private long id;

	private String name;

	private String description;

	private int price;

	private int originalPrice;

	private Date startDate;

	private Date endDate;

	private int sellNum;

	private boolean isDoctorRecommend;

	private String image; // 商品缩略图

	private String descImages; // 商品描述图片

	private String goodsContent; // 商品内容图片

	private String detailImages; // 商品详情多张图片

	private String buyNotice; // 购买须知图片

	private String tag;

	private String color;

	private String gift;

	private int isSell; // 1上架，0下架

	@Column(name = "good_type", columnDefinition = "int Default 1 COMMENT '商品类型'")
	private int goodsType;

	@Column(columnDefinition = "int Default 1 COMMENT '总店ID:1美年2爱基因'", nullable = false)
	private int companyId;

	@Column(columnDefinition = "int Default 1 COMMENT '商品分类:1精选套餐2肿瘤检测'", nullable = false)
	private int categoryId;

	@Column(columnDefinition = "int Default 0 COMMENT '商品评分:根据评价平均数计算'")
	private int goodsRank;

	@Transient
	private int companyType;

	// 剩余小时数
	@Transient
	private int days;

	@Transient
	private int time;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(int originalPrice) {
		this.originalPrice = originalPrice;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getSellNum() {
		return sellNum;
	}

	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}

	public boolean isDoctorRecommend() {
		return isDoctorRecommend;
	}

	public void setDoctorRecommend(boolean doctorRecommend) {
		isDoctorRecommend = doctorRecommend;
	}

	public String getDescImages() {
		return descImages;
	}

	public void setDescImages(String descImages) {
		this.descImages = descImages;
	}

	public String getDetailImages() {
		return detailImages;
	}

	public void setDetailImages(String detailImages) {
		this.detailImages = detailImages;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getDays() {
		if (endDate == null) {
			return -1;
		}
		int day = (int) ((endDate.getTime() - System.currentTimeMillis()) / (1000 * 60));
		return day <= 0 ? 0 : day;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getGoodsRank() {
		return goodsRank;
	}

	public void setGoodsRank(int goodsRank) {
		this.goodsRank = goodsRank;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getGoodsContent() {
		return goodsContent;
	}

	public void setGoodsContent(String goodsContent) {
		this.goodsContent = goodsContent;
	}

	public String getBuyNotice() {
		return buyNotice;
	}

	public void setBuyNotice(String buyNotice) {
		this.buyNotice = buyNotice;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public int getIsSell() {
		return isSell;
	}

	public void setIsSell(int isSell) {
		this.isSell = isSell;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getTime() {
		if (startDate == null || endDate == null) {
			return 0;
		}

		if (startDate.getTime() - System.currentTimeMillis() < 0 && endDate.getTime() - System.currentTimeMillis() > 0) {
			return 1;
		}

		return 0;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getCompanyType() {
		return companyType;
	}

	public void setCompanyType(int companyType) {
		this.companyType = companyType;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

}
