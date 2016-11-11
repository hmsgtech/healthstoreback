package com.hmsgtech.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hmsgtech.constants.CompanyEnum;
import com.hmsgtech.constants.StudioAppUserStatus;
import com.hmsgtech.constants.YesNoStatus;
import com.hmsgtech.domain.store.Store;
import com.hmsgtech.domain.studio.StudioUser;
import com.hmsgtech.repository.BuyOrderRepository;
import com.hmsgtech.repository.GoodsRepository;
import com.hmsgtech.repository.GoodsStoreRelationshipRepository;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.repository.basedao.Criteria;
import com.hmsgtech.repository.basedao.Restrictions;
import com.hmsgtech.repository.studio.StudioUserRepository;

@Service
public class StoreService {

	@Autowired
	BuyOrderRepository buyOrderRepository;
	@Autowired
	GoodsRepository goodsRepository;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	StudioUserRepository studioUserRepository;
	@Autowired
	GoodsStoreRelationshipRepository goodsStoreRelationshipRepository;

	public List<Store> findStores(long goodsId, int city, float longitude, float latitude, int page, int size) {
		List<Long> storeIds = goodsStoreRelationshipRepository.findStoreIdsByGoodsId(goodsId);
		if (latitude > 0 && longitude > 0) {
			List<Store> stores = findStoresByLocation(storeIds, city, longitude, latitude, page, size);
			if (stores.size() > 0) {
				stores.get(0).setAvailableTime(gps2m(latitude, longitude, stores.get(0).getLatitude(), stores.get(0).getLongitude()).toString());
			}
		}
		// 分页属性
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "rank"));
		// 查询条件
		Criteria<Store> where = new Criteria<Store>();
		where.add(Restrictions.in("id", storeIds, true));
		where.add(Restrictions.eq("status", 1, true));
		if (city > 0)
			where.add(Restrictions.eq("cityId", city, true));

		return storeRepository.findAll(where, pageable).getContent();
	}

	public List<Store> findStoresByLocation(List<Long> ids, int city, float longitude, float latitude, int page, int size) {
		Pageable pageable = new PageRequest(page, size, null);
		if (city > 0)
			return storeRepository.findStoresByCityAndLocation(city, longitude, latitude, ids, pageable);
		else
			return storeRepository.findStoresByLocation(ids, longitude, latitude, pageable);
	}

	public List<Store> findAgencies(String name, int city, float longitude, float latitude, int page, int size) {
		if (latitude > 0 && longitude > 0) {
			return findAgenciesByLocation(name, city, longitude, latitude, page, size);
		}
		// 分页属性
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		// 查询条件
		Criteria<Store> where = new Criteria<Store>();
		where.add(Restrictions.eq("companyId", CompanyEnum.MEIRONG.getValue(), true));
		where.add(Restrictions.eq("status", YesNoStatus.YES.getValue(), true));
		if (city > 0)
			where.add(Restrictions.eq("cityId", city, true));
		if (!StringUtils.isEmpty(name)) {
			where.add(Restrictions.like("name", "%" + name + "%", true));
		}

		return storeRepository.findAll(where, pageable).getContent();
	}

	public List<Store> findAgenciesByLocation(String name, int city, float longitude, float latitude, int page, int size) {
		Pageable pageable = new PageRequest(page, size, null);
		if (city > 0 && !StringUtils.isEmpty(name))
			return storeRepository.findAgenciesByCityAndNameAndLocation(city, longitude, latitude, "%" + name + "%", CompanyEnum.MEIRONG.getValue(), pageable);
		else if (city > 0)
			return storeRepository.findAgenciesByCityAndLocation(city, longitude, latitude, CompanyEnum.MEIRONG.getValue(), pageable);
		else if (!StringUtils.isEmpty(name))
			return storeRepository.findAgenciesByNameAndLocation(longitude, latitude, "%" + name + "%", CompanyEnum.MEIRONG.getValue(), pageable);
		else
			return storeRepository.findAgenciesOrderByLocation(longitude, latitude, CompanyEnum.MEIRONG.getValue(), pageable);

	}

	public StudioUser initStoreUser(String unionid) {
		StudioUser studioUser = new StudioUser();
		studioUser.setUnionid(unionid);
		studioUser.setName("小伊助手");
		studioUser.setStatus(StudioAppUserStatus.DEFAULT.getValue());
		studioUser.setCreateTime(new Date());
		studioUserRepository.save(studioUser);
		return studioUser;
	}

	private static Double gps2m(float lat_a, float lat_b, float lng_a, float lng_b) {

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
