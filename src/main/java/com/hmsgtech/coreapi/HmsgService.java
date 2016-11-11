package com.hmsgtech.coreapi;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsgtech.exception.TokenInvalidException;
import com.hmsgtech.utils.JsonUtil;

/**
 * Created by 晓丰 on 2016/5/29.
 */
@Service
public class HmsgService {

	@Value("${hmsg.url}")
	String hmsgUrlPrefix = "";

	public void validateUserToken(String userno, String token) throws IOException {
		String userid = getUserByToken(token);
		if (!userid.equals(userno)) {
			throw new TokenInvalidException("TOKEN ERROR");
		}
	}

	public String getUserByToken(String token) throws IOException {
		String result = Request.Post(hmsgUrlPrefix)
				.bodyForm(Form.form().add("body", "{\"token\":\"" + token + "\",\"method\":\"token\",\"requestType\":\"tokenLogin\",\"channel\":\"shop\",\"params\":{}}").build())
				.execute().returnContent().asString();
		ObjectMapper objectMapper = new ObjectMapper();
		HmsgMapResponse response = objectMapper.readValue(result, HmsgMapResponse.class);
		if (!response.getErrorCode().equals("0")) {
			if (response.getErrorCode().equals("1008")) {
				throw new TokenInvalidException(result);
			}
			throw new RuntimeException(result);
		}
		return (String) response.getValue().get("userno");
	}

	/**
	 * 获取用户头像和昵称
	 * 
	 * @param userid
	 * @return
	 * @throws IOException
	 */
	public HmsgUserInfo getUserInfoByUserId(String userid) throws IOException {
		HmsgUserInfo hmsgUserInfo = new HmsgUserInfo();
		String result = Request.Post(hmsgUrlPrefix)
				.bodyForm(Form.form().add("body",
						"{\"token\":\"\",\"method\":\"userInfo\",\"requestType\":\"findUserMainPage\",\"channel\":\"shop\",\"params\":{\"targetUserid\":\"" + userid + "\"}}")
						.build())
				.execute().returnContent().asString();
		ObjectMapper objectMapper = new ObjectMapper();
		HmsgMapResponse response = objectMapper.readValue(result, HmsgMapResponse.class);
		if (!response.getErrorCode().equals("0")) {
			if (response.getErrorCode().equals("10001")) {
				throw new TokenInvalidException(result + ",userid=" + userid);
			}
			throw new RuntimeException(result);
		}

		hmsgUserInfo.setNickName((String) response.getValue().get("nickName"));
		hmsgUserInfo.setSnapshot((String) response.getValue().get("snapshot"));
		hmsgUserInfo.setUserid((String) response.getValue().get("userid"));
		return hmsgUserInfo;
	}

	public String payOrder(String token, String userid, String orderid, int amount, String couponid) throws IOException {
		return Request.Post(hmsgUrlPrefix)
				.bodyForm(Form.form()
						.add("body", "{\"token\":\"" + token + "\",\"channel\":\"225\",\"method\":\"pay\",\"requestType\":\"payOrder\",\"params\":{\"userid\":\"" + userid
								+ "\",\"orderid\":\"" + orderid + "\",\"couponid\":\"" + couponid + "\",\"amount\":" + amount + ",\"channel\":\"weixin\",\"transactionType\":6}}")
						.build())
				.execute().returnContent().asString();
	}

	public HmsgResponse getPayOrder(String token, String userid, String orderid, int amount, String couponid) throws IOException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("orderid", orderid);
		params.put("couponid", couponid);
		params.put("channel", amount);
		params.put("amount", "weixin");
		params.put("transactionType", 6);
		HmsgResponse response = getClientMapResponse("pay", "payOrder", token, params);
		if (!response.getErrorCode().equals("0")) {
			throw new RuntimeException("getPayOrder Exception" + JsonUtil.toJson(params));
		}
		return response;
	}

	public HmsgResponse getCoupons(String token, String userid, long amount) throws IOException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("status", 2);
		params.put("type", "3");
		params.put("amount", amount);
		params.put("firstResult", 0);
		params.put("maxResults", 100);
		HmsgListResponse response = getClientListResponse("userRelation", "findCouponList", token, params);
		if (!response.getErrorCode().equals("0")) {
			throw new RuntimeException("getCoupons Exception" + JsonUtil.toJson(params));
		}
		return response;
	}

	public String getCouponCount(String token, String userid, long amount) throws IOException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("status", 2);
		params.put("type", "3");
		params.put("amount", amount);
		String response = getClientResponse("userRelation", "findCouponCount", token, params);
		Map<String, Object> responseMap = JsonUtil.readValue(response, HashMap.class);
		if (!responseMap.get("errorCode").equals("0")) {
			throw new RuntimeException("getCouponCount Exception" + JsonUtil.toJson(params));
		}
		return response;
	}

	public HmsgResponse addEgold(String token, String userid, int eglod, long goodsId) throws IOException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("name", "分享商品抽奖");
		params.put("resourceId", goodsId);
		params.put("type", "901");
		params.put("value", eglod);
		HmsgResponse response = getClientMapResponse("userOperate", "addEgold", token, params);
		if (!response.getErrorCode().equals("0")) {
			throw new RuntimeException("addEgold Exception" + JsonUtil.toJson(params));
		}
		return response;
	}

	public HmsgResponse addCoupon(String token, String userid, int couponAmount) throws IOException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("name", "分享商品抽奖");
		params.put("type", "3");
		params.put("monoey", couponAmount);
		params.put("days", 60);
		HmsgResponse response = getClientMapResponse("userOperate", "addCoupon", token, params);
		if (!response.getErrorCode().equals("0")) {
			throw new RuntimeException("addCoupon Exception" + JsonUtil.toJson(params));
		}
		return response;
	}

	public HmsgMapResponse getClientMapResponse(String method, String requestType, String token, HashMap<String, Object> params) throws IOException {
		String reponse = getClientResponse(method, requestType, token, params);
		return JsonUtil.readValue(reponse, HmsgMapResponse.class);
	}

	public HmsgListResponse getClientListResponse(String method, String requestType, String token, HashMap<String, Object> params) throws IOException {
		String reponse = getClientResponse(method, requestType, token, params);
		return JsonUtil.readValue(reponse, HmsgListResponse.class);
	}

	public String getClientResponse(String method, String requestType, String token, HashMap<String, Object> params) throws IOException {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setMethod(method);
		clientRequest.setRequestType(requestType);
		clientRequest.setToken(token);
		clientRequest.setParams(params);
		String reponse = Request.Post(hmsgUrlPrefix).bodyForm(Form.form().add("body", clientRequest.toJson()).build(), Charset.forName("utf-8")).execute().returnContent()
				.asString();
		return reponse;
	}
}