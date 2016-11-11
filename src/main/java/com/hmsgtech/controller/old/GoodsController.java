package com.hmsgtech.controller.old;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsgtech.coreapi.HmsgService;
import com.hmsgtech.coreapi.HmsgUserInfo;
import com.hmsgtech.domain.Category;
import com.hmsgtech.domain.Favourite;
import com.hmsgtech.domain.GoodsCategoryRelationship;
import com.hmsgtech.domain.TodayGoods;
import com.hmsgtech.domain.goods.Goods;
import com.hmsgtech.domain.order.StoreRank;
import com.hmsgtech.domain.store.Company;
import com.hmsgtech.domain.store.Store;
import com.hmsgtech.domain.store.SuitStores;
import com.hmsgtech.repository.CategoryRepository;
import com.hmsgtech.repository.CompanyRepository;
import com.hmsgtech.repository.FavouriteRepository;
import com.hmsgtech.repository.GoodsCategoryRepository;
import com.hmsgtech.repository.GoodsRankRepository;
import com.hmsgtech.repository.GoodsRepository;
import com.hmsgtech.repository.StoreRankRepository;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.repository.SuitStoresRepository;
import com.hmsgtech.repository.TodayGoodsRepository;
import com.hmsgtech.web.model.GoodsModel;
import com.hmsgtech.web.model.StoreDetaileModel;

/**
 * Created by 晓丰 on 2016/5/10.
 */
@Controller
public class GoodsController {

	@Autowired
	GoodsRepository goodsRepository;

	@Autowired
	GoodsCategoryRepository goodsCategoryRepository;

	@Autowired
	FavouriteRepository favouriteRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	HmsgService hmsgService;

	@Autowired
	GoodsRankRepository goodsRankRepository;

	@Autowired
	SuitStoresRepository suitStoresRepository;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	StoreRankRepository storeRankRepository;

	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	TodayGoodsRepository todayGoodsRepository;

	@RequestMapping("listTodayGoods")
	public @ResponseBody List<TodayGoods> getTodayGoodsList() {
		List<TodayGoods> todayGoods = (List<TodayGoods>) todayGoodsRepository.findAll();
		for (TodayGoods today : todayGoods) {
			Goods one = goodsRepository.findGoodsByIdAndIsSell(today.getGoodsId());
			int count = goodsRankRepository.getNiceGoodsRanksCount(one.getId(), 4, 5);
			one.setGoodsRank(count);
			if (one != null) {
				today.setGoods(one);
			}
		}
		return todayGoods;
	}

	@RequestMapping("searchGoods")
	public @ResponseBody List<Goods> getGoodsListBySearch(@RequestParam(value = "keyword", required = true) String keyword) {
		List<Goods> goodses = goodsRepository.findGoodsByNameLikeAndIsSell("%" + keyword + "%");
		for (Goods g : goodses) {
			int count = goodsRankRepository.getNiceGoodsRanksCount(g.getId(), 4, 5);
			g.setGoodsRank(count);
		}
		return goodses;
	}

	@RequestMapping("listGoods")
	public @ResponseBody List<Goods> getGoodsList(@RequestParam(value = "order", required = false, defaultValue = "sellNum") String order, @RequestParam("type") long type) {
		List<GoodsCategoryRelationship> goodsCategoryRelationshipsByCatelog = goodsCategoryRepository.findGoodsCategoryRelationshipsByCategoryId(type);
		List<Goods> goodses = new ArrayList<>();
		for (GoodsCategoryRelationship relationship : goodsCategoryRelationshipsByCatelog) {
			Goods one = goodsRepository.findGoodsByIdAndIsSell(relationship.getGoodsId());
			if (one != null) {
				goodses.add(one);
			}
		}

		for (Goods g : goodses) {
			int count = goodsRankRepository.getNiceGoodsRanksCount(g.getId(), 4, 5);
			g.setGoodsRank(count);
		}

		return goodses;
	}

