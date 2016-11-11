package com.hmsgtech.web.model;

import java.io.Serializable;
import java.util.List;

import com.hmsgtech.domain.order.GoodsRank;
import com.hmsgtech.domain.order.StoreRank;

public class RankModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private StoreRank storeRank;
	private List<GoodsRank> goodsRanks;

	public RankModel() {
	}

	public StoreRank getStoreRank() {
		return storeRank;
	}

	public void setStoreRank(StoreRank storeRank) {
		this.storeRank = storeRank;
	}

	public List<GoodsRank> getGoodsRanks() {
		return goodsRanks;
	}

	public void setGoodsRanks(List<GoodsRank> goodsRanks) {
		this.goodsRanks = goodsRanks;
	}

}
