package com.infosys.OMS.UserService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosys.OMS.UserService.dto.BuyerDTO;
import com.infosys.OMS.UserService.dto.CartDTO;
import com.infosys.OMS.UserService.dto.SellerDTO;
import com.infosys.OMS.UserService.dto.WishlistDTO;
import com.infosys.OMS.UserService.entity.Buyer;
import com.infosys.OMS.UserService.entity.Cart;
import com.infosys.OMS.UserService.entity.Seller;
import com.infosys.OMS.UserService.entity.WishList;
import com.infosys.OMS.UserService.exception.UserMSException;
import com.infosys.OMS.UserService.repository.BuyerRepository;
import com.infosys.OMS.UserService.repository.CartRepository;
import com.infosys.OMS.UserService.repository.SellerRepository;
import com.infosys.OMS.UserService.repository.WishListRepository;
import com.infosys.OMS.UserService.validator.UserValidator;


@Service(value="userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private WishListRepository wishlistRepository;
	
	@Autowired
	private CartRepository cartRepository;
	@Override
	public Integer buyerRegistration(BuyerDTO buyerDTO) throws UserMSException {
		// TODO Auto-generated method stub
		
		UserValidator.validateBuyer(buyerDTO);
		
		Buyer buyer = buyerRepository.findByPhoneNumber(buyerDTO.getPhoneNumber());
		
		if(buyer != null)
			throw new UserMSException("Buyer already present");
		
		
		Buyer buy1 = new Buyer();
		buy1.setEmail(buyerDTO.getEmail());
		buy1.setName(buyerDTO.getName());
		buy1.setPhoneNumber(buyerDTO.getPhoneNumber());
		buy1.setPassword(buyerDTO.getPassword());
		buy1.setIsActive("Active");
		buy1.setIsPrivileged("False");
		buy1.setRewardPoints("0");
		
		buyerRepository.save(buy1);
		
		return buy1.getBuyerId();
	}
	@Override
	public String buyerLogin(String email, String password) throws UserMSException {
		// TODO Auto-generated method stub
	
		if(!UserValidator.validateEmail(email))
			throw new UserMSException("Enter valid email id");
		
		Buyer buyer = buyerRepository.findByEmail(email);
		
		if(buyer == null)
			throw new UserMSException("Buyer is not present");
		
		if(!buyer.getPassword().equals(password))
			throw new UserMSException("Invalid Password");
		
			
		buyerRepository.save(buyer);
		
		return "Successfully Logged In";
	}
	@Override
	public Integer sellerRegistration(SellerDTO sellerDTO) throws UserMSException {
		// TODO Auto-generated method stub
		
		UserValidator.validateSeller(sellerDTO);
		
		Seller seller = sellerRepository.findByPhoneNumber(sellerDTO.getPhoneNumber());
		
		if(seller != null)
			throw new UserMSException("Seller Already present");
		
		
		
		Seller seller1 = new Seller();
		
		seller1.setEmail(sellerDTO.getEmail());
		seller1.setName(sellerDTO.getName());
		seller1.setPassword(sellerDTO.getPassword());
		seller1.setIsActive("Active");
		seller1.setPhoneNumber(sellerDTO.getPhoneNumber());
		
		sellerRepository.save(seller1);
		
		return seller1.getSellerId();
	}
	@Override
	public String sellerLogin(String email, String password) throws UserMSException {

		if(!UserValidator.validateEmail(email))
			throw new UserMSException("Enter valid email id");
		
		Seller seller = sellerRepository.findByEmail(email);
		
		if(seller == null)
			throw new UserMSException("Seller is not present");
		
		if(!seller.getPassword().equals(password))
			throw new UserMSException("Invalid Password");
		
			
		sellerRepository.save(seller);
		
		return "Successfully Logged In";
	}
	@Override
	public String deleteBuyer(Integer id) throws UserMSException{
		
		Optional<Buyer> buyer = buyerRepository.findById(id);
		Buyer b=buyer.orElseThrow(()->new UserMSException("Buyer Not Found") );
		b.setIsActive("InActive");
		buyerRepository.delete(b);
		return "Account successfully deleted";
	}

	@Override
	public String deleteSeller(Integer id) throws UserMSException{
		
		Optional<Seller> seller = sellerRepository.findById(id);
		Seller s=seller.orElseThrow(()->new UserMSException("Seller Not Found") );
		s.setIsActive("InActive");
		sellerRepository.delete(s);
		return "Account successfully deleted";
	}
	@Override
	public Integer addProductsToWishList(WishlistDTO wishlist) throws UserMSException {
		
		Optional<Buyer> opBuyer= buyerRepository.findById(wishlist.getBuyerId());
		Buyer b = opBuyer.orElseThrow(()-> new UserMSException("UserService.NO_BUYER"));
		Optional<WishList> op= wishlistRepository.findByBuyerIdAndProductId(wishlist.getBuyerId(), wishlist.getProdId());
		if(op.isPresent()) {
			throw new  UserMSException("UserService.ALREADY_WISHLISTED");
		}
		else {			
			
	        WishList wishl= new WishList();
			wishl.setBuyerId(wishlist.getBuyerId());
			wishl.setProductId(wishlist.getProdId());
			wishlistRepository.save(wishl);
			return wishlist.getProdId();
		}
	}
	@Override
	public void deleteProductFromWishlist(WishlistDTO wishlist) throws UserMSException{
		Optional<Buyer> opBuyer= buyerRepository.findById(wishlist.getBuyerId());
		Buyer b =opBuyer.orElseThrow(()-> new UserMSException("UserService.NO_BUYER"));
        Optional<WishList> optionalCart= wishlistRepository.findByBuyerIdAndProductId(wishlist.getBuyerId(), wishlist.getProdId());
        WishList wish= optionalCart.orElseThrow(()-> new UserMSException("UserService.NO_SUCH_PRODUCTS_ARE_WISHLISTED"));
        wishlistRepository.delete(wish);
	}
	@Override
	public String moveToCart(WishlistDTO wishlist,Integer quantity) throws UserMSException{
		Cart c = new Cart();
		c.setBuyerId(wishlist.getBuyerId());
		c.setProductId(wishlist.getProdId());
		c.setQuantity(quantity);
		cartRepository.save(c);
		deleteProductFromWishlist(wishlist);
		return "Product moved to cart successfully";
	}
	@Override
	public Integer addToCart(CartDTO cartDTO)throws UserMSException {

		Optional<Buyer> opBuyer= buyerRepository.findById(cartDTO.getBuyerId());
		Buyer b= opBuyer.orElseThrow(()-> new UserMSException("UserService.NO_BUYER"));

		Optional<Cart> optional= cartRepository.findByBuyerIdAndProductId(cartDTO.getBuyerId(), cartDTO.getProdId());
		if(optional.isPresent()) {
			throw new  UserMSException("UserService.ALREADY_PRESENT_IN_CART");
		}
        Cart cart= new Cart();
		cart.setBuyerId(cartDTO.getBuyerId());
		cart.setProductId(cartDTO.getProdId());
		cart.setQuantity(cartDTO.getQuantity());
		cartRepository.save(cart);
		return cart.getProductId();
	}
	@Override
	public void removeFromCart(CartDTO cartDTO) throws UserMSException{
		Optional<Buyer> opBuyer= buyerRepository.findById(cartDTO.getBuyerId());
		Buyer b =opBuyer.orElseThrow(()-> new UserMSException("UserService.NO_BUYER"));
        Optional<Cart> optionalCart= cartRepository.findByBuyerIdAndProductId(cartDTO.getBuyerId(), cartDTO.getProdId());
		Cart cart= optionalCart.orElseThrow(()-> new UserMSException("UserService.NO_SUCH_PRODUCTS_ARE_IN_CART"));
		cartRepository.delete(cart);
	}
	@Override
	public List<WishlistDTO> getAllProductsFromWishlist(Integer buyerId) throws UserMSException{
		List<WishList> list=wishlistRepository.findByBuyerId(buyerId);

		List<WishlistDTO> l = new ArrayList<>();
		
		for(WishList w : list)
		{
			WishlistDTO wish=new WishlistDTO();
			
			wish.setBuyerId(w.getBuyerId());
			wish.setProdId(w.getProductId());
			l.add(wish);
		}
		
		return l;
	}
	@Override
	public List<CartDTO> getAllProductsFromCart(Integer buyerId) throws UserMSException {
		
		List<Cart> list=cartRepository.findByBuyerId( buyerId);

		List<CartDTO> l = new ArrayList<>();
		
		for(Cart cart : list)
		{
			CartDTO cartDTO = new CartDTO();
			
			cartDTO.setBuyerId(cart.getBuyerId());
			cartDTO.setProdId(cart.getProductId());
			cartDTO.setQuantity(cart.getQuantity());
			l.add(cartDTO);
		}
		
		return l;
	}
	
}

