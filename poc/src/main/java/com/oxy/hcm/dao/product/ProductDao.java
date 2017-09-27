package com.oxy.hcm.dao.product;

import com.oxy.hcm.dto.Product;
import com.oxy.hcm.dto.TransactionDetails;

public interface ProductDao {
	Product getProduct(final long productId);
	boolean addProduct(Product product);
	boolean addTransactionDetails(TransactionDetails transactionDetails);
}
