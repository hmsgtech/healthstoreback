package com.hmsgtech.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.store.Store;

public interface StoreRepository extends CrudRepository<Store, Long>, JpaSpecificationExecutor<Store> {

	@Modifying
	@Transactional
	@Query("update Store s set s.rank=?1  where s.id = ?2")
	void updateStoreById(int rank, long storeid);

	List<Store> findStoresByCompanyId(int comid);

	Store findByManage(String unionid);

	// 城市下的店铺ID
	@Query("select s.id from Store s where s.cityId = ?1")
	List<Long> findIdsByCityId(int cityId);

	Store findByNameAndCompanyIdAndStatus(String name, int companyId, int status);

	@Query("select s from Store s where s.status=1 and s.companyId=?4 and s.cityId = ?1 order by (POWER(MOD(ABS(longitude - ?2 ),360),2) + POWER(ABS(latitude - ?3 ),2)) ")
	List<Store> findAgenciesByCityAndLocation(int city, float longitude, float latitude, int companyId, Pageable pageable);

	@Query("select s from Store s where s.status=1 and s.companyId=?4 and name like ?3 order by (POWER(MOD(ABS(longitude - ?1 ),360),2) + POWER(ABS(latitude - ?2 ),2)) ")
	List<Store> findAgenciesByNameAndLocation(float longitude, float latitude, String name, int companyId, Pageable pageable);

	@Query("select s from Store s where s.status=1 and s.companyId=?3 order by (POWER(MOD(ABS(longitude - ?1 ),360),2) + POWER(ABS(latitude - ?2 ),2)) ")
	List<Store> findAgenciesOrderByLocation(float longitude, float latitude, int companyId, Pageable pageable);

	@Query("select s from Store s where s.status=1 and s.companyId=?5 and s.cityId = ?1 and name like ?4 order by (POWER(MOD(ABS(longitude - ?2 ),360),2) + POWER(ABS(latitude - ?3 ),2)) ")
	List<Store> findAgenciesByCityAndNameAndLocation(int city, float longitude, float latitude, String name, int companyId, Pageable pageable);

	@Query("select s from Store s where s.id in ?4 and s.cityId = ?1 order by (POWER(MOD(ABS(longitude - ?2 ),360),2) + POWER(ABS(latitude - ?3 ),2)) ")
	List<Store> findStoresByCityAndLocation(int city, float longitude, float latitude, List<Long> ids, Pageable pageable);

	@Query("select s from Store s where s.id in ?1 order by (POWER(MOD(ABS(longitude - ?2 ),360),2) + POWER(ABS(latitude - ?3 ),2)) ")
	List<Store> findStoresByLocation(List<Long> ids, float longitude, float latitude, Pageable pageable);
}
