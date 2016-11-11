package com.hmsgtech.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.store.Company;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

	@Query("SELECT c from Company c where c.id in(?1)")
	List<Company> findCompanyByIds(Set<Integer> companyIds);

	@Query("SELECT min(id) from Company")
	int findminId();

}
