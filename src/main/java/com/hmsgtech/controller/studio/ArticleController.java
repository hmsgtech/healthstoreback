package com.hmsgtech.controller.studio;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.coreapi.ApiResponse;
import com.hmsgtech.coreapi.HealthStoreException;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.domain.studioArticle.ArticleTemplet;
import com.hmsgtech.domain.studioArticle.ArticleUseRecord;
import com.hmsgtech.domain.studioCustom.ArticlePushRecord;
import com.hmsgtech.utils.StringsUtil;

/**
 * 文章查询，二维码\推送
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/studioArticle")
public class ArticleController extends StudioBaseController {

	/**
	 * 查询我的模板
	 * 
	 * @param unionid
	 * @param type
	 * @return
	 */
	@RequestMapping("/articleTempletes")
	public @ResponseBody List<ArticleTemplet> findArticleTempletes(@RequestParam("unionid") String unionid, @RequestParam("type") int type) {
		return articleTempletRepository.findByUnionidAndTypeAndStatus(unionid, type, 0);
	}

	/**
	 * 文章使用历史：技师ID，类型为1=13，2=24
	 * 
	 * @param unionid
	 * @param type
	 * @return
	 */
	@RequestMapping("/wxpushHistory")
	public @ResponseBody List<ArticlePushRecord> findArticlePushRecords(@RequestParam(value = "customer") int customerid, @RequestParam("page") Integer page,
			@RequestParam("size") Integer size) {
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		List<ArticlePushRecord> articlePushRecords = articlePushRecordRepository.findByStudioCustomerOrderByIdDesc(customerid, pageable);
		for (ArticlePushRecord articlePushRecord : articlePushRecords) {
			ArticleUseRecord articleUseRecord = articleUseRecordRepository.findOne(articlePushRecord.getUseRecord());
			articlePushRecord.setType(articleUseRecord.getArticleType());
			articlePushRecord.setTitle(articleUseRecord.getArticleTitle());
		}
		return articlePushRecords;
	}

	/**
	 * 文章使用历史：技师ID，类型为1=13，2=24
	 * 
	 * @param unionid
	 * @param type
	 * @return
	 */
	@RequestMapping("/articleHistory")
	public @ResponseBody List<ArticleUseRecord> findArticleUseRecord(@RequestParam("unionid") String unionid, @RequestParam(value = "type") String type) {
		Pageable pageable = new PageRequest(0, 20, new Sort(Direction.DESC, "updateTime"));
		List<ArticleUseRecord> articleUseRecords = articleUseRecordRepository.findByUnionidAndArticleType(unionid, StringsUtil.strToList(type), pageable);
		for (ArticleUseRecord articleUseRecord : articleUseRecords) {
			if (articleUseRecord.getArticleType() == 3 || articleUseRecord.getArticleType() == 4) {
				articleUseRecord.setContent(articleTempletRepository.findOne(articleUseRecord.getArticleId()).getContent());
			} else {
				articleUseRecord.setContent("");
			}
		}
		return articleUseRecords;
	}

	/**
	 * 添加一个模板
	 * 
	 * @param unionid
	 * @param type
	 * @param title
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addArticleTemplet", method = RequestMethod.POST)
	public @ResponseBody ApiResponse addArticleTemplet(@RequestParam(value = "unionid") String unionid, @RequestParam("type") int type, @RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content) throws Exception {
		ArticleTemplet articleTemplet = new ArticleTemplet(unionid, title, content, type, 0);
		articleTempletRepository.save(articleTemplet);
		return ApiResponse.success();
	}

	/**
	 * 更新一个模板
	 * 
	 * @param id
	 * @param title
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value = "/updateArticleTemplet", method = RequestMethod.POST)
	public @ResponseBody ApiResponse updateArticleTemplet(@RequestParam("id") int id, @RequestParam(value = "title") String title, @RequestParam(value = "content") String content)
			throws Exception {
		ArticleTemplet articleTemplet = articleTempletRepository.findOne(id);
		if (articleTemplet.getStatus() == 1 || content.length() < 2 || content.length() < 2) {
			throw new HealthStoreException(ReturnCode.UPDATE_TEMPLET_ERROR);
		}
		articleTemplet.setContent(content);
		articleTemplet.setTitle(title);
		articleTemplet.setUpdatetime(new Date());
		articleTempletRepository.save(articleTemplet);
		return ApiResponse.success();
	}

	/**
	 * 文章ID+技师ID
	 * 
	 * @param id
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/qrcode", method = RequestMethod.POST)
	public @ResponseBody ApiResponse getArticleQrcode(@RequestParam("unionid") String unionid, @RequestParam("id") int id) throws IOException {
		try {
			return ApiResponse.success(articleService.getArticleQrcode(unionid, id));
		} catch (HealthStoreException e) {
			return ApiResponse.fail(e.getErrorCode());
		} catch (Exception e) {
			return ApiResponse.defaultFail();
		}
	}

	/**
	 * 获取模板二维码
	 * 
	 * @param unionid
	 * @param action
	 * @param id
	 * @param title
	 * @param content
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/qrcodeBytemplet", method = RequestMethod.POST)
	public @ResponseBody ApiResponse getQrcodeBytemplet(@RequestParam("unionid") String unionid, @RequestParam("action") int action, @RequestParam("id") Integer id,
			@RequestParam("title") String title, @RequestParam("content") String content) throws IOException {
		try {
			return ApiResponse.success(articleService.getArticleTempletQrcode(unionid, action, id, title, content));
		} catch (HealthStoreException e) {
			return ApiResponse.fail(e.getErrorCode());
		} catch (Exception e) {
			return ApiResponse.defaultFail();
		}
	}

	// 向客户推送消息：1小时之内未推送过改消息
	// 推送诊后提醒：推送文章
	@RequestMapping(value = "/sendRemind", method = RequestMethod.POST)
	public @ResponseBody ApiResponse sendArticle(@RequestParam("unionid") String unionid, @RequestParam("customer") int customerid, @RequestParam("articleId") int articleId)
			throws IOException {
		try {
			articleService.sendRemind(unionid, articleId, customerid);
		} catch (HealthStoreException e) {
			return ApiResponse.fail(e.getErrorCode());
		} catch (Exception e) {
			return ApiResponse.defaultFail();
		}
		return ApiResponse.success();
	}

	// 立即使用模板：推送模板
	@RequestMapping(value = "/sendRemindBytemplet", method = RequestMethod.POST)
	public @ResponseBody ApiResponse sendTempletRemind(@RequestParam("unionid") String unionid, @RequestParam("customer") int customerid, @RequestParam("action") int action,
			@RequestParam("id") int templetId, @RequestParam(value = "title") String title, @RequestParam(value = "content") String content) throws IOException {
		try {
			articleService.sendTempletRemind(unionid, action, customerid, templetId, title, content);
		} catch (HealthStoreException e) {
			return ApiResponse.fail(e.getErrorCode());
		} catch (Exception e) {
			return ApiResponse.defaultFail();
		}
		return ApiResponse.success();
	}

	// 推送诊后提醒：重新发送
	@RequestMapping(value = "/sendRemindByHistory", method = RequestMethod.POST)
	public @ResponseBody ApiResponse sendRemindByHistory(@RequestParam("unionid") String unionid, @RequestParam("id") int id) throws IOException {
		// TODO 发起推送
		return ApiResponse.success();
	}
}