	@RequestMapping("goods/{goodsId}")
	public @ResponseBody GoodsModel getGoods(@PathVariable("goodsId") long id) throws Exception {
		GoodsModel goodsModel = new GoodsModel();

		Goods goods = goodsRepository.findOne(id);
		if (goods == null) {
			return null;
		}
		goodsModel.setGoods(goods);

		if (goods.getDetailImages() != null && !"".equals(goods.getDetailImages())) {
			ObjectMapper objectMapper = new ObjectMapper();
			JavaType stringArraylist = objectMapper.getTypeFactory().constructType(ArrayList.class, String.class);
			List<String> detailImages = objectMapper.readValue(goods.getDetailImages(), stringArraylist);
			goodsModel.setDetailImages(detailImages);
		}
		return goodsModel;
	}

	@RequestMapping("addFavourite")
	public @ResponseBody String addFavourite(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("goodsId") long goodsId) throws IOException {
		String status = "{\"status\":\"fail\"}";
		String userByToken = hmsgService.getUserByToken(token);
		try {
			List<Favourite> favouriteList = favouriteRepository.findFavouriteByUseridAndGoodsId(userid, goodsId);
			if (favouriteList.size() == 0) {
				Favourite favourite = new Favourite();
				favourite.setGoodsId(goodsId);
				favourite.setUserid(userid);
				favouriteRepository.save(favourite);
				status = "{\"status\":\"success\",\"flag\":\"1\"}";
			} else {
				favouriteRepository.deleteFavouriteByUseridAndGoodsId(userid, goodsId);
				status = "{\"status\":\"success\",\"flag\":\"0\"}";
			}
			return status;
		} catch (Exception e) {
			return status;
		}

	}

	@RequestMapping("listFavouriteGoods")
	public @ResponseBody List<Goods> listFavouriteGoods(@RequestParam("token") String token, @RequestParam("userid") String userid) throws IOException {
		String userByToken = hmsgService.getUserByToken(token);
		List<Favourite> favouriteList = favouriteRepository.findFavouriteListByUserid(userid);
		List<Goods> favouriteGoodsList = new ArrayList<>();
		for (Favourite f : favouriteList) {
			Goods one = goodsRepository.findGoodsByIdAndIsSell(f.getGoodsId());
			if (one != null) {
				favouriteGoodsList.add(one);
			}

		}
		return favouriteGoodsList;
	}

	@RequestMapping("deleteFavouriteGoodsByIds")
	public @ResponseBody String deleteFavouriteGoodsByIds(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("goodsIds") String goodsIds)
			throws IOException {
		String userByToken = hmsgService.getUserByToken(token);
		String status = "{\"status\":\"fail\"}";
		try {
			String[] idsArray = goodsIds.split(",");
			List<Long> idsList = new ArrayList<Long>();
			for (String s : idsArray) {
				idsList.add(Long.parseLong(s));
			}
			favouriteRepository.deleteFavouriteByUseridAndGoodsIds(userid, idsList);
			status = "{\"status\":\"success\"}";
			return status;
		} catch (Exception e) {
			return status;
		}
	}

	@RequestMapping("findFavouriteStatus")
	public @ResponseBody String findFavouriteStatus(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("goodsId") long goodsId)
			throws IOException {
		String status = "{\"status\":\"fail\"}";
		String userByToken = hmsgService.getUserByToken(token);
		try {
			List<Favourite> favouriteList = favouriteRepository.findFavouriteByUseridAndGoodsId(userid, goodsId);
			if (favouriteList.size() == 0) {
				status = "{\"status\":\"success\",\"flag\":\"0\"}";
			} else {
				status = "{\"status\":\"success\",\"flag\":\"1\"}";
			}
			return status;
		} catch (Exception e) {
			return status;
		}
	}

	@RequestMapping("getCategory")
	public @ResponseBody Category getCategory(@RequestParam("type") long type) throws IOException {
		Category category = categoryRepository.findOne(type);
		System.out.println(category);
		return category;
	}

