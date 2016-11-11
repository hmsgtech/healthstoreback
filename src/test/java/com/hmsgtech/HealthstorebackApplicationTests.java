package com.hmsgtech;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hmsgtech.domain.store.Store;
import com.hmsgtech.service.StoreService;
import com.hmsgtech.utils.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HealthstorebackApplication.class)
@WebAppConfiguration
public class HealthstorebackApplicationTests {
	@Autowired
	StoreService storeService;

	@Test
	public void contextLoads() {
		findBySpecAndPaginate();
	}

	public void findBySpecAndPaginate() {
		String name = "s";
		float latitude = 12f;
		float longitude = 3f;
		int city = 454;
		List<Store> stores = storeService.findAgencies(name, city, longitude, latitude, 0, 10);
		// 查询条件
		for (Store store : stores) {
			System.out.println(JsonUtil.toJson(store));
		}
	}
}
