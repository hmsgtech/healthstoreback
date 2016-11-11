package com.hmsgtech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsgtech.coreapi.HmsgService;
import com.hmsgtech.domain.TodayGoods;
import com.hmsgtech.domain.goods.Goods;
import com.hmsgtech.domain.store.Store;
import com.hmsgtech.repository.ActivityGoodsRepository;
import com.hmsgtech.repository.ClassifyCommodityGoodRepository;
import com.hmsgtech.repository.FavouriteRepository;
import com.hmsgtech.repository.GoodsRankRepository;
import com.hmsgtech.repository.GoodsRepository;
import com.hmsgtech.repository.TodayGoodsRepository;
import com.hmsgtech.service.GoodsService;
import com.hmsgtech.service.StoreService;
import com.hmsgtech.utils.StringsUtil;
import com.hmsgtech.web.model.GoodsModel;

@Controller
@RequestMapping("/goodsQuery")
public class ShopGoodsQueryController {

	@Autowired
	GoodsService goodsService;
	@Autowired
	GoodsRepository goodsRepository;
	@Autowired
	GoodsRankRepository goodsRankRepository;
	@Autowired
	TodayGoodsRepository todayGoodsRepository;
	@Autowired
	StoreService storeService;
	@Autowired
	ActivityGoodsRepository activityGoodsRepository;
	@Autowired
	ClassifyCommodityGoodRepository classifyCommodityGoodRepository;
	@Autowired
	HmsgService hmsgService;
	@Autowired
	FavouriteRepository favouriteRepository;

	/**
	 * 搜索商品
	 * 
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/search")
	public @ResponseBody List<Goods> getGoodsListBySearch(@RequestParam(value = "keyword", required = true) String keyword,
			@RequestParam(value = "city", required = false, defaultValue = "0") int city) {
		List<Goods> goodses;
		if (city > 0) {
			// 城市下的店铺>店铺包含的商品
			List<Long> goodsIds = goodsService.getGoodsIdsByCity(city);
			goodses = goodsRepository.findGoodsByNameLikeAndIds("%" + keyword + "%", goodsIds);
		} else {
			goodses = goodsRepository.findGoodsByNameLikeAndIsSell("%" + keyword + "%");
		}
		return goodses;
	}

	/**
	 * 今日特价商品forAPP
	 * 
	 * @return
	 */
	@RequestMapping("/listTodayGoods")
	public @ResponseBody List<TodayGoods> getTodayGoodsList() {
		List<TodayGoods> todayGoods = (List<TodayGoods>) todayGoodsRepository.findAll();
		for (TodayGoods today : todayGoods) {
			Goods one = goodsRepository.findGoodsByIdAndIsSell(today.getGoodsId());
			if (one != null) {
				today.setGoods(one);
			}
		}
		return todayGoods;
	}

	/**
	 * 
	 * 查看商品列表:按活动分类
	 * 
	 * @param order
	 * @param type
	 * @return
	 */
	@RequestMapping("/activity")
	public @ResponseBody List<Goods> getGoodsListByActivity(@RequestParam("activityId") int activityId,
			@RequestParam(value = "city", required = false, defaultValue = "0") int city, @RequestParam(value = "type", required = false, defaultValue = "0") int type,
			@RequestParam(value = "sort", required = false, defaultValue = "0") int sort) {
		// 查询活动下的商品IDS
		List<Long> goodsIds = activityGoodsRepository.findgoodsIdsByActivityId(activityId);
		if (city > 0) {
			// 取 城市下的店铺商品+与城市无关商品的合集后去重 再和活动商品IDS取交集
			List<Long> cityGoodsIds = goodsService.getGoodsIdsByCity(city);
			goodsIds.retainAll(cityGoodsIds);
		}
		List<Goods> goodsList = new ArrayList<Goods>();
		if (goodsIds.isEmpty()) {
			return goodsList;
		}
		if (type > 0) {
			goodsList = goodsRepository.findAllByIdsAndType(goodsIds, type, goodsService.getSort(sort));
		} else {
			goodsList = goodsRepository.findAllByIds(goodsIds, goodsService.getSort(sort));
		}
		return goodsList;
	}

