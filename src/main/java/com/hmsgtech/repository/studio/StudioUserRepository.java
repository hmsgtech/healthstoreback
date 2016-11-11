package com.hmsgtech.repository.studio;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.hmsgtech.domain.studio.StudioUser;

public interface StudioUserRepository extends CrudRepository<StudioUser, String> {

	List<StudioUser> findByAgencyIdAndStatus(Integer agencyId, int i);

	Long countByAgencyIdAndStatus(Integer agencyId, int i);

	List<StudioUser> findByAgencyIdAndStatusAndUnionidNot(Integer agencyId, int i, String unionid);

	StudioUser findByPhone(String mobile);

	@Modifying
	@Transactional
	@Query("update StudioUser  set lastLandTime = ?2 where unionid = ?1")
	void updateLastLandTime(String unionid, Date time);

}
