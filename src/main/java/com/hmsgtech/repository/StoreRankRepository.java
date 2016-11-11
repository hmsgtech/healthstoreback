package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.order.StoreRank;

public interface StoreRankRepository extends CrudRepository<StoreRank, Long> {
	
	List<StoreRank> findStoreRankByStoreId(long storeid);
	
	@Query("select sum(rank)/count(rank) from StoreRank where storeId = ?1")
	int getSumBysStoreId(long storeid);
}
