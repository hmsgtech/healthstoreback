package com.hmsgtech;

import java.util.Date;

import com.google.gson.JsonObject;
import com.hmsgtech.utils.DateUtil;
import com.hmsgtech.utils.JsonUtil;

public class StringTest {
	public static void main(String[] args) {
		String reponse="{\"errorCode\":\"0\"}";
		JsonObject object = JsonUtil.gsonToObject(reponse, JsonObject.class);
		System.out.println("0".equals(object.get("errorCode").getAsString()));
		;
		System.out.println(System.currentTimeMillis() - 1477043397000L);
		System.out.println(new Date(1479895689777L).toLocaleString());
	}
}
