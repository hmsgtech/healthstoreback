package com.hmsgtech;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hmsgtech.controller.IndexController;
import com.hmsgtech.domain.studioArticle.ArticleUseRecord;
import com.hmsgtech.domain.studioCustom.ArticlePushRecord;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.repository.studio.ArticlePushRecordRepository;
import com.hmsgtech.repository.studio.ArticleRepository;
import com.hmsgtech.repository.studio.ArticleTempletRepository;
import com.hmsgtech.repository.studio.ArticleUseRecordRepository;
import com.hmsgtech.repository.studio.StudioCustomerRepository;
import com.hmsgtech.repository.studio.StudioUserRepository;
import com.hmsgtech.service.sms.MenWangSMSService;
import com.hmsgtech.utils.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HealthstorebackApplication.class)
@WebAppConfiguration
public class DaoTest {
	@Autowired
	ArticlePushRecordRepository articlePushRecordRepository;
	@Autowired
	StudioCustomerRepository studioCustomerRepository;
	@Autowired
	StudioUserRepository studioUserRepository;
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	ArticleTempletRepository articleTempletRepository;
	@Autowired
	ArticleUseRecordRepository articleUseRecordRepository;
	@Autowired
	StoreRepository storeRepository;

	Log logger = LogFactory.getLog(DaoTest.class);

	@Before
	public void init() throws IOException {
	}

	@Test
	public void articlePushRecordTest() throws IOException {
//		Specification<ArticlePushRecord> spec = new Specification<ArticlePushRecord>() {
//			@Override
//			public Predicate toPredicate(Root<ArticlePushRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				// 左连接
//				Join<ArticlePushRecord, ArticleUseRecord> depJoin = root.join("articleUseRecord", JoinType.LEFT);
//				Predicate p = cb.equal(depJoin.get("id").as(Integer.class),88);
//				return p;
//			}
//		};
//		List<ArticlePushRecord> records = (List<ArticlePushRecord>) articlePushRecordRepository.findAll(spec);
//		// System.out.println(JsonUtil.gsonToString(records));
//		System.out.println(JsonUtil.toJson(records));
//		System.out.println(JsonUtil.toJson(records.get(0).getArticleUseRecord()));
	}
	//
	// @Test
	// public void indexDayGoods() throws IOException {
	// logger.info(index.indexDayGoods());
	// }
	//
	// @Test
	// public void indexGoodsTypeList() throws IOException {
	// logger.info(index.indexGoodsTypeList());
	// }

}
