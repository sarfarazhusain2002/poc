package com.oxy.s3m.notification.DAO.seller;

import com.oxy.s3m.notification.exception.SellerException;
import com.oxy.s3m.notification.model.seller.SellerDetails;

public interface SellerDetailsDAO {
	public SellerDetails persist(SellerDetails transientInstance) throws SellerException;
	boolean findSellerByPhone(final String phoneNo) throws SellerException;
}
