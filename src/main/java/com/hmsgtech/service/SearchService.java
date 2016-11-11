package com.hmsgtech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.domain.studioArticle.Article;
import com.hmsgtech.domain.studioCustom.ArticlePushRecord;
import com.hmsgtech.domain.studioCustom.StudioCustomer;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.repository.basedao.Criteria;
import com.hmsgtech.repository.basedao.Restrictions;
import com.hmsgtech.repository.studio.ArticlePushRecordRepository;
import com.hmsgtech.repository.studio.ArticleRepository;
import com.hmsgtech.repository.studio.ArticleUseRecordRepository;
import com.hmsgtech.repository.studio.StudioCustomerRepository;
import com.hmsgtech.repository.studio.StudioIdentityRepository;
import com.hmsgtech.repository.studio.StudioUserRepository;
import com.hmsgtech.utils.StringsUtil;

@Service
public class SearchService {
	@Autowired
	StudioUserRepository studioUserRepository;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	StudioIdentityRepository studioIdentityRepository;
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	ArticleUseRecordRepository articleUseRecordRepository;
	@Autowired
	StudioCustomerRepository studioCustomerRepository;
	@Autowired
	ArticlePushRecordRepository articlePushRecordRepository;

	// 搜索机构

	// 工作室：搜索客户
	public List<StudioCustomer> findCustomer(String keyword, String unionid, Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "updateTime"));
		List<StudioCustomer> customers = new ArrayList<StudioCustomer>();
		if (StringsUtil.isNotEmpty(keyword)) {
			customers = studioCustomerRepository.findByTechidAndNameLike(unionid, "%" + keyword + "%", pageable);
		} else {
			customers = studioCustomerRepository.findByTechid(unionid, pageable);
		}
		for (StudioCustomer studioCustomer : customers) {
			// 设置推送的文章标题:查推送表最早的一条
			ArticlePushRecord articlePushRecord = articlePushRecordRepository.findTopByStudioCustomerOrderByIdDesc(studioCustomer.getId());
			studioCustomer.setArticleTitle(articleUseRecordRepository.findOne(articlePushRecord.getUseRecord()).getArticleTitle());
		}
		return customers;
	}

	// 工作室：搜索文章(诊前须知/诊后提醒)
	public List<Article> searchArticleTitle(String unionid, String keyword, int type, int secondSort, int page, Integer size) {
		// 分页属性
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "useCount"));
		// 查询条件:
		Criteria<Article> where = new Criteria<Article>();
		if (StringsUtil.isNotEmpty(keyword)) {
			where.add(Restrictions.like("title", "%" + keyword + "%", true));
		}
		if (secondSort > 0) {
			where.add(Restrictions.eq("secondSort", secondSort, true));
		}
		if (type > 0) {
			where.add(Restrictions.eq("type", type, true));
		}
		// TODO 私有的不能查
		List<Article> articles = articleRepository.findAll(where, pageable).getContent();
		// 只需返回文章标题、Id、type
		for (Article article : articles) {
			article.setContent("");
		}
		return articles;
	}
}
