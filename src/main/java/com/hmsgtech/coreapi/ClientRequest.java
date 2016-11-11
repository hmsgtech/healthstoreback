package com.hmsgtech.coreapi;

import java.util.HashMap;

import com.hmsgtech.utils.JsonUtil;

public class ClientRequest {
	private String deviceid = "healthstore";

	private String channel = "shop";

	private String clientVersion = "";

	private String machineName = "";
	private Integer platform = 1;
	private String clientIp = "127.0.0.1";
	private int userType = 1;
	// 总请求
	private String method;
	// 方法名
	private String requestType;
	private String token = "";
	private HashMap<String, Object> params;

	public String toJson() {
		return JsonUtil.toJson(this);
	}

	public String getDeviceid() {
		return deviceid;
	}

	public String getChannel() {
		return channel;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public String getMachineName() {
		return machineName;
	}

	public Integer getPlatform() {
		return platform;
	}

	public String getClientIp() {
		return clientIp;
	}

	public int getUserType() {
		return userType;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public HashMap<String, Object> getParams() {
		return params;
	}

	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}

}
