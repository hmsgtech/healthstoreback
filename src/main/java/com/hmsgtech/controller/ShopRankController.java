package com.hmsgtech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsgtech.coreapi.HmsgService;
import com.hmsgtech.coreapi.HmsgUserInfo;
import com.hmsgtech.domain.common.City;
import com.hmsgtech.domain.order.Agree;
import com.hmsgtech.domain.order.GoodsRank;
import com.hmsgtech.domain.order.StoreRank;
import com.hmsgtech.repository.AgreeRepository;
import com.hmsgtech.repository.BuyOrderRepository;
import com.hmsgtech.repository.CityRepository;
import com.hmsgtech.repository.GoodsRankRepository;
import com.hmsgtech.repository.StoreRankRepository;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.web.model.GoodsRankModel;
import com.hmsgtech.web.model.RankModel;

/**
 * 评分
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/rank")
public class ShopRankController {

	@Autowired
	GoodsRankRepository goodsRankRepository;

	@Autowired
	StoreRankRepository storeRankRepository;

	@Autowired
	BuyOrderRepository buyOrderRepository;

	@Autowired
	AgreeRepository agreeRepository;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	HmsgService hmsgService;

	@Transactional
	@RequestMapping(value = "/saverank", method = RequestMethod.POST)
	public @ResponseBody String addRank(@RequestParam(value = "sdescription", defaultValue = "") String sdescription,
			@RequestParam(value = "totalRatingVal", defaultValue = "0") int totalRatingVal, @RequestParam(value = "medicalRatingVal", defaultValue = "0") int medicalRatingVal,
			@RequestParam(value = "queueRatingVal", defaultValue = "0") int queueRatingVal, @RequestParam(value = "mechantRatingVal", defaultValue = "0") int mechantRatingVal,
			@RequestParam("orderId") int orderId, @RequestParam("userid") String userid, @RequestParam("detailes") String detailes, @RequestParam("image1") String image1,
			@RequestParam("image2") String image2, @RequestParam("image3") String image3, @RequestParam("storeid") int storeid)
			throws JsonParseException, JsonMappingException, IOException {
		RankModel model = new RankModel();
		ObjectMapper objectMapper = new ObjectMapper();
		// 封装商户评分
		StoreRank sRank = enStoreRank(sdescription, totalRatingVal, medicalRatingVal, queueRatingVal, mechantRatingVal, orderId, userid, image1, image2, image3, storeid);

		storeRankRepository.save(sRank);

		storeRepository.updateStoreById(storeRankRepository.getSumBysStoreId(storeid), storeid);
		// 封装商品评分
		JavaType goodsArraylist = objectMapper.getTypeFactory().constructParametrizedType(ArrayList.class, ArrayList.class, GoodsRankModel.class);
		List<GoodsRankModel> goodsRankModels = objectMapper.readValue("[" + detailes + "]", goodsArraylist);
		List<GoodsRank> goodsRanks = new ArrayList<GoodsRank>();
		for (GoodsRankModel goodsRankModel : goodsRankModels) {
			GoodsRank goodsRank = enGoodsRank(orderId, userid, goodsRankModel);
			goodsRankRepository.save(goodsRank);
		}

		buyOrderRepository.updateBuyOrderById(6, orderId);
		model.setStoreRank(sRank);
		model.setGoodsRanks(goodsRanks);
		return "{\"status\":\"success\"}";

	}

	@RequestMapping("/getGoodsRanks")
	public @ResponseBody List<GoodsRank> getGoodsRanks(@RequestParam("goodsId") long goodsId, @RequestParam("userid") String userid) {
		List<GoodsRank> goodsRank = goodsRankRepository.findGoodsRankByGoodsId(goodsId);
		for (GoodsRank rank : goodsRank) {
			HmsgUserInfo hmsgUserInfo = null;
			try {
				hmsgUserInfo = hmsgService.getUserInfoByUserId(rank.getUserId());
				if (hmsgUserInfo != null) {
					rank.setNickName(hmsgUserInfo.getNickName());
					rank.setSnapshot(hmsgUserInfo.getSnapshot());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			int agrees = agreeRepository.getSumByRankId(rank.getId());
			rank.setAgrees(agrees);
			int ranks = agreeRepository.getSumByRankId(rank.getId(), userid);
			if (ranks >= 1) {
				rank.setAgree(true);
			} else {
				rank.setAgree(false);
			}
		}
		return goodsRank;
	}

	// 获取好评数
	@RequestMapping("/getNiceGoodsRanks")
	public @ResponseBody List<GoodsRank> getNiceGoodsRanks(@RequestParam("goodsId") long goodsId) {
		List<GoodsRank> goodsRank = goodsRankRepository.findGoodsRankByGoodsIdAndGoodsRank(goodsId, 4, 5);
		return goodsRank;
	}

	// 点赞&取消赞
	@RequestMapping("/addAgree")
	public @ResponseBody String addAgree(@RequestParam("rankId") long rankId, @RequestParam("userId") String userId) {
		Agree agree = agreeRepository.findAgreeByUserIdAndRankId(userId, rankId);
		if (agree != null) {
			agreeRepository.delete(agree);
			return "{\"isexist\":\"true\",\"agree\":\"" + agreeRepository.getSumByRankId(rankId) + "\",\"isagree\":\"" + agreeRepository.getSumByRankId(rankId) + "\"}";
		}
		agree = new Agree();
		agree.setRankId(rankId);
		agree.setUserId(userId);
		agreeRepository.save(agree);
		return "{\"isexist\":\"false\",\"agree\":\"" + agreeRepository.getSumByRankId(rankId) + "\",\"isagree\":\"" + agreeRepository.getSumByRankId(rankId) + "\"}";
	}

	// 获取城市
	@RequestMapping("/getcities")
	public @ResponseBody List<City> getCities(@RequestParam("selectid") int selectid) {
		List<City> cities = cityRepository.findCityByProvince(selectid);
		return cities;
	}

	private StoreRank enStoreRank(String sdescription, int totalRatingVal, int medicalRatingVal, int queueRatingVal, int mechantRatingVal, int orderId, String userid,
			String image1, String image2, String image3, int storeid) {
		StoreRank sRank = new StoreRank();
		sRank.setSdescription(sdescription);
		sRank.setService(mechantRatingVal);
		sRank.setWaitTime(queueRatingVal);
		sRank.setRankDate(new Date());
		sRank.setLevel(medicalRatingVal);
		sRank.setRank(totalRatingVal);
		sRank.setOrderId(orderId);
		sRank.setUserId(userid);
		sRank.setImages("[\"" + image1 + "\",\"" + image2 + "\",\"" + image3 + "\"]");
		sRank.setStoreId(storeid);
		return sRank;
	}

	private GoodsRank enGoodsRank(int orderId, String userid, GoodsRankModel goodsRankModel) {
		GoodsRank goodsRank = new GoodsRank();
		goodsRank.setGoodsRank(goodsRankModel.getRatingVal());
		goodsRank.setRankDate(new Date());
		goodsRank.setGoodsId(goodsRankModel.getId());
		goodsRank.setUserId(userid);
		goodsRank.setOrderId(orderId);
		goodsRank.setGdescription(goodsRankModel.getGdescription());
		goodsRank.setAgree(false);
		goodsRank.setImages("[\"" + goodsRankModel.getImage1() + "\",\"" + goodsRankModel.getImage2() + "\",\"" + goodsRankModel.getImage3() + "\"]");
		return goodsRank;
	}
}
