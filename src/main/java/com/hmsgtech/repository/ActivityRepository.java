package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.goods.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {
	/* 查询首页的活动列表 */
	List<Activity> findTop4ByStatusAndTypeOrderBySort(int status, int type);

	Activity findTopByType(int type);

}
