package com.hmsgtech.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.constants.StudioAppUserStatus;
import com.hmsgtech.constants.YesNoStatus;
import com.hmsgtech.domain.store.Store;
import com.hmsgtech.domain.studio.StudioUser;
import com.hmsgtech.domain.studioArticle.Article;
import com.hmsgtech.domain.studioArticle.ArticleTemplet;
import com.hmsgtech.domain.studioArticle.ArticleUseRecord;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.repository.studio.ArticleRepository;
import com.hmsgtech.repository.studio.ArticleTempletRepository;
import com.hmsgtech.repository.studio.ArticleUseRecordRepository;
import com.hmsgtech.repository.studio.StudioUserRepository;
import com.hmsgtech.utils.JsonUtil;
import com.hmsgtech.utils.StringsUtil;

/**
 * 为H5ajax请求提供
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/h5request")
public class JsonpController {
	@Autowired
	StudioUserRepository studioUserRepository;
	@Autowired
	ArticleUseRecordRepository articleUseRecordRepository;
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	ArticleTempletRepository articleTempletRepository;
	@Autowired
	StoreRepository storeRepository;

	@RequestMapping("/getTechInfo")
	public void getTechInfo(HttpServletResponse response,@RequestParam("unionid") String unionid, @RequestParam(value = "callback", required = true) String callback) throws IOException {
		StudioUser studioUser = studioUserRepository.findOne(unionid);
		studioUser.setAgencyName(storeRepository.findOne((long) studioUser.getAgencyId()).getName());
		setReponse(response, callback, studioUser);
	}

	// 查询文章详情|APP中使用：文章ID
	@RequestMapping("/article")
	public void findArticle(HttpServletResponse response, @RequestParam(value = "callback", required = true) String callback, @RequestParam("id") int id) throws IOException {
		Article article = articleRepository.findOne(id);
		if (article != null) {
			// 设置发布文章的机构和技师
			article.setAgencyName(storeRepository.findOne((long) article.getStoreId()).getName());
			if (StringsUtil.isNotEmpty(article.getStudioUser())) {
				StudioUser studioUser = studioUserRepository.findOne(article.getStudioUser());
				article.setTechName(studioUser.getName());
			}
		}
		setReponse(response, callback, article);
	}

	// 查询文章详情|分享到微信的页面：场景ID
	@RequestMapping("/articleShare")
	public void findArticleByShare(HttpServletResponse response, @RequestParam(value = "callback", required = true) String callback, @RequestParam("id") int id)
			throws IOException {
		ArticleUseRecord articleUseRecord = articleUseRecordRepository.findOne(id);
		Article article;
		if (articleUseRecord.getArticleType() >= 3) {
			article = new Article();
			article.setTitle(articleUseRecord.getArticleTitle());
			article.setContent(articleTempletRepository.findOne(articleUseRecord.getArticleId()).getContent().replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;"));
		} else {
			article = articleRepository.findOne(articleUseRecord.getArticleId());
		}
		if (article != null) {
			// 设置分享技师的信息
			StudioUser studioUser = studioUserRepository.findOne(articleUseRecord.getUnionid());
			article.setTechName(studioUser.getName());
			article.setTechAvatar(studioUser.getSnapshot());
			article.setTechDescription(studioUser.getDescription());
		}
		setReponse(response, callback, article);
	}

	private void setReponse(HttpServletResponse response, String callback, Object data) throws IOException {
		response.getWriter().write(callback + "(" + JsonUtil.toJson(data) + ")");
		response.getWriter().flush();
		response.getWriter().close();
	}
}
