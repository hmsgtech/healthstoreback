package com.hmsgtech.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.user.ShareGoodsRecord;

public interface ShareGoodsRecordRepository extends CrudRepository<ShareGoodsRecord, Long> {
	List<ShareGoodsRecord> findByUseridAndGoodsIdAndShareDate(String userid, long goodsId, Date date);
}
