package com.hmsgtech.repository.studio;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.studioCustom.ArticlePushRecord;

public interface ArticlePushRecordRepository extends CrudRepository<ArticlePushRecord, Long>, JpaSpecificationExecutor<ArticlePushRecord> {
	// 查询某个客户的最近消息
	ArticlePushRecord findTopByStudioCustomerOrderByIdDesc(Integer customer);

	// 查询某个客户的消息历史
	List<ArticlePushRecord> findByStudioCustomerOrderByIdDesc(Integer customer, Pageable pageable);

	// 查询某个客户的上次消息历史
	ArticlePushRecord findTopByStudioCustomerAndUseRecordOrderByIdDesc(Integer customer, int useRecordId);

}
