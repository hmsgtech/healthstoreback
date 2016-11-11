package com.hmsgtech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JavaType;
import com.hmsgtech.constants.OrderReportStatus;
import com.hmsgtech.domain.order.BuyOrder;
import com.hmsgtech.repository.BuyOrderRepository;
import com.hmsgtech.repository.ContactRepository;
import com.hmsgtech.repository.RecipientRepository;
import com.hmsgtech.utils.JsonUtil;
import com.hmsgtech.utils.StringsUtil;
import com.hmsgtech.web.model.BuyOrderItemModel;

@Service
public class OrderReportService {

	@Autowired
	BuyOrderRepository buyOrderRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	RecipientRepository recipientRepository;

	public List<BuyOrder> getReports(@RequestParam("userid") String userid) throws Exception {
		List<BuyOrder> reports = buyOrderRepository.findBuyOrderByUseridAndReportStatusNot(userid, OrderReportStatus.DEFAULT.getValue());
		for (BuyOrder buyOrder : reports) {
			setReportName(buyOrder);
//			if (StringsUtil.isEmpty(buyOrder.getGoodsName())) {
//				setGoodsName(buyOrder);
//			}
		}
		return reports;
	}

//	private void setGoodsName(BuyOrder buyOrder) throws Exception {
//		JavaType goodsArraylist = JsonUtil.getObjectMapper().getTypeFactory().constructParametrizedType(ArrayList.class, ArrayList.class, BuyOrderItemModel.class);
//		List<BuyOrderItemModel> buyOrderItems = JsonUtil.getObjectMapper().readValue(buyOrder.getDetails(), goodsArraylist);
//		buyOrder.setGoodsName(buyOrderItems.get(0).getName());
//	}

	private void setReportName(BuyOrder buyOrder) {
		StringBuilder reportName = new StringBuilder();
		if (buyOrder.getContactId() > 0) {
			reportName.append(contactRepository.findOne(buyOrder.getContactId()).getName());
			reportName.append("的体检报告");
		} else if (buyOrder.getRecipientId() > 0) {
			reportName.append(recipientRepository.findOne(buyOrder.getRecipientId()).getName());
			reportName.append("的检测报告");
		} else {
			reportName.append("未知用户的报告");
		}
		buyOrder.setReportName(reportName.toString());
	}
}
