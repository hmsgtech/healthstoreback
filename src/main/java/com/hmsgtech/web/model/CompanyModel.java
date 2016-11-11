package com.hmsgtech.web.model;

import java.util.List;

import com.hmsgtech.domain.goods.Goods;
import com.hmsgtech.domain.store.Company;

public class CompanyModel extends Company {
	private List<CartItemModel> cartItems;

	public List<CartItemModel> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemModel> cartItems) {
		this.cartItems = cartItems;
	}
	
	 
}
