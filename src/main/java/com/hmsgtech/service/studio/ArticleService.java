package com.hmsgtech.service.studio;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.hmsgtech.coreapi.HealthStoreException;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.coreapi.WechatService;
import com.hmsgtech.domain.studioArticle.Article;
import com.hmsgtech.domain.studioArticle.ArticleTemplet;
import com.hmsgtech.domain.studioArticle.ArticleUseRecord;
import com.hmsgtech.domain.studioCustom.ArticlePushRecord;
import com.hmsgtech.domain.studioCustom.StudioCustomer;
import com.hmsgtech.repository.redis.ArticleModel;
import com.hmsgtech.repository.redis.RedisConfig;
import com.hmsgtech.utils.JsonUtil;
import com.hmsgtech.utils.StringsUtil;

@Service
public class ArticleService extends StudioBaseService {
	@Value("${article.templet.pic}")
	String templetPic;
	@Autowired
	WechatService wechatService;

	public void addArticleTemplet(String unionid, int type, String title, String content) throws Exception {
		ArticleTemplet articleTemplet = new ArticleTemplet(unionid, title, content, type, 0);
		articleTempletRepository.save(articleTemplet);
	}

	public ArticleUseRecord getArticleQrcode(String unionid, int id) throws IOException {
		// 查询历史记录的二维码是否存在或过期
		ArticleUseRecord articleUseRecord = articleUseRecordRepository.findByUnionidAndArticleIdAndArticleType(unionid, id, 1);
		if (articleUseRecord == null) {
			Article article = articleRepository.findOne(id);
			articleUseRecord = new ArticleUseRecord(unionid, article.getId(), article.getTitle(), article.getType());
			articleUseRecord = articleUseRecordRepository.save(articleUseRecord);
		}
		try {
			return updateQrcode(articleUseRecord, articleFirstSortRepository.findOne(articleRepository.findOne(id).getFirstSort()).getImg());
		} catch (Exception e) {
			throw new HealthStoreException(ReturnCode.REFRESH_QRCODE_FAIL);
		}
	}

	public ArticleUseRecord getArticleTempletQrcode(String unionid, int action, Integer id, String title, String content) throws IOException {
		ArticleUseRecord articleUseRecord;
		if (action == 1) {
			// 1，添加模板后直接发送 id为空 title和content有值
			ArticleTemplet articleTemplet = new ArticleTemplet(unionid, title, content, 3, 1);
			articleTemplet = articleTempletRepository.save(articleTemplet);
			articleUseRecord = new ArticleUseRecord(unionid, articleTemplet.getId(), articleTemplet.getTitle(), articleTemplet.getType());
			articleUseRecord = articleUseRecordRepository.save(articleUseRecord);
		} else {
			articleUseRecord = articleUseRecordRepository.findByUnionidAndArticleIdAndArticleType(unionid, id, 3);
			// 3，选择使用历史里的模板（非文章）id有值 title和content为空
			if (articleUseRecord == null) {
				ArticleTemplet articleTemplet = articleTempletRepository.findOne(id);
				if (StringsUtil.isNotEmpty(title)) {
					articleTemplet.setTitle(title);
				}
				if (StringsUtil.isNotEmpty(content)) {
					articleTemplet.setContent(content);
				}
				articleTemplet.setStatus(1);
				articleTemplet = articleTempletRepository.save(articleTemplet);
				articleUseRecord = new ArticleUseRecord(unionid, articleTemplet.getId(), articleTemplet.getTitle(), articleTemplet.getType());
				articleUseRecord = articleUseRecordRepository.save(articleUseRecord);
			}
		}

		try {
			return updateQrcode(articleUseRecord, templetPic);
		} catch (Exception e) {
			throw new HealthStoreException(ReturnCode.REFRESH_QRCODE_FAIL);
		}
	}

