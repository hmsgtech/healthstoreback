package com.hmsgtech.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.CartItem;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

	@Modifying
	@Transactional
	@Query("delete from CartItem where userId = ?1 and goodsId in(?2)")
	void deleteCartItemByIds(String userid, List<Long> cartItemIds);

	
}
