package com.oxy.s3m.notification.convertor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oxy.s3m.notification.model.categories.Categories;
import com.oxy.s3m.notification.model.customer.CustomerCategories;
import com.oxy.s3m.notification.model.customer.CustomerInterests;
import com.oxy.s3m.notification.model.customer.MasterArea;
//import com.oxy.s3m.notification.model.seller.MasterArea;
import com.oxy.s3m.notification.model.seller.SellerAddress;
import com.oxy.s3m.notification.model.seller.SellerCategories;
import com.oxy.s3m.notification.model.seller.SellerDetails;
import com.oxy.s3m.notification.model.seller.SellerInterests;

public class SellerDetailsActiveConverter {

	//private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public SellerDetails convertSellerDetails(final JSONObject objcustomer){
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		SellerDetails seller = new SellerDetails();
		SellerAddress selleradd = new SellerAddress();
		Set<SellerAddress> collectionselleradd = new HashSet<SellerAddress>();
		Set<SellerCategories> sellerCategoriesList = new HashSet<SellerCategories>();
		Set<SellerInterests> sellerInterestsList = new HashSet<SellerInterests>();
		seller.setSellerName(objcustomer.getString("name"));        
		seller.setDateCreated(date);
		seller.setActive(objcustomer.getString("active").equals("1")?true:false);
		seller.setDeviceId(objcustomer.getString("deviceId"));
		seller.setFcmToken(objcustomer.getString("fcmToken"));
		seller.setAgreement(objcustomer.getString("agree"));		
		seller.setSellerId(Integer.parseInt(objcustomer.getString("sellerId")));
		seller.setStatus("Success");
		
		return seller;
	}
	
}
