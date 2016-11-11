package com.hmsgtech.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

	DEFAULT(1, "默认"),

	ORDER_NOT_PAY(2, "未付款"),

	ORDER_PAY(3, "已付款"),

	ORDER_COMPLETE(4, "已完成-未评价-爱基因检测完成后台修改/美年体检报告完成"),

	ORDER_RATED(6, "已完成-已评价"),

	ORDER_BACKED(5, "已退款"),

	ORDER_CANCEL(9, "用户取消--取消订单"),

	ORDER_REBACKING(8, "待完成-取消订单退款中"),

	ORDER_BACKING(31, "待完成-退款中-用户申请退款"),

	/* 美年体检系列 */
	ORDER_MEINIAN_PAY_SUCCESS(11, "待完成-美年订单-支付成功待预约"),

	ORDER_MEINIAN_RESERVED(12, "待完成-美年订单-已预约-审核中"),
	
	ORDER_MEINIAN_RESERVED_PASS(14, "待完成-美年订单-预约成功-待体检"),
	
	ORDER_MEINIAN_RESERVED_FAIL(15, "待完成-美年订单-预约失败-待重新预约"),

	ORDER_MEINIAN_CHECKING(13, "待完成-美年订单-体检中"),

	/* 爱基因系列 */
	ORDER_AIJIYIN_PAY_SUCCESS(20, "待完成-爱基因-支付成功(待发货)"),

	ORDER_AIJIYIN_WAIT_SAMPL(21, "待完成-爱基因-待收货(发货采样盒)"),

	ORDER_AIJIYIN_SAMPLING(22, "待完成-爱基因-用户采样中"),

	@Deprecated ORDER_AIJIYIN_SAMPLED(23, "待完成-爱基因-用户采样完成后发货"),

	ORDER_AIJIYIN_CHECKING(24, "待完成-爱基因-基因检测中"),

	@Deprecated
	ORDER_AIJIYIN_WAIT_REPORT(25, "待完成-爱基因-检测后发送报告结果-完成"),

	;

	private Integer value;
	private String memo;

	/**
	 * 获得订单状态列表
	 * 
	 * @param status
	 * @return
	 */
	public static List<Integer> getOrderStates(int status) {
		List<Integer> list = new ArrayList<Integer>();
		switch (status) {
		case 1:
			break;
		case 2:
			// 未付款
			list = Arrays.asList(ORDER_NOT_PAY.getValue());
			break;
		case 3:
			// 待完成
			list = Arrays.asList(ORDER_MEINIAN_PAY_SUCCESS.getValue(), ORDER_MEINIAN_RESERVED.getValue(), ORDER_MEINIAN_CHECKING.getValue(), ORDER_AIJIYIN_WAIT_SAMPL.getValue(),
					ORDER_AIJIYIN_SAMPLING.getValue(), ORDER_AIJIYIN_SAMPLED.getValue(), ORDER_AIJIYIN_CHECKING.getValue(), ORDER_AIJIYIN_WAIT_REPORT.getValue());
			break;
		case 4:
			// 已完成
			list = Arrays.asList(ORDER_RATED.getValue(), ORDER_COMPLETE.getValue(), ORDER_BACKING.getValue(), ORDER_REBACKING.getValue());
			break;
		case 5:
			// 已退款
			list = Arrays.asList(ORDER_BACKED.getValue());
			break;
		default:
			break;
		}
		return list;
	}

	private OrderStatus(Integer value, String memo) {
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