	@RequestMapping("getstores/{goodsid}")
	public @ResponseBody List<Store> getSuitStores(@PathVariable("goodsid") long goodsid) throws IOException {
		SuitStores suitStore = suitStoresRepository.findOne(goodsid);
		if (suitStore == null) {
			return new ArrayList<Store>();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType intArraylist = objectMapper.getTypeFactory().constructType(ArrayList.class, Integer.class);
		List<Integer> storeIds = objectMapper.readValue(suitStore.getStoresIds(), intArraylist);

		List<Store> stores = new ArrayList<Store>();

		for (int storeId : storeIds) {
			stores.add(storeRepository.findOne((long) storeId));
		}
		return stores;
	}

	@RequestMapping("getstoresbucity")
	public @ResponseBody List<Store> getStoresBycity(@RequestParam("goodsid") long goodsid, @RequestParam("cityid") long cityid) throws IOException {
		SuitStores suitStore = suitStoresRepository.findOne(goodsid);
		if (suitStore == null) {
			return new ArrayList<Store>();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType intArraylist = objectMapper.getTypeFactory().constructType(ArrayList.class, Integer.class);
		List<Integer> storeIds = objectMapper.readValue(suitStore.getStoresIds(), intArraylist);

		List<Store> stores = new ArrayList<Store>();

		for (int storeId : storeIds) {
			Store store = storeRepository.findOne((long) storeId);
			if (cityid == 0) {
				stores.add(store);
			} else if (store.getCityId() == cityid) {
				stores.add(store);
			}
		}
		return stores;
	}

	@RequestMapping("getstoredetaile/{storeid}")
	public @ResponseBody StoreDetaileModel getStoreDetaileModel(@PathVariable("storeid") long storeid) throws IOException {
		StoreDetaileModel storeDetaileModels = new StoreDetaileModel();
		Store store = storeRepository.findOne(storeid);
		storeDetaileModels.setStore(store);

		List<StoreRank> storeRanks = storeRankRepository.findStoreRankByStoreId(storeid);
		for (StoreRank storeRank : storeRanks) {
			HmsgUserInfo hmsgUserInfo = null;
			try {
				hmsgUserInfo = hmsgService.getUserInfoByUserId(storeRank.getUserId());
				if (hmsgUserInfo != null) {
					storeRank.setNickName(hmsgUserInfo.getNickName());
					storeRank.setSnapshot(hmsgUserInfo.getSnapshot());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		storeDetaileModels.setRanks(storeRanks);
		int sum = 5;
		if (storeRankRepository.findStoreRankByStoreId(storeid).size() > 0) {
			sum = storeRankRepository.getSumBysStoreId(storeid);
		}
		storeDetaileModels.setRankavg(sum);

		return storeDetaileModels;
	}

	@RequestMapping("getnearest")
	public @ResponseBody Store getNearest(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude, @RequestParam("goodsId") long goodsId)
			throws IOException {
		SuitStores suitStores = suitStoresRepository.findOne(goodsId);

		ObjectMapper objectMapper = new ObjectMapper();
		JavaType intArraylist = objectMapper.getTypeFactory().constructType(ArrayList.class, Integer.class);
		List<Integer> storeIds = objectMapper.readValue(suitStores.getStoresIds(), intArraylist);

		List<Store> stores = new ArrayList<Store>();

		for (int storeId : storeIds) {
			stores.add(storeRepository.findOne((long) storeId));
		}

		Map<Double, Long> maps = new HashMap<Double, Long>();
		for (Store store : stores) {
			Double distance = gps2m(latitude, longitude, String.valueOf(store.getLatitude()), String.valueOf(store.getLongitude()));
			maps.put(distance, store.getId());
		}

		List<Map.Entry<Double, Long>> infoIds = new ArrayList<Map.Entry<Double, Long>>(maps.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<Double, Long>>() {
			public int compare(Map.Entry<Double, Long> o1, Map.Entry<Double, Long> o2) {
				return (o1.getKey()).compareTo(o2.getKey());
			}
		});

		long id = infoIds.get(0).getValue();
		Store srore = storeRepository.findOne(id);
		srore.setAvailableTime(infoIds.get(0).getKey().toString());
		return srore;
	}

	@RequestMapping("getCompany")
	public @ResponseBody Company getCompany(@RequestParam("goodsId") long goodsId) {
		Goods goods = goodsRepository.findOne(goodsId);
		return companyRepository.findOne(goods.getCompanyId());
	}

	/**
	 * lat_a1
	 */
	private static double gps2m(String lat_a1, String lng_a1, String lat_b1, String lng_b1) {
		double lat_a = Double.parseDouble(lat_a1);
		double lng_a = Double.parseDouble(lng_a1);
		double lat_b = Double.parseDouble(lat_b1);
		double lng_b = Double.parseDouble(lng_b1);

		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
}
