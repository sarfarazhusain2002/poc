package com.oxy.hcm.services.product;

import java.util.List;

import com.oxy.hcm.dto.Product;
import com.oxy.hcm.dto.ResponseTransaction;
import com.oxy.hcm.dto.TransactionDetails;

public interface ProductService {
	Product getProduct(final long productId);
	Product addProduct(Product product);
	ResponseTransaction addTransaction(List<TransactionDetails> transactionDetails);
	List<TransactionDetails> getTransactionDetails();
	
}
