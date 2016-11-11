package com.hmsgtech.web.model;

import com.hmsgtech.domain.goods.Goods;
import com.hmsgtech.domain.store.Company;

/**
 * Created by 晓丰 on 2016/5/18.
 */
public class CartItemModel {
    private Goods goods;
    private int num;
    private int price;
    private Company company;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
    
    
}
