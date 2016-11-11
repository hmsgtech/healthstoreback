package com.hmsgtech.web.model;

import java.util.List;

/**
 * Created by 晓丰 on 2016/5/18.
 */
public class CartModel {
    private String userid;
    private List<CartItemModel> items;
    private int count;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<CartItemModel> getItems() {
        return items;
    }

    public void setItems(List<CartItemModel> items) {
        this.items = items;
    }

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
    
    
}
