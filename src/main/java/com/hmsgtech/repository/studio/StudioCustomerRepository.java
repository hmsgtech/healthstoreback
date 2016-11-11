package com.hmsgtech.repository.studio;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.studioCustom.StudioCustomer;

public interface StudioCustomerRepository extends CrudRepository<StudioCustomer, Integer> {
	List<StudioCustomer> findByTechid(String unionid, Pageable pageable);

	StudioCustomer findByTechidAndCustomer(String unionid, String openid);

	List<StudioCustomer> findByTechidAndNameLike(String unionid, String name, Pageable pageable);
}
