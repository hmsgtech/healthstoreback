package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.store.GoodsStoreRelationship;

public interface GoodsStoreRelationshipRepository extends CrudRepository<GoodsStoreRelationship, Integer> {

	/* 根据商品查店铺列表 */
	@Query("SELECT g.goodsId from GoodsStoreRelationship g where g.storeId in ?1")
	List<Long> findGoodsIdsByStoreIds(List<Long> storeIds);

	/* 根据店铺查商品列表 */
	@Query("SELECT g.storeId from GoodsStoreRelationship g where g.goodsId in ?1")
	List<Long> findStoreIdsByGoodsIds(List<Long> goodsIds);

	/* 根据商品查店铺列表 */
	@Query("SELECT g.storeId from GoodsStoreRelationship g where g.goodsId = ?1")
	List<Long> findStoreIdsByGoodsId(Long goodsId);
}
