package com.oxy.hcm.dto;

public class Product {
	
	   
	   private String product_name ;
	   private String price ;
	   private String discount ;   
	   private String quantity ;
	   private Long product_id ;
	   private String curr;
	   
	   
	   public String getCurr() {
		return curr;
	}
	   public void setCurr(String curr) {
		this.curr = curr;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
