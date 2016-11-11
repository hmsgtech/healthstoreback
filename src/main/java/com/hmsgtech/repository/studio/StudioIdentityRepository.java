package com.hmsgtech.repository.studio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.studio.StudioIdentity;

public interface StudioIdentityRepository extends CrudRepository<StudioIdentity, Integer> {
	/* 查询所有身份 */
	List<StudioIdentity> findByStatus(int status);

}
