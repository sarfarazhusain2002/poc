package com.oxy.s3m.notification.validators;

import java.util.regex.Pattern;

import org.json.JSONObject;

public class PushNotificationValidator {
	public JSONObject validate(JSONObject custobj){
		JSONObject obj = new JSONObject();
		obj.put("validate", true);
	    if(custobj.getString("title")==null || custobj.getString("title").equals("")){
	    	obj.put("title", "Name Can not be blank");
	    	obj.put("validate", false);
	    }else if(custobj.getString("title").length()>100){
	    	obj.put("title", "title length can not be greater than 100");
	    	obj.put("validate", false);
	    }
	    if(custobj.getString("msg")==null || custobj.getString("msg").equals("")){
	    	obj.put("msg", "msg Can not be blank");
	    	obj.put("validate", false);
	    }	    	    
	    if(custobj.getString("startdate")==null || custobj.getString("startdate").equals("")){
	    	obj.put("startdate", "startdate no Can not be blank");
	    	obj.put("validate", false);
	    }
	    if(custobj.getString("enddate")==null || custobj.getString("enddate").equals("")){
	    	obj.put("enddate", "enddate can not be blank");
	    	obj.put("validate", false);
	    }    	    
	    	    
		return obj;
	}
	
	


}
