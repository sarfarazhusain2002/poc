package com.oxy.s3m.notification.convertor;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import com.oxy.s3m.notification.model.categories.Categories;
import com.oxy.s3m.notification.model.customer.CustomerAddress;
import com.oxy.s3m.notification.model.customer.CustomerCategories;
import com.oxy.s3m.notification.model.customer.CustomerDetails;
import com.oxy.s3m.notification.model.customer.MasterArea;
import com.oxy.s3m.notification.model.customer.CustomerInterests;



public class CustomerConvertor {
	
	public static CustomerDetails convert(JSONObject objcustomer){
		CustomerDetails customer = new CustomerDetails();
		CustomerAddress custadd = new CustomerAddress();
		Set<CustomerCategories> customerCategoriesList = new HashSet<CustomerCategories>();
		Set<CustomerInterests> customerInterestsList = new HashSet<CustomerInterests>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());;
		System.out.println(dateFormat.format(date));
		
		customer.setCustName(objcustomer.getString("name"));        
		customer.setDateCreated(date);
		customer.setActive(true);
		customer.setDeviceId(objcustomer.getString("deviceId"));
		customer.setFcmToken(objcustomer.getString("fcmToken"));
		custadd.setCustPh(objcustomer.getString("phno"));
		custadd.setCustCountry(objcustomer.getString("country"));
		custadd.setCustState(objcustomer.getString("state"));
		custadd.setCustCity(objcustomer.getString("city"));
		custadd.setCustArea(objcustomer.getString("area"));
		custadd.setAddress(objcustomer.getString("address"));
		custadd.setEmail(objcustomer.getString("email"));					
		customer.setCustomerAddress(custadd);
		
		
		for(int i=0; i<objcustomer.getJSONArray("categories").length(); i++){
			Categories categories=new Categories(); 
			CustomerCategories customerCategories = new CustomerCategories();			
			System.out.println(objcustomer.getJSONArray("categories").get(i));
			System.out.println(">>>"+objcustomer.getJSONArray("categories").get(i));
			categories.setId(Integer.parseInt(objcustomer.getJSONArray("categories").get(i).toString()));
			customerCategories.setCategories(categories);
			customerCategoriesList.add(customerCategories);
		}
		customer.setCustCategoriesCollection(customerCategoriesList);
		for(int i=0; i<objcustomer.getJSONArray("interests").length(); i++){
			MasterArea masterArea = new MasterArea();
			CustomerInterests customerInterests = new CustomerInterests();			
			System.out.println(">>>"+objcustomer.getJSONArray("interests").get(i));
			masterArea.setId(Integer.parseInt(objcustomer.getJSONArray("interests").get(i).toString()));			
			customerInterests.setMasterArea(masterArea);			
			customerInterestsList.add(customerInterests);
		}
		customer.setCustomerInterests(customerInterestsList);
		
		
		return customer;
		
	}

}
