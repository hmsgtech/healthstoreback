package com.hmsgtech.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.goods.Goods;

/**
 * Created by 晓丰 on 2016/4/2.
 */
public interface GoodsRepository extends CrudRepository<Goods, Long> {

	/**
	 * 查询与城市无关的商品的ID
	 * 
	 * @return
	 */
	@Query("SELECT g.id from Goods g where g.companyId=2 ")
	List<Long> findByNotCity();

	@Query("SELECT g from Goods g where g.id in ?1 and g.isSell = 1")
	List<Goods> findAllByIds(List<Long> ids, Sort sort);

	@Query("SELECT g from Goods g where g.id in ?1 and g.goodsType = ?2 and g.isSell = 1")
	List<Goods> findAllByIdsAndType(List<Long> ids, int type, Sort sort);

	// 成功后增加商品数量
	@Modifying
	@Transactional
	@Query("update Goods g set g.sellNum = g.sellNum+?1 where g.id = ?2")
	void updateGoodsById(int sellnum, long id);

	@Query("SELECT g from Goods g where g.id = ?1 and g.isSell = 1")
	Goods findGoodsByIdAndIsSell(long goods);

	List<Goods> findByCategoryIdAndIsSell(long categoryId, int isSell);

	@Query("SELECT g from Goods g where g.isSell = 1")
	List<Goods> findByPage(Pageable pageable);

	@Query("SELECT g from Goods g where g.goodsType=?1  and g.isSell = 1")
	List<Goods> findByGoodsType(int type, Pageable pageable);

	@Query("SELECT g from Goods g where g.id in ?1 and g.isSell = 1")
	List<Goods> findByIds(List<Long> ids, Pageable pageable);

	@Query("SELECT g from Goods g where g.goodsType=?1 and g.id in ?2 and g.isSell = 1")
	List<Goods> findByGoodsTypeAndIds(int type, List<Long> ids, Pageable pageable);

	/* 搜索商品 */
	@Query("SELECT g from Goods g where g.name like ?1 and g.isSell = 1")
	List<Goods> findGoodsByNameLikeAndIsSell(String name);

	@Query("SELECT g from Goods g where g.name like ?1 and g.id in ?2 and g.isSell = 1")
	List<Goods> findGoodsByNameLikeAndIds(String name, List<Long> ids);

}
