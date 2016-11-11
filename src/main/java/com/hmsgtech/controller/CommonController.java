package com.hmsgtech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.constants.YesNoStatus;
import com.hmsgtech.coreapi.ApiResponse;
import com.hmsgtech.coreapi.HealthStoreException;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.domain.common.City;
import com.hmsgtech.domain.common.Province;
import com.hmsgtech.domain.goods.ClassifyCommodity;
import com.hmsgtech.domain.goods.ClassifyPerson;
import com.hmsgtech.domain.goods.GoodsType;
import com.hmsgtech.domain.studio.StudioIdentity;
import com.hmsgtech.domain.studioArticle.ArticleFirstSort;
import com.hmsgtech.domain.studioArticle.ArticleSecondSort;
import com.hmsgtech.repository.CityRepository;
import com.hmsgtech.repository.ClassifyCommodityPersonRepository;
import com.hmsgtech.repository.ClassifyCommodityRepository;
import com.hmsgtech.repository.ClassifyPersonRepository;
import com.hmsgtech.repository.GoodsTypePersonRepository;
import com.hmsgtech.repository.GoodsTypeRepository;
import com.hmsgtech.repository.ProvinceRepository;
import com.hmsgtech.repository.studio.ArticleFirstSortRepository;
import com.hmsgtech.repository.studio.ArticleSecondSortRepository;
import com.hmsgtech.repository.studio.StudioIdentityRepository;
import com.hmsgtech.service.CommonService;