	/**
	 * 查看商品列表:按店铺类型分类
	 * 
	 * @param goodsType
	 * @param page
	 * @return
	 */
	@RequestMapping("/goodsType")
	public @ResponseBody List<Goods> getGoodsListByType(@RequestParam("goodsType") int goodsType, @RequestParam("page") int page,
			@RequestParam(value = "size", required = false, defaultValue = "15") int size, @RequestParam(value = "sort", required = false, defaultValue = "0") int sort,
			@RequestParam(value = "classifyCommodities", required = false, defaultValue = "") String classifyCommodities,
			@RequestParam(value = "city", required = false, defaultValue = "0") int city) {

		List<Long> goodsIds = new ArrayList<Long>();
		boolean notbyGoodsIds = true;
		// ids.字符串转list
		if (!StringsUtil.isEmpty(classifyCommodities) && !"0".equals(classifyCommodities)) {
			notbyGoodsIds = false;
			List<Long> goodsIds1 = classifyCommodityGoodRepository.findGoodsIdsByCommodityIds(StringsUtil.strToList(classifyCommodities));
			goodsIds.addAll(goodsIds1);
		}
		if (city > 0) {
			notbyGoodsIds = false;
			List<Long> cityGoodsIds = goodsService.getGoodsIdsByCity(city);
			// 城市下的店铺>店铺包含的商品
			goodsIds.addAll(cityGoodsIds);
		}
		StringsUtil.removeDuplicate(goodsIds);
		Pageable pageable = new PageRequest(page, size, goodsService.getSort(sort));
		List<Goods> goodses;
		if (goodsIds.isEmpty() && notbyGoodsIds) {
			if (goodsType == 0) {
				return goodsRepository.findByPage(pageable);
			}
			return goodsRepository.findByGoodsType(goodsType, pageable);
		} else {
			if (goodsType == 0) {
				return goodsRepository.findByIds(goodsIds, pageable);
			}
			goodses = goodsRepository.findByGoodsTypeAndIds(goodsType, goodsIds, pageable);
		}
		return goodses;
	}

	/**
	 * 我的收藏
	 * 
	 * @param token
	 * @param userid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/favourite")
	public @ResponseBody List<Goods> listFavouriteGoods(@RequestParam("token") String token, @RequestParam("userid") String userid) throws IOException {
		List<Long> ids = favouriteRepository.findGoodsIdsByUserid(userid);
		return (List<Goods>) goodsRepository.findAll(ids);
	}

	/**
	 * 商品详情
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{goodsId}")
	public @ResponseBody GoodsModel getGoods(@PathVariable("goodsId") long id) throws Exception {
		GoodsModel goodsModel = new GoodsModel();
		Goods goods = goodsRepository.findOne(id);
		if (goods == null) {
			return null;
		}
		goodsModel.setGoods(goods);
		if (goods.getDetailImages() != null && !"".equals(goods.getDetailImages())) {
//			goods.setDetailImages(goods.getDescImages().replaceAll("\"", ""));
			// StringsUtil.strToListStr(goods.getDetailImages());
			ObjectMapper objectMapper = new ObjectMapper();
			JavaType stringArraylist = objectMapper.getTypeFactory().constructType(ArrayList.class, String.class);
			List<String> detailImages = objectMapper.readValue(goods.getDetailImages(), stringArraylist);
			goodsModel.setDetailImages(detailImages);
		}
		return goodsModel;
	}

	@RequestMapping("/storeList")
	public @ResponseBody List<Store> findStores(@RequestParam(value = "goodsid") long goodsid, @RequestParam(value = "city", required = false, defaultValue = "0") int city,
			@RequestParam(value = "longitude", required = false, defaultValue = "0") Float longitude,
			@RequestParam(value = "latitude", required = false, defaultValue = "0") Float latitude, @RequestParam(value = "page") int page,
			@RequestParam(value = "size", required = false) Integer size) {
		return storeService.findStores(goodsid, city, longitude, latitude, page, size);
	}
}
