package com.hmsgtech.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.constants.YesNoStatus;
import com.hmsgtech.domain.common.Banner;
import com.hmsgtech.domain.goods.Activity;
import com.hmsgtech.domain.goods.Goods;
import com.hmsgtech.domain.goods.GoodsType;
import com.hmsgtech.repository.ActivityGoodsRepository;
import com.hmsgtech.repository.ActivityRepository;
import com.hmsgtech.repository.BannerRepository;
import com.hmsgtech.repository.GoodsRepository;
import com.hmsgtech.repository.GoodsTypeRepository;
import com.hmsgtech.web.model.ShopIndexModel;

/**
 * 首页查询:轮播图，活动分类，商品类型，每日伊选
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController {
	@Autowired
	BannerRepository bannerRepository;
	@Autowired
	GoodsRepository goodsRepository;
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	GoodsTypeRepository goodsTypeRepository;
	@Autowired
	ActivityGoodsRepository activityGoodsRepository;

	@RequestMapping
	public @ResponseBody ShopIndexModel indexModel() throws IOException {
		ShopIndexModel indexModel = new ShopIndexModel();
		indexModel.setActivities(activityRepository.findTop4ByStatusAndTypeOrderBySort(YesNoStatus.YES.getValue(), 1));
		indexModel.setBanners(bannerRepository.findByOrderBySort());
		Activity activity = activityRepository.findTopByType(2);
		List<Long> goodsIds = activityGoodsRepository.findgoodsIdsByActivityId(activity.getId());
		indexModel.setDayGoods((List<Goods>) goodsRepository.findAll(goodsIds));
		indexModel.setGoodsTypes(goodsTypeRepository.findTop4ByOrderBySort());
		indexModel.setDayGoodsImg(activity.getImg());
		return indexModel;
	}
}
