package com.hmsgtech.service.studio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.hmsgtech.coreapi.WechatService;
import com.hmsgtech.repository.StoreImageRepository;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.repository.studio.ArticleFirstSortRepository;
import com.hmsgtech.repository.studio.ArticlePushRecordRepository;
import com.hmsgtech.repository.studio.ArticleRepository;
import com.hmsgtech.repository.studio.ArticleTempletRepository;
import com.hmsgtech.repository.studio.ArticleUseRecordRepository;
import com.hmsgtech.repository.studio.CustomTagRepository;
import com.hmsgtech.repository.studio.StudioApplyRecordRepository;
import com.hmsgtech.repository.studio.StudioCustomerRepository;
import com.hmsgtech.repository.studio.StudioIdentityRepository;
import com.hmsgtech.repository.studio.StudioUserRepository;
import com.hmsgtech.service.CommonService;
import com.hmsgtech.service.SearchService;
import com.hmsgtech.service.StoreService;
import com.hmsgtech.service.sms.SmsService;
import com.hmsgtech.service.studio.ArticleService;
import com.hmsgtech.service.studio.StudioService;

/**
 * 工作室的基础控制器，提供依赖bean的注入
 * 
 * @author lujq
 *
 */
public class StudioBaseService {
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
	@Autowired
	ArticleFirstSortRepository articleFirstSortRepository;
	@Autowired
	StringRedisTemplate stringTemplate;

	@Autowired
	SearchService searchService;
	@Autowired
	CustomTagRepository customTagRepository;
	@Autowired
	StoreImageRepository storeImageRepository;
	@Autowired
	StudioApplyRecordRepository studioApplyRecordRepository;
	@Autowired
	StudioIdentityRepository studioIdentityRepository;

}
