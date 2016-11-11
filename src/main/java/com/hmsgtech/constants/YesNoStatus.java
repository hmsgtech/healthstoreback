package com.hmsgtech.constants;

public enum YesNoStatus {

	YES(1),

	NO(0),

	;

	private Integer value;

	YesNoStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
