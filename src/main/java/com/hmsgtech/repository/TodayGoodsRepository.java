package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.TodayGoods;

public interface TodayGoodsRepository extends CrudRepository<TodayGoods, Long> {
	@Query("SELECT t from TodayGoods t order by order_by ")
	List<TodayGoods> findAll();

	@Query("SELECT t.goodsId from TodayGoods t order by order_by ")
	List<Long> findAllGoodsId();
}
