package com.hmsgtech.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * JSON转换工具类
 */
public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Gson gson = new Gson();

	public static String gsonToString(Object obj) {
		return gson.toJson(obj);
	}

	public static <T> T gsonToObject(String body, Class<T> clazz) {
		return gson.fromJson(body, clazz);
	}

	public static String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("OrderRequest.toJson error", e);
		}
	}

	public static <T> T readValue(String body, Class<T> clazz) {
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(body, clazz);
		} catch (Exception e) {
			throw new RuntimeException("OrderRequest.fromJsonToOrderRequest error:" + body, e);
		}
	}

}
