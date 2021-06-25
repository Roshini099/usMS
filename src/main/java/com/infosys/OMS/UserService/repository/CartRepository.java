package com.infosys.OMS.UserService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.infosys.OMS.UserService.entity.Cart;
import com.infosys.OMS.UserService.entity.WishListId;

public interface CartRepository extends CrudRepository<Cart, WishListId>{
	public Optional<Cart> findByBuyerIdAndProductId(Integer buyerId, Integer prodId);
	List<Cart> findByBuyerId(Integer buyerId);
}
