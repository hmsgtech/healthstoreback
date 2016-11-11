package com.hmsgtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.hmsgtech.repository.BuyOrderRepository;
import com.hmsgtech.repository.GoodsRepository;
import com.hmsgtech.repository.GoodsStoreRelationshipRepository;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.utils.StringsUtil;

@Service
public class GoodsService {

	@Autowired
	BuyOrderRepository buyOrderRepository;
	@Autowired
	GoodsRepository goodsRepository;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	GoodsStoreRelationshipRepository goodsStoreRelationshipRepository;

	public List<Long> getGoodsIdsByCity(int city) {
		// 取 城市下的店铺商品+与城市无关商品的合集
		List<Long> cityIds1 = goodsRepository.findByNotCity();
		List<Long> storeids = storeRepository.findIdsByCityId(city);
		if (!storeids.isEmpty()) {
			List<Long> cityIds2 = goodsStoreRelationshipRepository.findGoodsIdsByStoreIds(storeids);
			cityIds1.addAll(cityIds2);
		}
		StringsUtil.removeDuplicate(cityIds1);
		return cityIds1;
	}

	public Sort getSort(int type) {
		Order order = null;
		switch (type) {
		case 0:
			break;
		case 1:
			order = new Order(Direction.DESC, "sellNum");
			break;
		case 2:
			order = new Order(Direction.DESC, "goodsRank");
			break;
		case 3:
			order = new Order("price");
			break;
		default:
			break;
		}
		if (order != null) {
			return new Sort(order);
		}
		return null;
	}
}
