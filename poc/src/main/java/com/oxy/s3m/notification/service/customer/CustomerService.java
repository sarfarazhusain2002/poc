package com.oxy.s3m.notification.service.customer;

import java.util.List;

import com.oxy.s3m.notification.beans.customer.Notification;
import com.oxy.s3m.notification.beans.customer.ReqGetNotification;
import com.oxy.s3m.notification.exception.CustomerException;
import com.oxy.s3m.notification.model.customer.CustomerDetails;



public interface CustomerService {
	
	/*Customer findById(long id);
	
	Customer findByName(String name);*/
	
	CustomerDetails saveCustomer(CustomerDetails cust) throws CustomerException;
	
	void updateUser(CustomerDetails cust);
	
	List<Notification> getNotifications(ReqGetNotification notification);
	
	/*void deleteUserById(long id);

	List<Customer> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(Customer user);*/
	
}
