package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.common.City;

public interface CityRepository extends CrudRepository<City, Long>{
	List<City> findCityByProvince(int id );
}
