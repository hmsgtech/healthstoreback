package com.hmsgtech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.BuyderComparator;
import com.hmsgtech.constants.CompanyEnum;
import com.hmsgtech.constants.OrderReportStatus;
import com.hmsgtech.constants.OrderStatus;
import com.hmsgtech.coreapi.ApiResponse;
import com.hmsgtech.coreapi.HmsgService;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.domain.goods.Goods;
import com.hmsgtech.domain.order.BuyOrder;
import com.hmsgtech.repository.BuyOrderRepository;
import com.hmsgtech.repository.GoodsRepository;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.service.BuyOrderService;
import com.hmsgtech.service.OrderReportService;
import com.hmsgtech.service.sms.SmsService;
import com.hmsgtech.utils.DateUtil;

/**
 * 新商城订单的处理逻辑：一单对应一个商品
 * 
 * @author lujq
 *
 */
@Controller
public class ShopOrderController {

	@Autowired
	GoodsRepository goodsRepository;

	@Autowired
	BuyOrderRepository buyOrderRepository;

	@Autowired
	OrderReportService orderReportService;

	@Autowired
	HmsgService hmsgService;

	@Autowired
	BuyOrderService buyOrderService;
	@Autowired
	SmsService smsService;
	@Autowired
	StoreRepository storeRepository;

	// 预约；收货；体检完成/检测完成；再次收货
	/**
	 * 下单
	 * 
	 * @param token
	 * @param userid
	 * @param goodsId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("order/addOrder")
	public @ResponseBody ApiResponse addOrder(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("goodsId") long goodsId)
			throws IOException {
		hmsgService.validateUserToken(userid, token);
		// TODO 验证重复提交

		Goods goods = goodsRepository.findOne(goodsId);
		if (goods == null || goods.getIsSell() == 0) {
			return ApiResponse.fail(ReturnCode.GOODS_NO_SELL);
		}
		BuyOrder buyOrder = new BuyOrder();
		String detailes = "[{" + "\"id\":\"" + goods.getId() + "\",\"name\" : \"" + goods.getName() + "\",\"price\":\"" + goods.getPrice() + "\",\"quantity\":\"1\"}]";
		buyOrder.setDetails(detailes);
		buyOrder.setUserid(userid);
		buyOrder.setCreateTime(new Date());
		buyOrder.setPrice(goods.getPrice());
		buyOrder.setStatus(OrderStatus.ORDER_NOT_PAY.getValue());
		buyOrder.setGoodsId(goodsId);
		buyOrder.setGoodsName(goods.getName());
		buyOrder.setCompanyId(goods.getCompanyId());
		buyOrderRepository.save(buyOrder);
		return ApiResponse.success(buyOrder.getId());
	}

	/**
	 * 添加联系人/添加收件人/添加优惠券/添加预约人
	 * 
	 * @param orderid
	 * @param reserveId
	 * @param recipientId
	 * @param money
	 * @param contactId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("order/updateOrderInfo")
	public @ResponseBody ApiResponse updateOrderInfo(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("orderid") long orderid,
			@RequestParam(required = false, value = "recipientId", defaultValue = "0") int recipientId,
			@RequestParam(required = false, value = "monoey", defaultValue = "0") int money, @RequestParam(required = false, value = "contactId", defaultValue = "0") int contactId)
			throws IOException {
		hmsgService.validateUserToken(userid, token);
		BuyOrder order = buyOrderRepository.findOne(orderid);
		if (money > 0) {
			// 优惠券金额
			order.setCoupon(money);
		}
		// TODO 验证订单类型
		if (contactId > 0) {
			// 联系人ID：必须是体检订单
			order.setContactId(contactId);
		}
		if (recipientId > 0) {
			// 收件人ID：必须是爱基因订单
			order.setRecipientId(recipientId);
		}
		buyOrderRepository.save(order);
		return ApiResponse.success();
	}

	/**
	 * 美年预约：设置预约人、预约时间、预约店铺、取消预约
	 * 
	 * @param orderid
	 * @param contactId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("order/setReserve")
	public @ResponseBody ApiResponse setReserve(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("orderid") long orderid,
			@RequestParam("reserveId") int reserveId, @RequestParam(value = "reserveTime", required = false) String reserveTime,
			@RequestParam(value = "storeId", required = false, defaultValue = "0") int storeId) throws IOException {
		hmsgService.validateUserToken(userid, token);
		BuyOrder order = buyOrderRepository.findOne(orderid);
		if (reserveId > 0) {
			order.setStoreId(storeId);
			order.setReserveId(reserveId);
			order.setReserveTime(DateUtil.parse("yyyy-MM-dd", reserveTime));
			order.setStatus(OrderStatus.ORDER_MEINIAN_RESERVED.getValue());
		} else {
			// 取消预约
			order.setStoreId(0);
			order.setReserveId(0);
			order.setReportTime(null);
			order.setStatus(OrderStatus.ORDER_MEINIAN_PAY_SUCCESS.getValue());
		}
		buyOrderRepository.save(order);
		return ApiResponse.success();
	}

	/**
	 * 更新状态：确认收货
	 * 
	 * @param token
	 * @param userid
	 * @param orderid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("order/confirmReceipt")
	public @ResponseBody ApiResponse confirmReceipt(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("orderid") long orderid)
			throws IOException {
		// 验证token
		hmsgService.validateUserToken(userid, token);
		BuyOrder buyOrder = buyOrderRepository.findOne(orderid);
		// 验证状态
		if (buyOrder.getStatus() != OrderStatus.ORDER_AIJIYIN_WAIT_SAMPL.getValue()) {
			return ApiResponse.fail(ReturnCode.CONFIRM_RECEIPT_FAIL);
		}
		buyOrderRepository.updateBuyOrderById(OrderStatus.ORDER_AIJIYIN_SAMPLING.getValue(), orderid);
		return ApiResponse.success();
	}

	/**
	 * 用户结束订单：体检完成/报告完成
	 * 
	 * @param orderid
	 * @return
	 */
	@RequestMapping("order/finish")
	public @ResponseBody ApiResponse orderFinish(@RequestParam("orderid") long orderid) {
		buyOrderRepository.updateBuyOrderById(OrderStatus.ORDER_COMPLETE.getValue(), orderid);
		return ApiResponse.success();
	}

