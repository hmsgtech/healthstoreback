package com.hmsgtech.web.model;

import java.util.List;

import com.hmsgtech.domain.common.Banner;
import com.hmsgtech.domain.goods.Activity;
import com.hmsgtech.domain.goods.Goods;
import com.hmsgtech.domain.goods.GoodsType;

/**
 * Created by 晓丰 on 2016/5/18.
 */
public class ShopIndexModel {
	private List<Banner> banners;
	private List<Activity> activities;
	private List<GoodsType> goodsTypes;
	private List<Goods> dayGoods;
	private String dayGoodsImg;

	public List<Banner> getBanners() {
		return banners;
	}

	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public List<GoodsType> getGoodsTypes() {
		return goodsTypes;
	}

	public void setGoodsTypes(List<GoodsType> goodsTypes) {
		this.goodsTypes = goodsTypes;
	}

	public List<Goods> getDayGoods() {
		return dayGoods;
	}

	public void setDayGoods(List<Goods> dayGoods) {
		this.dayGoods = dayGoods;
	}

	public String getDayGoodsImg() {
		return dayGoodsImg;
	}

	public void setDayGoodsImg(String dayGoodsImg) {
		this.dayGoodsImg = dayGoodsImg;
	}

}
