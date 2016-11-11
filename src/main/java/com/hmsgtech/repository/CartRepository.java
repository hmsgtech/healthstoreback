package com.hmsgtech.repository;

import com.hmsgtech.domain.CartItem;
import com.hmsgtech.domain.GoodsCategoryRelationship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 晓丰 on 2016/4/2.
 */
public interface CartRepository extends CrudRepository<CartItem, Long> {

    public List<CartItem> findCartItemsByUserId(String userid);

}
