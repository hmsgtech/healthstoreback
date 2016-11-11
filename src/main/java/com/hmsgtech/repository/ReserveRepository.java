package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.user.Reserve;

public interface ReserveRepository extends CrudRepository<Reserve, Integer> {
	List<Reserve> findByUserid(String userid);
}
