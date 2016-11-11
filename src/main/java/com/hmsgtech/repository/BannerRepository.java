package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.common.Banner;

public interface BannerRepository extends CrudRepository<Banner, Integer> {
	List<Banner> findByOrderBySort();
}
