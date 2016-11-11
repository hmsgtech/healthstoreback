package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.goods.ClassifyCommodityGood;

public interface ClassifyCommodityGoodRepository extends CrudRepository<ClassifyCommodityGood, Integer> {
	// 查询对应的goodsids
	@Query("SELECT c.goodId from ClassifyCommodityGood c where c.commodityId in ?1")
	List<Long> findGoodsIdsByCommodityIds(List<Integer> classifyCommodities);
}
