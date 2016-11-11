package com.hmsgtech.coreapi;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.hmsgtech.utils.JsonUtil;

/**
 * 调用微信服务
 * 
 * @author lujq
 *
 */
@Service
public class WechatService {

	@Value("${nginx.url}")
	String nginxHost;

	@Value("${wechat.url}")
	String wechatHost;

	String article_url = "/wechat/pages/article/preview.html";

	public String getWxArticleUrl(int id) {
		return nginxHost + article_url + "?id=" + id;
	}

	// 获取二维码
	public Map<String, Object> getQrScene(long sceneid) throws IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sceneid", String.valueOf(sceneid)));
		params.add(new BasicNameValuePair("channel", "qr_studio_article"));
		String reponse = Request.Post(wechatHost + "/getQR").bodyForm(params).execute().returnContent().asString();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = JsonUtil.readValue(reponse, HashMap.class);
		return map;
	}

	// 发送诊后提醒模板消息：URL
	public JsonObject sendTreatmentRemind(String openid, int id, String customName, String agencyName, String title) throws IOException {
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("touser", openid));
		params.add(new BasicNameValuePair("url", getWxArticleUrl(id)));
		params.add(new BasicNameValuePair("customName", customName));
		params.add(new BasicNameValuePair("agencyName", agencyName));
		params.add(new BasicNameValuePair("title", title));
		String reponse = Request.Post(nginxHost + "/wechatserver/sendReminTemplate").bodyForm(params, Charset.forName("UTF-8")).execute().returnContent().asString();

		JsonObject object = JsonUtil.gsonToObject(reponse, JsonObject.class);
		String code = object.get("errorCode").getAsString();
		if ("0".equals(code)) {
			return object.get("value").getAsJsonObject();
		} else if ("43004".equals(code)) {
			throw new HealthStoreException(ReturnCode.WX_NOT_SUBSCRIBE);
		} else if ("40003".equals(code)) {
			throw new HealthStoreException(ReturnCode.USER_NOT_EXIST);
		} else {
			throw new HealthStoreException(ReturnCode.SEND_REMIND_FAIL);
		}
	}
}