package com.oxy.hcm.dto;

public class TransactionDetails {
	
	   private Long transaction_id ;
	   private String price ;
	   private String final_price ;
	   private String discount_price ;
	   private String discount ;   
	   private String quantity ;
	   private Long product_id ;
	   private String curr;
	   private String productName;
	   private String payment_transaction_id;
	   
	   public String getPayment_transaction_id() {
		return payment_transaction_id;
	}
	public void setPayment_transaction_id(String payment_transaction_id) {
		this.payment_transaction_id = payment_transaction_id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getFinal_price() {
		return final_price;
	}
	public void setFinal_price(String final_price) {
		this.final_price = final_price;
	}
	public String getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(String discount_price) {
		this.discount_price = discount_price;
	}
	
	   
	public Long getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(Long transaction_id) {
		this.transaction_id = transaction_id;
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
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	   

}
