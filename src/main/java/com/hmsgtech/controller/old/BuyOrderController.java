package com.hmsgtech.controller.old;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsgtech.BuyderComparator;
import com.hmsgtech.coreapi.HmsgService;
import com.hmsgtech.domain.CartItem;
import com.hmsgtech.domain.goods.Goods;
import com.hmsgtech.domain.order.BuyOrder;
import com.hmsgtech.domain.store.Company;
import com.hmsgtech.domain.user.Contact;
import com.hmsgtech.exception.TokenInvalidException;
import com.hmsgtech.repository.BuyOrderRepository;
import com.hmsgtech.repository.CartItemRepository;
import com.hmsgtech.repository.CartRepository;
import com.hmsgtech.repository.CompanyRepository;
import com.hmsgtech.repository.ContactRepository;
import com.hmsgtech.repository.GoodsRepository;
import com.hmsgtech.service.BuyOrderService;
import com.hmsgtech.service.CartService;
import com.hmsgtech.web.model.BuyOrderItemModel;
import com.hmsgtech.web.model.CartItemModel;
import com.hmsgtech.web.model.CartModel;
import com.hmsgtech.web.model.CompanyModel;
import com.hmsgtech.web.model.GoodsRankModel;

import ch.qos.logback.classic.Logger;

/**
 * Created by 晓丰 on 2016/5/10.
 */
@Controller
public class BuyOrderController {

	@Autowired
	BuyOrderRepository buyOrderRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	GoodsRepository goodsRepository;

	@Autowired
	CartService cartService;

	@Autowired
	HmsgService hmsgService;

	@Autowired
	BuyOrderService buyOrderService;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@RequestMapping("listOrders/{status}")
	public @ResponseBody List<BuyOrder> getOrders(@RequestParam("token") String token, @RequestParam("userid") String userid, @PathVariable("status") Integer status)
			throws IOException {
		hmsgService.getUserByToken(token);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
		buyOrderRepository.deleteExpireOrdersBycreateTime(date, 2, userid);

		List<BuyOrder> buyOrders = new ArrayList<BuyOrder>();
		if (status != 1) {
			if (status == 4) {
				buyOrders = buyOrderRepository.findBuyOrderByStatusAndUserid(4, 6, userid);
			} else {
				buyOrders = buyOrderRepository.findBuyOrderByStatusAndUserid(status, userid);
			}
		} else {
			buyOrders = buyOrderRepository.findByUserid(userid);
		}

		Collections.sort(buyOrders, new BuyderComparator());
		return buyOrders;
	}

	@RequestMapping("createOrder")
	public @ResponseBody BuyOrder createOrder(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("detail") String detail,
			@RequestParam("price") int price, @RequestParam("ids") String ids) throws IOException {
		String userByToken = hmsgService.getUserByToken(token);
		String[] split = ids.split(",");
		List<Long> goodsIds = new ArrayList<>();
		for (String s : split) {
			goodsIds.add(Long.parseLong(s));
		}
		BuyOrder buyOrder = new BuyOrder();
		buyOrder.setUserid(userid);
		buyOrder.setCreateTime(new Date());
		buyOrder.setDetails(detail);
		buyOrder.setPrice(price);
		buyOrder.setStatus(2);
		BuyOrder buyOrderNew = buyOrderService.createOrder(buyOrder);
		cartItemRepository.deleteCartItemByIds(userid, goodsIds);
		return buyOrderNew;
	}

	@RequestMapping("createOrdernow")
	public @ResponseBody BuyOrder createOrdernow(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("goodsId") long goodsId)
			throws IOException {
		String userByToken = hmsgService.getUserByToken(token);

		BuyOrder buyOrder = new BuyOrder();
		buyOrder.setUserid(userid);
		buyOrder.setCreateTime(new Date());
		Goods goods = goodsRepository.findOne(goodsId);
		String detailes = "[{" + "\"id\":\"" + goods.getId() + "\",\"name\" : \"" + goods.getName() + "\",\"price\":\"" + goods.getPrice() + "\",\"quantity\":\"1\"}]";
		buyOrder.setDetails(detailes);
		buyOrder.setPrice(goods.getPrice());
		buyOrder.setStatus(2);
		return buyOrderService.createOrder(buyOrder);
	}

	@RequestMapping("getOrderDetails")
	public @ResponseBody List<BuyOrderItemModel> getOrderDetails(@RequestParam("orderId") long orderId) throws JsonParseException, JsonMappingException, IOException {
		BuyOrder order = buyOrderRepository.findOne(orderId);
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType goodsArraylist = objectMapper.getTypeFactory().constructParametrizedType(ArrayList.class, ArrayList.class, BuyOrderItemModel.class);
		return objectMapper.readValue(order.getDetails(), goodsArraylist);
	}

