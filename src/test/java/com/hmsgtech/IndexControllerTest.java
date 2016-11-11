package com.hmsgtech;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.gson.JsonObject;
import com.hmsgtech.controller.IndexController;
import com.hmsgtech.coreapi.WechatService;
import com.hmsgtech.service.sms.MenWangSMSService;
import com.hmsgtech.utils.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HealthstorebackApplication.class)
@WebAppConfiguration
public class IndexControllerTest {
	@Autowired
	IndexController index;
	@Autowired
	WechatService wechatService;
	Log logger = LogFactory.getLog(IndexControllerTest.class);

	@Before
	public void init() throws IOException {
		JsonObject object = JsonUtil.gsonToObject("", JsonObject.class);
	}
	@Autowired
	private MenWangSMSService menService;
	@Test
	public void indexBannerList() throws IOException {
		wechatService.sendTreatmentRemind("oJ6fwv9ashkmS-Sd8Jtt7jAFzE6E", 123, "客户的昵称", "机构的名称", "消息的标题");
	}
//
//	@Test
//	public void indexActivityList() throws IOException {
//		logger.info(index.indexActivityList());
//	}
//
//	@Test
//	public void indexDayGoods() throws IOException {
//		logger.info(index.indexDayGoods());
//	}
//
//	@Test
//	public void indexGoodsTypeList() throws IOException {
//		logger.info(index.indexGoodsTypeList());
//	}

}
