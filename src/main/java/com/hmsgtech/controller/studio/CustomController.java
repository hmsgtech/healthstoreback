package com.hmsgtech.controller.studio;

import java.io.IOException;
import java.util.ArrayList;
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
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.domain.studioArticle.ArticleUseRecord;
import com.hmsgtech.domain.studioCustom.CustomTag;
import com.hmsgtech.domain.studioCustom.StudioCustomer;
import com.hmsgtech.utils.DateUtil;
import com.hmsgtech.utils.StringsUtil;

/**
 * 客户管理
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/custom")
public class CustomController extends StudioBaseController {

	// 添加推送记录：由微信服务调用:区分文章/模板
	@RequestMapping(value = "/scanArticleQr", method = RequestMethod.POST)
	public @ResponseBody ApiResponse scanArticleQr(@RequestParam("unionid") String unionid, @RequestParam("openid") String openid, @RequestParam("nickname") String nickname,
			@RequestParam("sex") int sex, @RequestParam("headimgurl") String headimgurl, @RequestParam("city") String city, @RequestParam("province") String province,
			@RequestParam("scene") int scene) throws IOException {
		ArticleUseRecord articleUseRecord = articleUseRecordRepository.findOne(scene);
		if (articleUseRecord == null) {
			return ApiResponse.fail(ReturnCode.RESOURCE_NULL);
		}
		// 给技师添加一个客户
		StudioCustomer studioCustomer = studioCustomerRepository.findByTechidAndCustomer(articleUseRecord.getUnionid(), openid);
		if (studioCustomer == null) {
			studioCustomer = new StudioCustomer(articleUseRecord.getUnionid(), openid, nickname, headimgurl, sex, articleUseRecord.getArticleTitle());
		}else{
			studioCustomer.setHeadimgurl(headimgurl);
			studioCustomer.setNickname(nickname);
		}
		articleService.addPushRecord(articleUseRecord, studioCustomer);
		return ApiResponse.success();
	}

	// TODO 推送结果：由微信服务调用:区分文章/模板

	// 查询客户列表：客户推送时间排序
	@RequestMapping("/customers")
	public @ResponseBody List<StudioCustomer> findCustomer(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam("unionid") String unionid,
			@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		return searchService.findCustomer(keyword, unionid, page, size);
	}

	// 更新客户信息
	@Transactional
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	public @ResponseBody ApiResponse setUserInfo(@RequestParam("id") int id, @RequestParam(value = "sex", required = false) Integer sex,
			@RequestParam(value = "name", required = false) String name, @RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "birth", required = false) String birth,
			@RequestParam(value = "profession", required = false) String profession, @RequestParam(value = "tags", required = false) String tags) throws Exception {
		StudioCustomer customer = studioCustomerRepository.findOne(id);
		if (customer == null) {
			return ApiResponse.fail(ReturnCode.RESOURCE_NULL);
		}
		if (sex != null && sex > 0) {
			customer.setSex(sex);
		}
		if (StringsUtil.isNotEmpty(birth)) {
			customer.setBirth(DateUtil.parse("yyyy-MM-dd", birth));
		}
		if (StringsUtil.isNotEmpty(name)) {
			customer.setName(name);
		}
		if (StringsUtil.isNotEmpty(remark)) {
			customer.setRemark(remark);
		}
		if (StringsUtil.isNotEmpty(phone)) {
			customer.setPhone(phone);
		}
		if (StringsUtil.isNotEmpty(profession)) {
			customer.setProfession(profession);
		}
		if (StringsUtil.isNotEmpty(tags)) {
			customer.setTags(tags);
		}
		studioCustomerRepository.save(customer);
		return ApiResponse.success();
	}

	// 查询客户的标签列表
	@RequestMapping("/findCustomerTag")
	public @ResponseBody List<CustomTag> findCustomerTag(@RequestParam("id") int id) {
		StudioCustomer customer = studioCustomerRepository.findOne(id);
		List<Integer> ids = StringsUtil.strToList(customer.getTags());
		if (ids.size() == 0) {
			return new ArrayList<>();
		} else {
			return (List<CustomTag>) customTagRepository.findAll(ids);
		}
	}

	// 查询技师的标签列表
	@RequestMapping("/findCustomTags")
	public @ResponseBody List<CustomTag> findCustomTag(@RequestParam("unionid") String unionid, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "hotSort", "createTime"));
		return customTagRepository.findByUnionid(unionid, pageable);
	}

	// 添加标签
	@RequestMapping("/addCustomTag")
	public @ResponseBody ApiResponse addCustomTag(@RequestParam("unionid") String unionid, @RequestParam("name") String name) {
		// 检测标签是否存在
		if (customTagRepository.countByUnionidAndName(unionid, name) > 0) {
			ApiResponse.fail(ReturnCode.RESULT_EXIST);
		}
		// 新建标签并返回
		CustomTag customTag = new CustomTag(name, unionid);
		return ApiResponse.success(customTagRepository.save(customTag));
	}

	// 查询客户的所有推送

	// 使用历史：如果是文章

}
