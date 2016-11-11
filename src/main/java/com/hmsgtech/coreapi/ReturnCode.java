package com.hmsgtech.coreapi;

public enum ReturnCode {

	SUCCESS("0", "SUCCESS"),

	ALREADY_SHARE("10001", "今日已经分享过商品"),

	NO_NEW_REPORT("20001", "没有新报告"),

	GOODS_NO_SELL("30001", "该商品不存在或已下架"),

	CONFIRM_RECEIPT_FAIL("30002", "确认收货失败，请检查订单状态"),

	CANCEL_FAIL_MEINIAN("30003", "取消订单失败，订单不可取消"),

	CANCEL_FAIL_AIJIYIN("30004", "取消订单失败，商品已发货不可取消"),

	AGENCY_NAME_REPEAT("40001", "机构名称重复"),

	AGENCY_ADMIN_FAIL("40002", "不是管理员不可操作"),

	AGENCY_AUDIT_FAIL("40003", "当前状态不可审核"),

	AGENCY_NOT_COMMON("40004", "不是同一个机构的成员"),

	PHONE_ALREADY_EXIST("40005", "该手机号已注册"),

	ALEARY_REGISTER_SUCESS("40006", "已注册审核完成"),

	REGISTER_USERINFO_FIRST("40007", "还未完善资料"),

	ADMIN_EXIT("40008", "管理员不可退出"),

	REFRESH_QRCODE_FAIL("40009", "请求二维码失败"),

	SEND_REMIND_FAIL("40010", "消息发送失败"),

	HISTRY_RECORD_ERROR("40011", "历史记录不存在"),

	SEND_REMIND_REPEAT("40012", "刚刚已经发送过了噢"),

	UPDATE_TEMPLET_ERROR("40013", "修改失败"),

	WX_NOT_SUBSCRIBE("40014", "该用户已取消关注伊健康公众号"),

	RESULT_NULL("2001", "查询结果为空"),

	RESULT_EXIST("2002", "结果已经存在"),

	RESOURCE_NULL("2003", "资源不存在"),

	TOKEN_ERROR("2004", "token error"),

	NOT_LOGIN("2005", "not Login"),

	USER_NOT_EXIST("2006", "未知用户"),

	SEND_XCODE_TIME_ERROR("2007", "频繁发送验证码，请一分钟后再试"),

	XCODE_ERROR("2008", "验证码无效"),

	TOKEN_RESET("2009", "修改成功，需重新登陆"),

	REQUEST_ERROR("9001", "参数错误"),

	FAIL("9999", "未知错误"),

	;

	private String value;

	private String memo;

	public String value() {
		return value;
	}

	public String memo() {
		return memo;
	}

	private ReturnCode(String value, String memo) {
		this.value = value;
		this.memo = memo;
	}

	public static ReturnCode getErrorCode(String value) {
		ReturnCode[] values = ReturnCode.values();
		for (ReturnCode error : values) {
			if (error.value.equals(value)) {
				return error;
			}
		}
		return null;
	}

}
