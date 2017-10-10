package com.oxy.s3m.notification.service.seller;

import com.oxy.s3m.notification.beans.seller.SellerDetailsBean;
import com.oxy.s3m.notification.exception.SellerException;
import com.oxy.s3m.notification.model.seller.Notification;
import com.oxy.s3m.notification.model.seller.SellerDetails;



public interface SellerService {
	
	/*Customer findById(long id);
	
	Customer findByName(String name);*/
	
	SellerDetails saveSeller(SellerDetails seller) throws SellerException;
	
	SellerDetailsBean sendNotification(Notification notification) throws SellerException;
	
	/*void deleteUserById(long id);

	List<Customer> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(Customer user);*/
	
}
