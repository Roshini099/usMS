package com.infosys.OMS.UserService.validator;

import com.infosys.OMS.UserService.dto.BuyerDTO;
import com.infosys.OMS.UserService.dto.SellerDTO;
import com.infosys.OMS.UserService.exception.UserMSException;

public class UserValidator {
     public static void validateBuyer(BuyerDTO buyer) throws UserMSException {
		
		if(!validateName(buyer.getName()))
			throw new UserMSException("Validator.ENTER_VALID_NAME");
		
		if(!validateEmail(buyer.getEmail()))
			throw new UserMSException("Validator.ENTER_VALID_EMAIL");
		
		if(!validateContactNumber(buyer.getPhoneNumber()))
			throw new UserMSException("Validator.ENTER_VALID_NUMBER");
		
			
		if(!validatePassword(buyer.getPassword()))
			throw new UserMSException("Validator.Enter_VALID_PASSWORD");
		
	  }
	
	public static void validateSeller(SellerDTO buyer) throws UserMSException {
		
		if(!validateName(buyer.getName()))
			throw new UserMSException("Validator.ENTER_VALID_NAME");
		
		if(!validateEmail(buyer.getEmail()))
			throw new UserMSException("Validator.ENTER_VALID_EMAIL");
		
		if(!validateContactNumber(buyer.getPhoneNumber()))
			throw new UserMSException("Validator.ENTER_VALID_NUMBER");
		
			
		if(!validatePassword(buyer.getPassword()))
			throw new UserMSException("Validator.Enter_VALID_PASSWORD");
		
	}
	
	
	public static boolean validateName(String name)
	{
		
		
		String regex = "[A-Za-z]+([ ][A-Za-z]+)?";
		if(name.matches(regex))
			return true;
		
		return false;
		
	}
	
	public static boolean validateEmail(String email)
	{
		String regex = "[a-z]+@[a-z]+[\\.]com";
		
		if(email.matches(regex))
			return true;
		
		return false;
	}
	
	public static boolean validateContactNumber(String contactNumber)
	{
		
		String regex = "[6-9][0-9]{9}";
		
		if(contactNumber.matches(regex))
			return true;
		
		return false;
	}
	
	public static boolean validatePassword(String password)
	{
		String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{7,20}$";
		
		if(password.matches(regex))
			return true;
		
		return false;
	}	

}
