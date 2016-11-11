package com.hmsgtech.domain.store;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 *适用商户 
 *
 */
@Entity
public class SuitStores {
	@Id
	private long goodsId;
	private String storesIds;

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public String getStoresIds() {
		return storesIds;
	}

	public void setStoresIds(String storesIds) {
		this.storesIds = storesIds;
	}

}
