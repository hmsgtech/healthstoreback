package com.hmsgtech.coreapi;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HmsgMapResponse extends HmsgResponse {
	private HashMap<String, Object> value;

	public HashMap<String, Object> getValue() {
		return value;
	}

	public void setValue(HashMap<String, Object> value) {
		this.value = value;
	}

}
