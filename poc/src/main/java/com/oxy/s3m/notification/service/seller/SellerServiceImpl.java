package com.oxy.s3m.notification.service.seller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxy.s3m.notification.DAO.seller.SellerDetailsDAOImpl;
import com.oxy.s3m.notification.exception.SellerException;
import com.oxy.s3m.notification.model.seller.SellerDetails;

@Service
public class SellerServiceImpl implements SellerService {
	
	@Autowired
	private SellerDetailsDAOImpl sellerDetailsDAO;

	private static final Logger LOGGER  = Logger
			.getLogger(SellerServiceImpl.class);
	
	public SellerDetails saveSeller(SellerDetails seller) throws SellerException{
		
		try{
			return sellerDetailsDAO.persist(seller);
		}catch(Exception e){
			LOGGER.error("Error occured while persisting seller details " , e);
			throw new SellerException("Error occured while persisting seller details ", e);
		}
	}

	public void setSellerDetailsDAO(SellerDetailsDAOImpl sellerDetailsDAO) {
		this.sellerDetailsDAO = sellerDetailsDAO;
	}

}
