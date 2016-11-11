package com.hmsgtech.constants;

public enum CompanyEnum {

	MEINIAN(1, "美年"),

	AIJIYIN(13, "爱基因"),
	
	MEIRONG(14, "生活美容"),

	;

	private Integer value;
	private String memo;

	CompanyEnum(Integer value, String memo) {
		this.value = value;
		this.memo = memo;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
