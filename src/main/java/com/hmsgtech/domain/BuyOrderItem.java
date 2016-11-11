package com.hmsgtech.domain;

/**
 * Created by 晓丰 on 2016/5/10. 购物车商品信息
 */
public class BuyOrderItem {

	private int goodsId;

	private int num;

	private int pirce;

	private int storeId;

	public long getGoodsId() {
		return goodsId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPirce() {
		return pirce;
	}

	public void setPirce(int pirce) {
		this.pirce = pirce;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

}
