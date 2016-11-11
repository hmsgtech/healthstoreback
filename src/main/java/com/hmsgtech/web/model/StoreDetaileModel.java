package com.hmsgtech.web.model;

import java.util.List;

import com.hmsgtech.domain.order.StoreRank;
import com.hmsgtech.domain.store.Store;

public class StoreDetaileModel {
	private Store store;
	private List<StoreRank> ranks;
	private int rankavg;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<StoreRank> getRanks() {
		return ranks;
	}

	public void setRanks(List<StoreRank> ranks) {
		this.ranks = ranks;
	}

	public int getRankavg() {
		return rankavg;
	}

	public void setRankavg(int rankavg) {
		this.rankavg = rankavg;
	}

}
