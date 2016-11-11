package com.hmsgtech.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmsgtech.domain.order.BuyOrder;
import com.hmsgtech.repository.BuyOrderRepository;

/**
 * Created by 晓丰 on 2016/6/14.
 */
@Service
public class BuyOrderService {

	@Autowired
	BuyOrderRepository buyOrderRepository;

	public BuyOrder createOrder(BuyOrder buyOrder) {
		return buyOrderRepository.save(buyOrder);
	}



}
