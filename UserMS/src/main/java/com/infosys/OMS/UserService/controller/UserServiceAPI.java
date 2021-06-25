package com.infosys.OMS.UserService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.infosys.OMS.UserService.dto.BuyerDTO;
import com.infosys.OMS.UserService.dto.CartDTO;
import com.infosys.OMS.UserService.dto.ProductDTO;
//import com.infosys.OMS.UserService.dto.ProductDTO;
import com.infosys.OMS.UserService.dto.SellerDTO;
import com.infosys.OMS.UserService.dto.WishlistDTO;
import com.infosys.OMS.UserService.exception.UserMSException;
import com.infosys.OMS.UserService.service.UserService;

@RestController
@CrossOrigin
public class UserServiceAPI {

	@Autowired
	private UserService userService;
	
	@Autowired
	Environment environment;
	
	@Autowired
	DiscoveryClient client;
	
	@Value("${product.uri}")
	String prodUri;
	
	@PostMapping(value = "/userMS/buyer/register")
	public ResponseEntity<String> registerBuyer(@RequestBody BuyerDTO buyerDto) throws UserMSException{
		  String s ="Buyer registered successfully with buyer Id :"+ userService.buyerRegistration(buyerDto);
		  return new ResponseEntity<>(s,HttpStatus.OK);
	}
	
	@PostMapping(value = "/userMS/buyer/login/{email}/{password}")
	public ResponseEntity<String> loginBuyer(@PathVariable String email, @PathVariable String password) throws UserMSException
	{
		
			String msg = userService.buyerLogin(email, password);
			return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	@PostMapping(value = "/userMS/seller/register")
	public ResponseEntity<String> registerSeller(@RequestBody SellerDTO sellerDto) throws UserMSException{
		
		
		String s ="Seller registered successfully with seller Id : "+ userService.sellerRegistration(sellerDto);
		return new ResponseEntity<>(s,HttpStatus.OK);
		

	}
	@PostMapping(value = "/userMS/seller/login/{email}/{password}")
	public ResponseEntity<String> loginSeller(@PathVariable String email, @PathVariable String password) throws UserMSException
	{
			String msg = userService.sellerLogin(email, password);
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		
	}
	@DeleteMapping(value = "/userMS/buyer/deactivate/{id}")
	public ResponseEntity<String> deleteBuyerAccount(@PathVariable Integer id) throws UserMSException{
		
		String msg = userService.deleteBuyer(id);
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
		
		
	}
	
	@DeleteMapping(value = "/userMS/seller/deactivate/{id}")
	public ResponseEntity<String> deleteSellerAccount(@PathVariable Integer id) throws UserMSException{
		
		String msg = userService.deleteSeller(id);
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	@PostMapping(value = "/userMS/buyer/wishlist/add")
	public ResponseEntity<String> addProductToWishlist(@RequestBody WishlistDTO wishlistDto) throws UserMSException
	{
		String msg = "Added to wishlist successfully with ProductID :"+userService.addProductsToWishList(wishlistDto);
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	@DeleteMapping(value = "/userMS/buyer/wishlist/remove")
	public ResponseEntity<String> removeFromWishlist(@RequestBody WishlistDTO wishlistDto) throws UserMSException
	{
		userService.deleteProductFromWishlist(wishlistDto);
		String msg="Product removed from wishlist successfully";
		return new ResponseEntity<>(msg,HttpStatus.OK);
		
	}
	@PostMapping(value="/wishlist/move/{quantity}")
	public ResponseEntity<String> moveToCart(@RequestBody WishlistDTO wishlistDTO,@PathVariable Integer quantity) throws UserMSException{
		String msg=userService.moveToCart(wishlistDTO, quantity);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	@PostMapping(value = "/userMS/buyer/cart/add")
	public ResponseEntity<String> addProductToCart(@RequestBody CartDTO cartDTO) throws UserMSException
	{
		String msg = "Added to Cart successfully with prod id: "+userService.addToCart(cartDTO);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	@DeleteMapping(value = "/userMS/buyer/cart/remove")
	public ResponseEntity<String> removeFromCart(@RequestBody CartDTO cartDTO) throws UserMSException
	{
		userService.removeFromCart(cartDTO);
		String msg="Product removed from cart successfully";
		return new ResponseEntity<>(msg,HttpStatus.OK);
		
	}
	@GetMapping(value = "/userMS/buyer/wishlist/get/{buyerId}")
	public ResponseEntity<List<ProductDTO>> getProductsFromWishlist(@PathVariable Integer buyerId) throws UserMSException
	{
		List<ProductDTO> prodList = new ArrayList<>();
		List<WishlistDTO> list = userService.getAllProductsFromWishlist(buyerId);
		for(WishlistDTO w: list) {
			ProductDTO productDTO = new RestTemplate().getForObject(prodUri+"/prodMS/getById/"+ w.getProdId(), ProductDTO.class);
			prodList.add(productDTO);
		}
		return new ResponseEntity<>(prodList,HttpStatus.OK);
		}
	@GetMapping(value = "/userMS/cart/get/{buyerId}")
	public ResponseEntity<List<CartDTO>> getProductListFromCart(@PathVariable Integer buyerId) throws UserMSException
	{
		
		//ProductDTO product = new RestTemplate().getForObject(prodUri+"/prodMS/getById/"+, ProductDTO.class);
		
		List<CartDTO> list = userService.getAllProductsFromCart(buyerId);
		
		return new ResponseEntity<>(list,HttpStatus.OK);
		}
		
}
