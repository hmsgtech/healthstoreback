package com.hmsgtech.web.model;

import java.util.List;

import com.hmsgtech.domain.goods.Goods;

public class GoodsModel {
	private Goods goods;
	
	private List<String> detailImages;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public List<String> getDetailImages() {
		return detailImages;
	}

	public void setDetailImages(List<String> detailImages) {
		this.detailImages = detailImages;
	}
}
