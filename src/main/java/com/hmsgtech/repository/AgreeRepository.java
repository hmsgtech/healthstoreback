package com.hmsgtech.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.order.Agree;

public interface AgreeRepository extends CrudRepository<Agree, Long>{
	@Query("select  count(*) from Agree where rankId = ?1 and userId = ?2")
	int getSumByRankId(long rankId,String userId);
	
	@Query("select  count(*) from Agree where rankId = ?1")
	int getSumByRankId(long rankId);
	
	Agree findAgreeByUserIdAndRankId(String userId,long rankId);
}
