package com.oxy.s3m.notification.beans.customer;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class MessageListBean {
	private Timestamp lastmodifieddate;
	private List<Notification> listofmessages;
	public String getLastmodifieddate() {
		final   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		return sdf.format(lastmodifieddate);
	}
	public void setLastmodifieddate(Timestamp lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}
	public List<Notification> getListofmessages() {
		return listofmessages;
	}
	public void setListofmessages(List<Notification> listofmessages) {
		this.listofmessages = listofmessages;
	}
	
	
	

}