	@RequestMapping("payOrder")
	public @ResponseBody String payOrder(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("orderId") String orderId,
			@RequestParam("price") int price, @RequestParam(required = false, value = "couponId", defaultValue = "") String couponid) throws IOException {
		return hmsgService.payOrder(token, userid, orderId, price, couponid);
	}

	@RequestMapping("addToCart")
	public @ResponseBody CartModel addToCart(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("goodsId") long goodsId,
			@RequestParam("num") int num) throws IOException {
		cartService.addToCart(userid, goodsId, num);
		return getCart(userid, token);
	}

	@RequestMapping("updateCartNum")
	public @ResponseBody List<CompanyModel> updateCartNum(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("goodsId") long goodsId,
			@RequestParam("num") int num) throws IOException {
		cartService.updateCartNum(userid, goodsId, num);
		return bjCart(userid);
	}

	@RequestMapping("cart/{userid}")
	public @ResponseBody CartModel getCart(@PathVariable("userid") String userid, @RequestParam("token") String token) {
		CartModel cartModel = new CartModel();
		cartModel.setUserid(userid);
		List<CartItem> cartItemsByUserId = cartRepository.findCartItemsByUserId(userid);
		List<CartItemModel> itemModels = new ArrayList<>();
		int totalCount = 0;
		for (CartItem cartItem : cartItemsByUserId) {
			CartItemModel cartItemModel = new CartItemModel();
			cartItemModel.setNum(cartItem.getNum());
			cartItemModel.setGoods(goodsRepository.findOne(cartItem.getGoodsId()));
			cartItemModel.setCompany(companyRepository.findOne(goodsRepository.findOne(cartItem.getGoodsId()).getCompanyId()));
			itemModels.add(cartItemModel);
			totalCount += cartItem.getNum();
		}
		cartModel.setCount(totalCount);
		cartModel.setItems(itemModels);

		return cartModel;
	}

	@RequestMapping("bjcart/{userid}")
	public @ResponseBody List<CompanyModel> getbjCart(@PathVariable("userid") String userid, @RequestParam("token") String token) {
		return bjCart(userid);
	}

	private List<CompanyModel> bjCart(String userid) {
		CartModel cartModel = new CartModel();
		cartModel.setUserid(userid);
		List<CartItem> cartItemsByUserId = cartRepository.findCartItemsByUserId(userid);
		if (cartItemsByUserId.size() != 0) {
			List<CartItemModel> itemModels = new ArrayList<>();
			Set<Integer> companyIds = new HashSet<>();
			for (CartItem cartItem : cartItemsByUserId) {
				CartItemModel cartItemModel = new CartItemModel();
				cartItemModel.setNum(cartItem.getNum());
				cartItemModel.setGoods(goodsRepository.findOne(cartItem.getGoodsId()));
				cartItemModel.setCompany(companyRepository.findOne(cartItemModel.getGoods().getCompanyId()));
				itemModels.add(cartItemModel);
				companyIds.add(cartItemModel.getCompany().getId());
			}
			// 查询购物车中商品的所有商户
			List<CompanyModel> companyModelList = new ArrayList<>();
			List<Company> companyList = companyRepository.findCompanyByIds(companyIds);
			if (companyList.size() != 0 && companyList != null) {
				for (Company company : companyList) {
					List<CartItemModel> cartItemModelList = new ArrayList<>();
					CompanyModel companyModel = new CompanyModel();
					companyModel.setId(company.getId());
					companyModel.setName(company.getName());

					Iterator<CartItemModel> its = itemModels.iterator();
					while (its.hasNext()) {
						CartItemModel cartItem = its.next();
						if (cartItem.getCompany().getId() == company.getId()) {
							cartItemModelList.add(cartItem);
							its.remove();
						}
					}
					companyModel.setCartItems(cartItemModelList);
					companyModelList.add(companyModel);
				}
			}
			return companyModelList;
		} else {
			return null;
		}
	}

	@RequestMapping("updateGwc")
	public @ResponseBody String updateGwc(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("ids") String ids)
			throws JsonParseException, JsonMappingException, IOException {
		String status = "{\"status\":\"fail\"}";
		try {
			String[] split = ids.split(",");
			List<Long> goodsIds = new ArrayList<>();
			for (String s : split) {
				goodsIds.add(Long.parseLong(s));
			}
			cartItemRepository.deleteCartItemByIds(userid, goodsIds);
			status = "{\"status\":\"success\"}";
			return status;
		} catch (NumberFormatException e) {
			return status;
		}
	}

