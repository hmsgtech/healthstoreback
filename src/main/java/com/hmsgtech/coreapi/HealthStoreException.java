package com.hmsgtech.coreapi;

public class HealthStoreException extends RuntimeException {
	private static final long serialVersionUID = 2186957811943999230L;
	private ReturnCode errorCode;

	public ReturnCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ReturnCode errorCode) {
		this.errorCode = errorCode;
	}

	public HealthStoreException(ReturnCode errorCode) {
		super(errorCode.memo());
		this.errorCode = errorCode;
	}
}
