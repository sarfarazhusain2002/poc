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
		
		System.out.println(objcustomer.isNull("lastUpdatedDate")?"2017-10-14 19.03.56":objcustomer.getString("lastUpdatedDate").toString());
	    Date parsed = format.parse((objcustomer.isNull("lastUpdatedDate")?"2017-10-14 19.03.56":objcustomer.getString("lastUpdatedDate").toString()));
	    java.sql.Date lastUpdateDate = new java.sql.Date(parsed.getTime());
		
		reqGetNotification.setCustId(Integer.parseInt(objcustomer.getString("cust_id")));        
		reqGetNotification.setNotificationEnddate(objcustomer.isNull("lastUpdatedDate")?"2017-10-14 19.03.56":objcustomer.getString("lastUpdatedDate").toString());
		
		
		return reqGetNotification;
		
	}

}
