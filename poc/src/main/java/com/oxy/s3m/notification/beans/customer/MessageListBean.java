package com.oxy.s3m.notification.beans.customer;

import java.sql.Date;
import java.util.List;

public class MessageListBean {
	private Date lastmodifieddate;
	private List<MessageBean> listofmessage;
	public Date getLastmodifieddate() {
		return lastmodifieddate;
	}
	public void setLastmodifieddate(Date lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}
	public List<MessageBean> getListofmessage() {
		return listofmessage;
	}
	public void setListofmessage(List<MessageBean> listofmessage) {
		this.listofmessage = listofmessage;
	}
	
	
	

}
