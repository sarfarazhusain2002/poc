package com.oxy.s3m.notification.beans.customer;

import java.sql.Date;

public class ReqGetNotification {
	private Integer custId;
	private String notificationEnddate;
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getNotificationEnddate() {
		return notificationEnddate;
	}
	public void setNotificationEnddate(String notificationEnddate) {
		this.notificationEnddate = notificationEnddate;
	}

}
