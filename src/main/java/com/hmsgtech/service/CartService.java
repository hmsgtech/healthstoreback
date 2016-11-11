package com.hmsgtech.service;

import com.hmsgtech.domain.CartItem;
import com.hmsgtech.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 晓丰 on 2016/5/28.
 */
@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Transactional
    public List<CartItem> getCartItems(String userid){

        return cartRepository.findCartItemsByUserId(userid);
    }

    @Transactional
    public CartItem addToCart(String userid, long goodsId, int num) throws IOException {
        List<CartItem> cartItems = getCartItems(userid);
        for(CartItem orderItem:cartItems){
            if(orderItem.getGoodsId()==goodsId){
                orderItem.setNum(orderItem.getNum()+num);
                return cartRepository.save(orderItem);
            }
        }
        CartItem buyOrderItem = new CartItem();
        buyOrderItem.setNum(num);
        buyOrderItem.setGoodsId(goodsId);
        buyOrderItem.setUserId(userid);
        return        cartRepository.save(buyOrderItem);
    }

	public CartItem updateCartNum(String userid, long goodsId, int num) {
		List<CartItem> cartItems = getCartItems(userid);
        for(CartItem orderItem:cartItems){
            if(orderItem.getGoodsId()==goodsId){
                orderItem.setNum(num);
                return cartRepository.save(orderItem);
            }
        }
        CartItem buyOrderItem = new CartItem();
        buyOrderItem.setNum(num);
        buyOrderItem.setGoodsId(goodsId);
        buyOrderItem.setUserId(userid);
        return        cartRepository.save(buyOrderItem);
	}
}

