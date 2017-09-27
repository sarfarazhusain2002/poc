package com.oxy.s3m.notification.service.customer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oxy.s3m.notification.DAO.customer.CustomerDetailsDAO;
import com.oxy.s3m.notification.beans.customer.Notification;
import com.oxy.s3m.notification.beans.customer.ReqGetNotification;
import com.oxy.s3m.notification.exception.CustomerException;
import com.oxy.s3m.notification.model.customer.CustomerDetails;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
	@Autowired
    CustomerDetailsDAO custDetailsDAO;
	
	

	private static final AtomicLong counter = new AtomicLong();
	
	

	
	
	public CustomerDetails saveCustomer(CustomerDetails customer) throws CustomerException {
		//user.setId(counter.incrementAndGet());
		return custDetailsDAO.persist(customer);
		//customers.add(user);
	}





	@Override
	public void updateUser(CustomerDetails cust) {
		custDetailsDAO.updateCustomer(cust);
		
	}





	@Override
	public List<Notification> getNotifications(ReqGetNotification notification) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
