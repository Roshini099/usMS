package com.infosys.OMS.UserService.repository;


import org.springframework.data.repository.CrudRepository;

import com.infosys.OMS.UserService.entity.Buyer;

public interface BuyerRepository extends CrudRepository<Buyer, Integer> {

	public Buyer findByPhoneNumber(String phoneNumber);
	public Buyer findByEmail(String email);
}
