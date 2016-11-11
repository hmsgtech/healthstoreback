package com.hmsgtech.constants;

public enum OrderReportStatus {

	DEFAULT(0, "未上传/无报告"),

	UPLOAD(1, "已上传"),

	DOWNLOAD(2, "已下载"),

	;

	private Integer value;
	private String memo;

	OrderReportStatus(Integer value, String memo) {
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
