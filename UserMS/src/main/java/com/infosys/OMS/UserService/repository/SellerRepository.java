package com.infosys.OMS.UserService.repository;

import org.springframework.data.repository.CrudRepository;

import com.infosys.OMS.UserService.entity.Seller;

public interface SellerRepository extends CrudRepository<Seller, Integer>{
    public Seller findByPhoneNumber(String phoneNumber);
	public Seller findByEmail(String email);
} 
