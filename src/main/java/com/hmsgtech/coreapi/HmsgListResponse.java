package com.hmsgtech.coreapi;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HmsgListResponse extends HmsgResponse {
	private List<HashMap<String, Object>> value;

	public List<HashMap<String, Object>> getValue() {
		return value;
	}

	public void setValue(List<HashMap<String, Object>> value) {
		this.value = value;
	}

}
