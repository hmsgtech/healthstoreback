package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.goods.ClassifyCommodity;

public interface ClassifyCommodityRepository extends CrudRepository<ClassifyCommodity, Integer> {

	@Query("SELECT g from ClassifyCommodity g where g.id in (select a.commodityId from ClassifyCommodityPerson a where a.personId= ?1)")
	List<ClassifyCommodity> findByClassifyPersonIdOrderBySort(int classifyPersonId);

	List<ClassifyCommodity> findByOrderBySort();
}
