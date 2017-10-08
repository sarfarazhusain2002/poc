package com.oxy.s3m.notification.service.seller;

import com.oxy.s3m.notification.exception.SellerException;
import com.oxy.s3m.notification.model.seller.SellerDetails;



public interface SellerService {
	
	/*Customer findById(long id);
	
	Customer findByName(String name);*/
	
	SellerDetails saveSeller(SellerDetails seller) throws SellerException;
	
	/*void updateUser(Customer user);
	
	void deleteUserById(long id);

	List<Customer> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(Customer user);*/
	
}
