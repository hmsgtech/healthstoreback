package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.order.GoodsRank;

public interface GoodsRankRepository extends CrudRepository<GoodsRank, Long> {
	List<GoodsRank> findGoodsRankByGoodsId(long goodsId);

	// 获取好评数
	@Query("SELECT g from GoodsRank g where g.goodsId=?1 and g.goodsRank in(?2,?3)")
	List<GoodsRank> findGoodsRankByGoodsIdAndGoodsRank(long goodsId,
			Integer status1, Integer status2);

	
	@Query("SELECT count(g) from GoodsRank g where g.goodsId=?1 and g.goodsRank in(?2,?3)")
	int getNiceGoodsRanksCount(long id, int status1, int status2);
	
	
	
}
