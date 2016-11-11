package com.hmsgtech.constants;

public enum StudioAppUserStatus {

	DEFAULT(0, "默认"),

	SET_INFO(1, "完善资料"),

	SET_AGENCY(2, "选择机构（审核中）"),

	AUDIT_PASS(3, "审核通过"),

	AUDIT_SET_AGENCY_FAIL(4, "审核未通过(选择机构未通过)"),

	ADD_AGENCY(5, "添加机构（机构审核中）"),

	AUDIT_ADD_AGENCY_FAIL(6, "审核未通过（添加机构未通过）"),

	;

	private Integer value;
	private String memo;

	private StudioAppUserStatus(Integer value, String memo) {
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
