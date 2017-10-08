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

public class SellerDetailsConverter {

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
		seller.setActive(false);
		seller.setDeviceId(objcustomer.getString("deviceId"));
		seller.setFcmToken(objcustomer.getString("fcmToken"));
		seller.setAgreement(objcustomer.getString("agree"));
		selleradd.setSellerPh(objcustomer.getString("phno"));
		selleradd.setSellerCountry(objcustomer.getString("country"));
		selleradd.setSellerState(objcustomer.getString("state"));
		selleradd.setSellerCity(objcustomer.getString("city"));
		selleradd.setSellerArea(objcustomer.getString("area"));
		selleradd.setAddress(objcustomer.getString("address"));			
		collectionselleradd.add(selleradd);
		seller.setSellerAddresses(collectionselleradd);
		
		for(int i=0; i<objcustomer.getJSONArray("categories").length(); i++){
			Categories categories=new Categories(); 
			SellerCategories sellerCategories = new SellerCategories();			
			System.out.println(objcustomer.getJSONArray("categories").get(i));
			System.out.println(">>>"+objcustomer.getJSONArray("categories").get(i));
			categories.setId(Integer.parseInt(objcustomer.getJSONArray("categories").get(i).toString()));

			sellerCategories.setCategories(categories);
			sellerCategoriesList.add(sellerCategories);
		}
		seller.setSellerCategorieses(sellerCategoriesList);
		for(int i=0; i<objcustomer.getJSONArray("interests").length(); i++){
			MasterArea masterArea = new MasterArea();
			SellerInterests sellerInterests = new SellerInterests();			
			masterArea.setId(Integer.parseInt(objcustomer.getJSONArray("interests").get(i).toString()));
			sellerInterests.setMasterArea(masterArea);
			sellerInterestsList.add(sellerInterests);
		}
		
		
		
		seller.setSellerInterests(sellerInterestsList);
		seller.setStatus("Success");
		
		return seller;
	}
	
}
