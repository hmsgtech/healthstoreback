package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.goods.GoodsType;

public interface GoodsTypeRepository extends CrudRepository<GoodsType, Integer> {

	/* 查询首页的4个分类 */
	List<GoodsType> findTop4ByOrderBySort();

	List<GoodsType> findAll(Pageable pageable);

}
