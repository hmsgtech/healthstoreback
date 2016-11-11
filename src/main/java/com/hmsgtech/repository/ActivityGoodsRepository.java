package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.goods.ActivityGoods;

public interface ActivityGoodsRepository extends CrudRepository<ActivityGoods, Integer> {
	/* 查询某个分类下的商品IDS */
	@Query("select a.goodId from ActivityGoods a where a.activityId= ?1")
	List<Long> findgoodsIdsByActivityId(int id);

}
