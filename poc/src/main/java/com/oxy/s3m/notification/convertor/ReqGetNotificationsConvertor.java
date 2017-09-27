package com.oxy.s3m.notification.convertor;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.oxy.s3m.notification.beans.customer.ReqGetNotification;
import com.oxy.s3m.notification.model.customer.CustomerAddress;
import com.oxy.s3m.notification.model.customer.CustomerDetails;

public class ReqGetNotificationsConvertor {

	public static ReqGetNotification convert(JSONObject objcustomer) throws JSONException, ParseException{
		ReqGetNotification reqGetNotification = new ReqGetNotification();
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
	    Date parsed = format.parse(objcustomer.getString("lastUpdatedDate").toString());
	    java.sql.Date lastUpdateDate = new java.sql.Date(parsed.getTime());
		
		reqGetNotification.setCustId(Integer.parseInt(objcustomer.getString("cust_id")));        
		reqGetNotification.setNotificationEnddate(objcustomer.getString("lastUpdatedDate").toString());
		
		
		return reqGetNotification;
		
	}

}