	/**
	 * 更新状态：取消订单
	 * 
	 * @param token
	 * @param userid
	 * @param orderid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("order/cancel")
	public @ResponseBody ApiResponse updateStatus(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("orderid") long orderid)
			throws IOException {
		// 验证token
		hmsgService.validateUserToken(userid, token);
		BuyOrder buyOrder = buyOrderRepository.findOne(orderid);
		// TODO 验证状态
		if (buyOrder.getStatus() == OrderStatus.ORDER_NOT_PAY.getValue()) {
			// 未付款订单直接删除订单
			buyOrderRepository.delete(orderid);
			return ApiResponse.success();
		}
		if (buyOrder.getCompanyId() == CompanyEnum.MEINIAN.getValue()) {
			// 美年订单取消:11，15
			if (OrderStatus.ORDER_MEINIAN_PAY_SUCCESS.getValue() == buyOrder.getStatus() || OrderStatus.ORDER_MEINIAN_RESERVED_FAIL.getValue() == buyOrder.getStatus()) {
				buyOrderRepository.updateBuyOrderById(OrderStatus.ORDER_REBACKING.getValue(), orderid);
				return ApiResponse.success();// 已付款订单6小时内可退款
			} else {
				return ApiResponse.fail(ReturnCode.CANCEL_FAIL_MEINIAN);
			}
		} else if (buyOrder.getCompanyId() == CompanyEnum.AIJIYIN.getValue()) {
			// 爱基因订单取消
			if (buyOrder.getStatus() == OrderStatus.ORDER_AIJIYIN_PAY_SUCCESS.getValue()) {
				buyOrderRepository.updateBuyOrderById(OrderStatus.ORDER_REBACKING.getValue(), orderid);
				return ApiResponse.success();// 已付款订单6小时内可退款
			} else {
				return ApiResponse.fail(ReturnCode.CANCEL_FAIL_AIJIYIN);
			}
		} else {
			// TODO 美容院取消订单
			smsService.sendCancleOrderMessage(buyOrder.getContactId());
			buyOrderRepository.updateBuyOrderById(OrderStatus.ORDER_REBACKING.getValue(), orderid);
			return ApiResponse.success();// 已付款订单6小时内可退款
		}
//		return ApiResponse.defaultFail();
	}

	/**
	 * 查询我的商城订单列表
	 * 
	 * @param token
	 * @param userid
	 * @param states
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("orders/{status}")
	public @ResponseBody List<BuyOrder> getOrders(@RequestParam("token") String token, @RequestParam("userid") String userid, @PathVariable("status") int status)
			throws IOException {
		// 验证token
		hmsgService.validateUserToken(userid, token);
		// 删除未支付的过期订单
		Date date = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
		buyOrderRepository.deleteExpireOrdersBycreateTime(date, OrderStatus.ORDER_NOT_PAY.getValue(), userid);
		// 查询状态中的订单
		List<BuyOrder> buyOrders = new ArrayList<BuyOrder>();
		if (status > 1) {
			buyOrders = buyOrderRepository.findByUseridAndStatusIn(userid, OrderStatus.getOrderStates(status));

		} else {
			buyOrders = buyOrderRepository.findByUserid(userid);
		}
		Collections.sort(buyOrders, new BuyderComparator());
		return buyOrders;
	}

	/**
	 * 单个订单查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("order/{id}")
	public @ResponseBody BuyOrder getOrder(@PathVariable("id") long id) {
		BuyOrder buyOrder = buyOrderRepository.findOne(id);
		if (buyOrder.getStoreId() > 0)
			buyOrder.setStoreName(storeRepository.findOne(buyOrder.getStoreId()).getName());
		return buyOrder;
	}

	// 查询新报告
	@RequestMapping("report/newReport")
	public @ResponseBody ApiResponse newReport(@RequestParam("userid") String userid) throws IOException {
		// 新报告
		if (buyOrderRepository.countByUseridAndReportStatus(userid, OrderReportStatus.UPLOAD.getValue()) > 0)
			return ApiResponse.success();
		else
			return ApiResponse.fail(ReturnCode.NO_NEW_REPORT);
	}

	// 下载新报告
	@RequestMapping("report/download")
	public @ResponseBody ApiResponse reportDownload(@RequestParam("orderid") long orderid) throws IOException {
		BuyOrder buyOrder = buyOrderRepository.findOne(orderid);
		if (buyOrder == null || buyOrder.getReportStatus() == OrderReportStatus.DEFAULT.getValue()) {
			return ApiResponse.fail(ReturnCode.RESOURCE_NULL);
		}
		buyOrder.setReportStatus(OrderReportStatus.DOWNLOAD.getValue());
		buyOrderRepository.save(buyOrder);
		return ApiResponse.success();
	}

	// 查询所有报告
	@RequestMapping("report/reports")
	public @ResponseBody List<BuyOrder> reports(@RequestParam("userid") String userid) throws Exception {
		return orderReportService.getReports(userid);
	}
}