/**
 * 常规查询：各种类型、分类等
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/common")
public class CommonController {
	@Autowired
	ProvinceRepository provinceRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	GoodsTypeRepository goodsTypeRepository;
	@Autowired
	ClassifyCommodityRepository classifyCommodityRepository;
	@Autowired
	ClassifyCommodityPersonRepository classifyCommodityPersonRepository;
	@Autowired
	ClassifyPersonRepository classifyPersonRepository;
	@Autowired
	GoodsTypePersonRepository goodsTypePersonRepository;
	@Autowired
	StudioIdentityRepository studioIdentityRepository;
	@Autowired
	ArticleFirstSortRepository articleFirstSortRepository;
	@Autowired
	ArticleSecondSortRepository articleSecondSortRepository;
	@Autowired
	CommonService commonService;

	/* 省份+ 城市 */
	@RequestMapping("/allLocation")
	public @ResponseBody List<Map<String, Object>> getAllLocation() throws IOException {
		List<Map<String, Object>> toReturn = new ArrayList<Map<String, Object>>();
		List<Province> provinceList = getAllProvince();
		for (Province location : provinceList) {
			Map<String, Object> locationMap = new HashMap<String, Object>();
			List<Map<String, Object>> childMap = new ArrayList<Map<String, Object>>();
			List<City> children = getCities(location.getId());
			for (City child : children) {
				Map<String, Object> oneChildMap = new HashMap<String, Object>();
				Map<String, Object> citymap = new HashMap<String, Object>();
				citymap.put("id", child.getId());
				citymap.put("name", child.getCity());
				oneChildMap.put("data", citymap);
				childMap.add(oneChildMap);
			}

			locationMap.put("children", childMap);
			locationMap.put("data", location);
			toReturn.add(locationMap);
		}
		return toReturn;
	}

	/* 省份 城市 */
	@RequestMapping("/provinces")
	public @ResponseBody List<Province> getAllProvince() throws IOException {
		List<Province> list = (List<Province>) provinceRepository.findAll();
		Province province = new Province();
		province.setId(0);
		province.setName("不限");
		list.add(0, province);
		return list;
	}

	@RequestMapping("/cities")
	public @ResponseBody List<City> getCities(@RequestParam("province") int province) {
		List<City> cities = new ArrayList<>();
		if (province != 0) {
			cities = cityRepository.findCityByProvince(province);
		} else {
			City city = new City();
			city.setCity("全部");
			city.setId(0);
			city.setProvince(0);
			cities.add(city);
		}
		return cities;
	}

	/**
	 * 查分类:服务类型
	 * 
	 * @param goodsType
	 * @param page
	 * @return
	 */
	@RequestMapping("/goodsTypes")
	public @ResponseBody List<GoodsType> getGoodsTypes() {
		List<GoodsType> goodsTypes = (List<GoodsType>) goodsTypeRepository.findAll();
		GoodsType goodsType = new GoodsType();
		goodsType.setId(0);
		goodsType.setName("全部");
		goodsTypes.add(0, goodsType);
		return goodsTypes;
	}

	/**
	 * 查分类:筛选分类
	 * 
	 * @param goodsType
	 * @param page
	 * @return
	 */
	@RequestMapping("/classifyPersons")
	public @ResponseBody List<ClassifyPerson> getClassifyPersons(@RequestParam(value = "goodsType", defaultValue = "0", required = false) int goodsType) {
		List<ClassifyPerson> classifyPersons = new ArrayList<>();
		if (goodsType > 0) {
			List<Integer> personIds = goodsTypePersonRepository.findPersonIdsByType(goodsType);
			classifyPersons = (List<ClassifyPerson>) classifyPersonRepository.findAll(personIds);
		} else {
			classifyPersons = (List<ClassifyPerson>) classifyPersonRepository.findAll();
		}
		ClassifyPerson classifyPerson = new ClassifyPerson();
		classifyPerson.setId(0);
		classifyPerson.setName("全部");
		classifyPersons.add(0, classifyPerson);
		return classifyPersons;
	}

	/**
	 * 查分类:筛选分类
	 * 
	 * @param goodsType
	 * @param page
	 * @return
	 */
	@RequestMapping("/classifyCommodities")
	public @ResponseBody List<ClassifyCommodity> getGoodsClassifies(@RequestParam("classifyPersonId") int classifyPersonId) {
		List<ClassifyCommodity> classifyCommodities = new ArrayList<ClassifyCommodity>();
		if (classifyPersonId == 0) {
			ClassifyCommodity classifyCommodity = new ClassifyCommodity();
			classifyCommodity.setId(0);
			classifyCommodity.setName("全部");
			classifyCommodities.add(classifyCommodity);
		} else {
			classifyCommodities = classifyCommodityRepository.findByClassifyPersonIdOrderBySort(classifyPersonId);
		}
		return classifyCommodities;
	}

	/**
	 * 所有身份
	 * 
	 * @return
	 */
	@RequestMapping("/identities")
	public @ResponseBody List<StudioIdentity> findIdentities() {
		return studioIdentityRepository.findByStatus(YesNoStatus.YES.getValue());
	}

	// 查文章分类
	@RequestMapping("/articleClassify")
	public @ResponseBody List<ArticleFirstSort> findArticleFirstSorts() {
		List<ArticleFirstSort> articleFirstSorts = (List<ArticleFirstSort>) articleFirstSortRepository.findAll();
		for (ArticleFirstSort articleFirstSort : articleFirstSorts) {
			articleFirstSort.setChildren(articleSecondSortRepository.findByFirstSort(articleFirstSort.getId()));
		}
		List<ArticleSecondSort> articleSecondSorts = new LinkedList<ArticleSecondSort>();
		ArticleSecondSort articleSecondSort = new ArticleSecondSort();
		articleSecondSort.setId(0);
		articleSecondSort.setFirstSort(0);
		articleSecondSort.setName("不限");
		articleSecondSorts.add(articleSecondSort);
		
		ArticleFirstSort not = new ArticleFirstSort();
		not.setId(0);
		not.setName("不限");
		not.setChildren(articleSecondSorts);
		articleFirstSorts.add(0, not);
		return articleFirstSorts;
	}

	/**
	 * 发送验证码：目前只有更换手机一种类型
	 * 
	 * @param mobile
	 * @param type
	 * @param validateCodeType
	 * @return
	 */
	@RequestMapping(value = "/sendXcode")
	public @ResponseBody ApiResponse sendXcode(@RequestParam(value = "mobile") String mobile, @RequestParam(value = "validateCodeType") int validateCodeType) {
		ReturnCode code = ReturnCode.SUCCESS;
		try {
			commonService.sendXcode(mobile, validateCodeType);
		} catch (HealthStoreException e) {
			code = e.getErrorCode();
			e.printStackTrace();
		} catch (Exception e) {
			code = ReturnCode.FAIL;
			e.printStackTrace();
		}
		return ApiResponse.fail(code);
	}
}
