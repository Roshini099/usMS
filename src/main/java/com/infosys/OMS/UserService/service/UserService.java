package com.infosys.OMS.UserService.service;

import java.util.List;

import com.infosys.OMS.UserService.dto.BuyerDTO;
import com.infosys.OMS.UserService.dto.CartDTO;
import com.infosys.OMS.UserService.dto.SellerDTO;
import com.infosys.OMS.UserService.dto.WishlistDTO;
import com.infosys.OMS.UserService.exception.UserMSException;

public interface UserService {
    
	public Integer buyerRegistration(BuyerDTO buyerDTO) throws UserMSException;
	public String buyerLogin(String email, String password) throws UserMSException;
	public Integer sellerRegistration(SellerDTO sellerDTO) throws UserMSException;
	public String sellerLogin(String email, String password) throws UserMSException;
	public String deleteBuyer(Integer id) throws UserMSException;
	public String deleteSeller(Integer id) throws UserMSException;
	public Integer addProductsToWishList(WishlistDTO wishlist) throws UserMSException;
	public void deleteProductFromWishlist(WishlistDTO wishlist) throws UserMSException;
	public String moveToCart(WishlistDTO wishlist,Integer quantity) throws UserMSException;
	public Integer addToCart(CartDTO cartDTO)throws UserMSException;
	public void removeFromCart(CartDTO cartDTO) throws UserMSException;
	public List<WishlistDTO> getAllProductsFromWishlist(Integer buyerId) throws UserMSException;
	public List<CartDTO> getAllProductsFromCart(Integer buyerId) throws UserMSException;
}