	/**
	 * 
	 * 刷新历史记录
	 * 
	 * @param articleUseRecord
	 * @throws IOException
	 */
	private ArticleUseRecord updateQrcode(ArticleUseRecord articleUseRecord, String imgUrl) throws IOException {
		if (articleUseRecord.getExpireTime() == null || new Date().after(articleUseRecord.getExpireTime())
				|| !stringTemplate.hasKey(RedisConfig.QRCODE_KEY + "_" + articleUseRecord.getTicket())) {
			// 生成新的二维码
			Map<String, Object> qrmap = wechatService.getQrScene((long) (100000 + articleUseRecord.getId()));
			articleUseRecord.setTicket((String) qrmap.get("ticket"));
			articleUseRecord.setExpireTime(new Date((long) qrmap.get("expire_time")));
			articleUseRecord.setTicketUrl((String) qrmap.get("url"));
			// 保存至REDIS
			ArticleModel articleModel = new ArticleModel();
			articleModel.setTitle(articleUseRecord.getArticleTitle());
			articleModel.setDescription("");
			articleModel.setPicUrl(imgUrl);
			articleModel.setUrl(wechatService.getWxArticleUrl(articleUseRecord.getId()));
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "news");
			map.put("article_1", JsonUtil.toJson(articleModel));
			map.put("source", String.valueOf(articleUseRecord.getId()));
			stringTemplate.opsForHash().putAll(RedisConfig.QRCODE_KEY + "_" + articleUseRecord.getTicket(), map);
			stringTemplate.expire(RedisConfig.QRCODE_KEY + "_" + articleUseRecord.getTicket(), 28, TimeUnit.DAYS);
		}
		articleUseRecord.setUpdateTime(new Date());
		return articleUseRecordRepository.save(articleUseRecord);
	}

	// 给客户添加一条推送记录:1小时之内没有推送则新加一条，更新时间
	public void sendRemind(String unionid, int articleId, int customerid) throws IOException {
		StudioCustomer customer = studioCustomerRepository.findOne(customerid);
		ArticleUseRecord articleUseRecord = articleUseRecordRepository.findByUnionidAndArticleIdAndArticleType(unionid, articleId, 2);
		if (articleUseRecord == null) {
			Article article = articleRepository.findOne(articleId);
			articleUseRecord = new ArticleUseRecord(unionid, article.getId(), article.getTitle(), article.getType());
		}
		addPushRecordAndSendRemind(articleUseRecord, customer);
	}

	public void sendTempletRemind(String unionid, int action, int customerid, int templetId, String title, String content) throws IOException {
		StudioCustomer customer = studioCustomerRepository.findOne(customerid);
		ArticleUseRecord articleUseRecord;
		if (action == 1) {
			// 1，添加模板后直接发送 id为空 title和content有值
			ArticleTemplet articleTemplet = new ArticleTemplet(unionid, title, content, 4, 1);
			articleTemplet = articleTempletRepository.save(articleTemplet);
			articleUseRecord = new ArticleUseRecord(unionid, articleTemplet.getId(), articleTemplet.getTitle(), articleTemplet.getType());
			articleUseRecord = articleUseRecordRepository.save(articleUseRecord);
		} else {
			// 3，选择使用历史里的模板（非文章）id有值 title和content为空
			articleUseRecord = articleUseRecordRepository.findByUnionidAndArticleIdAndArticleType(unionid, templetId, 4);
			if (articleUseRecord == null) {
				ArticleTemplet articleTemplet = articleTempletRepository.findOne(templetId);
				if (StringsUtil.isNotEmpty(title)) {
					articleTemplet.setTitle(title);
				}
				if (StringsUtil.isNotEmpty(content)) {
					articleTemplet.setContent(content);
				}
				articleTemplet.setStatus(1);
				articleTemplet = articleTempletRepository.save(articleTemplet);
				articleUseRecord = new ArticleUseRecord(unionid, articleTemplet.getId(), articleTemplet.getTitle(), articleTemplet.getType());
				articleUseRecord = articleUseRecordRepository.save(articleUseRecord);
			}
		}
		addPushRecordAndSendRemind(articleUseRecord, customer);
	}

	public void addPushRecord(ArticleUseRecord articleUseRecord, StudioCustomer studioCustomer) throws IOException {
		// 给客户添加一条推送记录:1小时之内没有推送则新加一条，更新时间
		ArticlePushRecord articlePushRecord;
		if (studioCustomer.getId() != null) {
			articlePushRecord = articlePushRecordRepository.findTopByStudioCustomerAndUseRecordOrderByIdDesc(studioCustomer.getId(), articleUseRecord.getId());
			if (articlePushRecord != null && System.currentTimeMillis() - articlePushRecord.getCreateTime().getTime() < 1000 * 60 * 60) {
				throw new HealthStoreException(ReturnCode.SEND_REMIND_REPEAT);
			}
		}
		studioCustomer.setUpdateTime(new Date());
		studioCustomer = studioCustomerRepository.save(studioCustomer);

		articleUseRecord.setUpdateTime(new Date());
		articleUseRecord = articleUseRecordRepository.save(articleUseRecord);

		articlePushRecord = new ArticlePushRecord(studioCustomer.getId(), articleUseRecord.getId(), articleUseRecord.getArticleType());
		articlePushRecordRepository.save(articlePushRecord);
	}

	public void addPushRecordAndSendRemind(ArticleUseRecord articleUseRecord, StudioCustomer studioCustomer) throws IOException {
		ArticlePushRecord articlePushRecord;
		if (studioCustomer.getId() != null) {
			articlePushRecord = articlePushRecordRepository.findTopByStudioCustomerAndUseRecordOrderByIdDesc(studioCustomer.getId(), articleUseRecord.getId());
			if (articlePushRecord != null && System.currentTimeMillis() - articlePushRecord.getCreateTime().getTime() < 1000 * 60 * 60) {
				throw new HealthStoreException(ReturnCode.SEND_REMIND_REPEAT);
			}
		}

		articleUseRecord.setUpdateTime(new Date());
		articleUseRecord = articleUseRecordRepository.save(articleUseRecord);

		// 推送
		JsonObject jsonObject= wechatService.sendTreatmentRemind(studioCustomer.getCustomer(), articleUseRecord.getId(), studioCustomer.getNickname(),
				storeRepository.findOne((long) studioUserRepository.findOne(articleUseRecord.getUnionid()).getAgencyId()).getName(), articleUseRecord.getArticleTitle());
		// 更新记录
		studioCustomer.setNickname(jsonObject.get("nickname").getAsString());
		studioCustomer.setHeadimgurl(jsonObject.get("headimgurl").getAsString());
		studioCustomer.setUpdateTime(new Date());
		studioCustomer = studioCustomerRepository.save(studioCustomer);

		articlePushRecord = new ArticlePushRecord(studioCustomer.getId(), articleUseRecord.getId(), articleUseRecord.getArticleType());
		articlePushRecordRepository.save(articlePushRecord);

	}

}
