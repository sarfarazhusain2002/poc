package com.oxy.s3m.notification.validators;

import java.util.regex.Pattern;

import org.json.JSONObject;

public class ReqGetNotificationValidation {
	public  JSONObject Validate(JSONObject custobj){

		JSONObject obj = new JSONObject();
		obj.put("validate", true);
		if(custobj.getString("custid")==null || custobj.getString("custid").equals("")){
			obj.put("custid", "custid Can not be blank");
			obj.put("validate", false);
		}
		if(custobj.getString("lastUpdatedDate")==null || custobj.getString("lastUpdatedDate").equals("")){
			obj.put("lastUpdatedDate", "lastUpdatedDate Can not be blank");
			obj.put("validate", false);
		}	


		return obj;
	
	}

}
