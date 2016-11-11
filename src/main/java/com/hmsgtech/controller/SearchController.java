package com.hmsgtech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.domain.studioArticle.Article;
import com.hmsgtech.repository.basedao.Criteria;
import com.hmsgtech.repository.basedao.Restrictions;
import com.hmsgtech.repository.studio.ArticleRepository;
import com.hmsgtech.utils.StringsUtil;

/**
 * 模糊搜索
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
	ArticleRepository articleRepository;

	/**
	 * 工作室：搜索文章
	 * 
	 * @param unionid
	 * @param keyword
	 * @param type
	 * @param secondSort
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/articles")
	public @ResponseBody List<Article> searchArticleTitle(@RequestParam("unionid") String unionid, @RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "type") int type, @RequestParam(value = "secondSort") int secondSort, @RequestParam(value = "page") int page,
			@RequestParam(value = "size", required = false) Integer size) {
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