	@RequestMapping("orderFinish")
	public @ResponseBody String orderFinish(@RequestParam("orderid") long orderid, int status) {
		buyOrderRepository.updateBuyOrderById(status, orderid);
		return "{\"status\":\"success\"}";
	}

	@RequestMapping("addCoupon")
	public @ResponseBody String addCoupon(@RequestParam("orderid") long orderid, @RequestParam("monoey") int money) {
		buyOrderRepository.updateCouponOrdersById(money, orderid);
		return "{\"status\":\"success\"}";
	}

	@RequestMapping("updateGoods")
	public @ResponseBody String updateGoods(@RequestParam("orderid") long orderid) throws JsonParseException, JsonMappingException, IOException {
		// 更改商品数量
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType goodsArraylist = objectMapper.getTypeFactory().constructParametrizedType(ArrayList.class, ArrayList.class, GoodsRankModel.class);
		BuyOrder store = buyOrderRepository.findOne(orderid);
		List<GoodsRankModel> goodsRankModels = objectMapper.readValue(store.getDetails(), goodsArraylist);
		for (GoodsRankModel goodsRankModel : goodsRankModels) {
			long goodsid = goodsRankModel.getId();
			int num = goodsRankModel.getQuantity();
			goodsRepository.updateGoodsById(num, goodsid);
		}

		return "{\"status\":\"success\"}";
	}

	@RequestMapping("delorder")
	public @ResponseBody String delOrder(@RequestParam("orderid") long orderid, @RequestParam("status") int status) {
		BuyOrder order = buyOrderRepository.findOne(orderid);
		// 已付款订单删除订单
		if (status == 3) {
			if (System.currentTimeMillis() - order.getCreateTime().getTime() < 6 * 1000 * 60) {
				buyOrderRepository.updateBuyOrderById(5, orderid);
				return "{\"status\":\"success1\"}";// 已付款订单6小时内可退款
			} else {
				return "{\"status\":\"fail\"}";// 已付款订单>6小时内不可退款
			}
			// 未付款订单直接删除订单
		} else {
			buyOrderRepository.delete(orderid);
			return "{\"status\":\"success\"}";
		}
	}

	@RequestMapping("rebuy")
	public @ResponseBody BuyOrder rebuy(@RequestParam("orderid") long orderid) throws Exception {
		BuyOrder order = buyOrderRepository.findOne(orderid);
		BuyOrder buyOrder = new BuyOrder();
		buyOrder.setUserid(order.getUserid());
		buyOrder.setCreateTime(new Date());
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType goodsArraylist = objectMapper.getTypeFactory().constructParametrizedType(ArrayList.class, ArrayList.class, BuyOrderItemModel.class);
		List<BuyOrderItemModel> buyOrderItems = objectMapper.readValue(order.getDetails(), goodsArraylist);
		String jsonStr = "[";
		int totalPrice = 0;
		for (BuyOrderItemModel buyOrderItem : buyOrderItems) {
			Goods goods = goodsRepository.findOne(buyOrderItem.getId());
			if (goods != null) {
				buyOrderItem.setPrice(goods.getPrice());
				buyOrderItem.setName(goods.getName());
				jsonStr += "{\"" + "id" + "\"" + ":" + "\"" + buyOrderItem.getId() + "\",";
				jsonStr += "\"" + "name" + "\"" + ":" + "\"" + buyOrderItem.getName() + "\",";
				jsonStr += "\"" + "price" + "\"" + ":" + "\"" + buyOrderItem.getPrice() + "\",";
				jsonStr += "\"" + "quantity" + "\"" + ":" + "\"" + buyOrderItem.getQuantity() + "\"},";
				totalPrice += buyOrderItem.getPrice() * buyOrderItem.getQuantity();
			}
		}
		jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		jsonStr += "]";
		buyOrder.setDetails(jsonStr);
		buyOrder.setPrice(totalPrice);
		buyOrder.setStatus(2);
		return buyOrderService.createOrder(buyOrder);
	}

	@RequestMapping("changeContact")
	public @ResponseBody BuyOrder changeContact(@RequestParam("orderid") long orderid, @RequestParam("contactId") int contactId) {
		BuyOrder order = buyOrderRepository.findOne(orderid);
		order.setContactId(contactId);
		return buyOrderService.createOrder(order);
	}

	@ExceptionHandler(TokenInvalidException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public String invalidToken(TokenInvalidException exception) {
		return exception.getMessage();
	}
}
