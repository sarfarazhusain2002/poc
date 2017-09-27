package com.oxy.s3m.notification.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class CustomerValidator {

	private  static Pattern pattern;
	private static Matcher matcher;

	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public  JSONObject Validate(JSONObject custobj){
		JSONObject obj = new JSONObject();
		obj.put("validate", true);
		if(custobj.getString("name")==null || custobj.getString("name").equals("")){
			obj.put("name", "Name Can not be blank");
			obj.put("validate", false);
		}else if(custobj.getString("name").length()>50){
			obj.put("name", "Name length can not be greater than 50");
			obj.put("validate", false);
		}
		if(custobj.getString("deviceId")==null || custobj.getString("deviceId").equals("")){
			obj.put("deviceId", "deviceId Can not be blank");
			obj.put("validate", false);
		}
		if(custobj.getString("fcmToken")==null || custobj.getString("fcmToken").equals("")){
			obj.put("fcmToken", "fcmToken Can not be blank");
			obj.put("validate", false);
		}
		if(custobj.getString("phno")==null || custobj.getString("phno").equals("")){
			obj.put("phno", "Phone no Can not be blank");
			obj.put("validate", false);
		}else if(custobj.getString("phno").length()>10 || custobj.getString("phno").length()<10){
			obj.put("phno", "Phone no length can not be greater than 10 and less than 10");
			obj.put("validate", false);	    	
		}else if(!Pattern.matches("[789][0-9]{9}", custobj.getString("phno"))){
			obj.put("phno", "Only numbers allowed and should start with 7/8/9");
			obj.put("validate", false);
		}
		if(custobj.getString("country")==null || custobj.getString("country").equals("")){
			obj.put("country", "country can not be blank");
			obj.put("validate", false);
		}else if(custobj.getString("country").length()>40){
			obj.put("country", "country length can not be greater than 40");
			obj.put("validate", false);	    	
		}
		if(custobj.getString("state")==null || custobj.getString("state").equals("")){
			obj.put("state", "state can not be blank");
			obj.put("validate", false);
		}else if(custobj.getString("state").length()>40){
			obj.put("state", "state length can not be greater than 40");
			obj.put("validate", false);	    	
		}
		if(custobj.getString("city")==null || custobj.getString("city").equals("")){
			obj.put("city", "city can not be blank");
			obj.put("validate", false);
		}else if(custobj.getString("city").length()>40){
			obj.put("city", "city length can not be greater than 40");
			obj.put("validate", false);	    	
		}
		if(custobj.getString("area")==null || custobj.getString("area").equals("")){
			obj.put("area", "area can not be blank");
			obj.put("validate", false);
		}else if(custobj.getString("area").length()>40){
			obj.put("area", "area length can not be greater than 40");
			obj.put("validate", false);	    	
		}
		if(custobj.getString("address")==null || custobj.getString("address").equals("")){
			obj.put("address", "address can not be blank");
			obj.put("validate", false);
		}else if(custobj.getString("address").length()>200){
			obj.put("area", "address length can not be greater than 200");
			obj.put("validate", false);	    	
		}

		if(custobj.getString("email")==null || custobj.getString("email").equals("")){
			obj.put("email", "email can not be blank");
			obj.put("validate", false);
		}else if(custobj.getString("email").length()>100){
			obj.put("email", "email length can not be greater than 100");
			obj.put("validate", false);	    	
		}else {
			pattern = Pattern.compile(EMAIL_PATTERN);
			System.out.println("email>>>"+validateemail(custobj.getString("email").toString()));
			if(!validateemail(custobj.getString("email").toString())){
				obj.put("email", "invalid email address");
				obj.put("validate", false);	    	
			}
		}
		
	    
		if(custobj.getJSONArray("categories")==null || custobj.getJSONArray("categories").length()==0){
	    	obj.put("categories", "Customer has to choose categories");
	    	obj.put("validate", false);
	    }
	    
	    if(custobj.getJSONArray("interests")==null || custobj.getJSONArray("interests").length()==0){
	    	obj.put("interests", "Customer has to choose area");
	    	obj.put("validate", false);
	    }	

		return obj;
	}

	public static boolean validateemail(final String hex) {

		matcher = pattern.matcher(hex);
		return matcher.matches();

	}

}
