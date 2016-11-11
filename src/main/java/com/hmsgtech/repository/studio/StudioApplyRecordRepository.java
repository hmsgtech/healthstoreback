package com.hmsgtech.repository.studio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.studio.StudioApplyRecord;

public interface StudioApplyRecordRepository extends CrudRepository<StudioApplyRecord, String> {

	List<StudioApplyRecord> findByUnionid(String unionid);

}
