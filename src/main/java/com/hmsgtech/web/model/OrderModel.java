package com.hmsgtech.web.model;

import com.hmsgtech.domain.order.BuyOrder;
import com.hmsgtech.domain.user.Contact;

public class OrderModel {
	private BuyOrder buyOrder;
	private Contact contact;

	public BuyOrder getBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(BuyOrder buyOrder) {
		this.buyOrder = buyOrder;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}
