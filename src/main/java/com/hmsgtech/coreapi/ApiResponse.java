package com.hmsgtech.coreapi;

public class ApiResponse {
	private String returnCode;
	private String message;
	private Object data;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ApiResponse build(ReturnCode code, Object data) {
		ApiResponse response = new ApiResponse();
		response.setReturnCode(code.value());
		response.setMessage(code.memo());
		response.setData(data);
		return response;
	}

	public static ApiResponse success(Object data) {
		ApiResponse response = new ApiResponse();
		response.setReturnCode(ReturnCode.SUCCESS.value());
		response.setMessage(ReturnCode.SUCCESS.memo());
		response.setData(data);
		return response;
	}

	public static ApiResponse success() {
		ApiResponse data = new ApiResponse();
		data.setReturnCode(ReturnCode.SUCCESS.value());
		data.setMessage(ReturnCode.SUCCESS.memo());
		return data;
	}

	public static ApiResponse fail(ReturnCode code) {
		ApiResponse data = new ApiResponse();
		data.setReturnCode(code.value());
		data.setMessage(code.memo());
		return data;
	}

	public static ApiResponse defaultFail() {
		ApiResponse data = new ApiResponse();
		data.setReturnCode(ReturnCode.FAIL.value());
		data.setMessage(ReturnCode.FAIL.memo());
		return data;
	}
}
