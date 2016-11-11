package com.hmsgtech.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hmsgtech.constants.CompanyEnum;
import com.hmsgtech.constants.OrderStatus;
import com.hmsgtech.coreapi.ApiResponse;
import com.hmsgtech.coreapi.HmsgResponse;
import com.hmsgtech.coreapi.HmsgService;
import com.hmsgtech.coreapi.HmsgUserInfo;
import com.hmsgtech.domain.order.BuyOrder;
import com.hmsgtech.repository.BuyOrderRepository;
import com.hmsgtech.service.sms.SmsService;
import com.hmsgtech.utils.RandomUtil;

/**
 * 小棉袄
 * 
 * @author lujq
 *
 */
@Controller
public class HmsgController {
	@Autowired
	BuyOrderRepository buyOrderRepository;
	@Autowired
	HmsgService hmsgService;
	@Autowired
	SmsService smsService;

	@RequestMapping("getCoupons")
	public @ResponseBody HmsgResponse getCoupons(@RequestParam("token") String token, @RequestParam("userid") String userid,
			@RequestParam(required = false, value = "amount", defaultValue = "0") long amount) throws IOException {
		return hmsgService.getCoupons(token, userid, amount);
	}

	@RequestMapping("getCouponCount")
	public @ResponseBody String getCouponCount(@RequestParam("token") String token, @RequestParam("userid") String userid,
			@RequestParam(required = false, value = "amount", defaultValue = "0") long amount) throws IOException {
		return hmsgService.getCouponCount(token, userid, amount);
	}

	@RequestMapping("getUserInfo")
	public @ResponseBody HmsgUserInfo getUserInfo(@RequestParam("userid") String userid) {
		HmsgUserInfo result;
		try {
			result = hmsgService.getUserInfoByUserId(userid);
		} catch (Exception e) {
			e.printStackTrace();
			result = new HmsgUserInfo();
		}
		return result;
	}

	@RequestMapping("orderSuccess")
	public @ResponseBody ApiResponse orderSuccess(@RequestParam("orderid") long orderid) throws JsonParseException, JsonMappingException, IOException {
		BuyOrder buyOrder = buyOrderRepository.findOne(orderid);
		if (buyOrder.getCompanyId() == CompanyEnum.MEINIAN.getValue()) {
			// 美年订单
			buyOrderRepository.updateBuyOrderById(OrderStatus.ORDER_MEINIAN_PAY_SUCCESS.getValue(), orderid);
		} else if (buyOrder.getCompanyId() == CompanyEnum.AIJIYIN.getValue()) {
			// 爱基因订单
			buyOrderRepository.updateBuyOrderById(OrderStatus.ORDER_AIJIYIN_PAY_SUCCESS.getValue(), orderid);
		} else {
			// 美容院订单
			String code = RandomUtil.createActivationCode(buyOrder.getId());
			// TODO 查询联系人ID，并给该联系人发短信
			smsService.sendActivationCodeMessage(buyOrder.getContactId(), code);
			buyOrderRepository.updateBuyOrderById(orderid, code, OrderStatus.ORDER_PAY.getValue());// 更新激活码
		}
		return ApiResponse.success();
	}

	@RequestMapping("orderFail")
	public @ResponseBody ApiResponse orderFail(@RequestParam("orderid") long orderid) {
		BuyOrder buyOrder = buyOrderRepository.findOne(orderid);
		buyOrder.setStatus(2);
		buyOrderRepository.save(buyOrder);
		return ApiResponse.success();
	}
}
