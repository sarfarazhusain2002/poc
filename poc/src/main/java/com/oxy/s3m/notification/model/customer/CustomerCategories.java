package com.oxy.s3m.notification.model.customer;
// Generated Sep 5, 2017 4:03:09 PM by Hibernate Tools 5.2.3.Final

import com.oxy.s3m.notification.model.categories.Categories;

/**
 * SellerCategories generated by hbm2java
 */
public class CustomerCategories implements java.io.Serializable {

	private Integer id;
	private Categories categories;
	private CustomerDetails customerDetails;

	public CustomerCategories() {
	}

	public CustomerCategories(Categories categories, CustomerDetails customerDetails) {
		this.categories = categories;
		this.customerDetails = customerDetails;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Categories getCategories() {
		return this.categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public CustomerDetails getCustomerDetails() {
		return this.customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

}
