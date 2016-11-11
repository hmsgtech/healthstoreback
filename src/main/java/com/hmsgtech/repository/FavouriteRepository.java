package com.hmsgtech.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.Favourite;

public interface FavouriteRepository extends CrudRepository<Favourite, Long> {
	@Query("SELECT f from Favourite f where f.userid = ?1 and f.goodsId=?2")
	List<Favourite> findFavouriteByUseridAndGoodsId(String userid, long goodsId);

	@Modifying
	@Transactional
	@Query("delete from Favourite where userid = ?1 and goodsId=?2")
	void deleteFavouriteByUseridAndGoodsId(String userid, long goodsId);

	List<Favourite> findFavouriteListByUserid(String userid);

	@Query("SELECT f.goodsId from Favourite f where f.userid = ?1")
	List<Long> findGoodsIdsByUserid(String userid);

	@Modifying
	@Transactional
	@Query("delete from Favourite where userid = ?1 and goodsId in(?2)")
	void deleteFavouriteByUseridAndGoodsIds(String userid, List<Long> idsList);

}
