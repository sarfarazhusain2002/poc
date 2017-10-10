package com.oxy.s3m.notification.convertor;

import org.json.JSONObject;

import com.oxy.s3m.notification.model.seller.Message;
import com.oxy.s3m.notification.model.seller.Notification;

public class PushNotificationConvertor {

	public Notification convertSellerDetails(final JSONObject objcustomer){

		Notification notification = new Notification();
		Message msg = new Message();

		notification.setNotificationTitle(objcustomer.getString("title"));  
		notification.setNotificationStartdate(objcustomer.getString("startdate"));
		notification.setNotificationEnddate(objcustomer.getString("enddate"));
		notification.setCatId(objcustomer.getInt("catid"));
		msg.setMsgTxt(objcustomer.getString("msg"));
		notification.setSellerId(objcustomer.getLong("seller_id"));
		notification.setMessages(msg);

		return notification;

	}

}
