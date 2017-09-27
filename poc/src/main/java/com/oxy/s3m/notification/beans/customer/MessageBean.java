package com.oxy.s3m.notification.beans.customer;

import java.sql.Date;

public class MessageBean {
	
	private Date enddate;
	private String notification_txt;
	private String msg_txt;
	private String address;
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getNotification_txt() {
		return notification_txt;
	}
	public void setNotification_txt(String notification_txt) {
		this.notification_txt = notification_txt;
	}
	public String getMsg_txt() {
		return msg_txt;
	}
	public void setMsg_txt(String msg_txt) {
		this.msg_txt = msg_txt;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
