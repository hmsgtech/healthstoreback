package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.store.StoreImage;

public interface StoreImageRepository extends CrudRepository<StoreImage, Integer> {
	List<StoreImage> findByStoreIdOrderByType(int storeId);

	StoreImage findByStoreIdAndType(int storeId, Integer type);
}
