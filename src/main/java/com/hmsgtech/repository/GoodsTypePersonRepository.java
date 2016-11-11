package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.goods.GoodsTypePerson;

public interface GoodsTypePersonRepository extends CrudRepository<GoodsTypePerson, Integer> {
	@Query("SELECT c.personId from GoodsTypePerson c where c.typeId = ?1")
	List<Integer> findPersonIdsByType(Integer type);
}
