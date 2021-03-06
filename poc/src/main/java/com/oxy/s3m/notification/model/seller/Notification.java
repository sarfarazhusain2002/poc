package com.oxy.s3m.notification.model.seller;
// Generated Sep 15, 2017 4:52:02 PM by Hibernate Tools 5.2.3.Final

import java.text.SimpleDateFormat;

/**
 * Notification generated by hbm2java
 */
public class Notification implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Integer notificationId;
	private Long sellerId;
	private String notificationTitle;
	private String notificationStartdate;
	private String notificationEnddate;
	//private String status;
	private int catId;
	private Message messages;

	public Notification() {
	}

	public Notification(String notificationTitle, String notificationStartdate, String notificationEnddate, int catId) {
		this.notificationTitle = notificationTitle;
		this.notificationStartdate = notificationStartdate;
		this.notificationEnddate = notificationEnddate;
		this.catId = catId;
	}

	public Notification(Long sellerId, String notificationTitle, String notificationStartdate,
			String notificationEnddate, String status, int catId, Message messages) {
		this.sellerId = sellerId;
		this.notificationTitle = notificationTitle;
		this.notificationStartdate = notificationStartdate;
		this.notificationEnddate = notificationEnddate;
		//this.status = status;
		this.catId = catId;
		this.messages = messages;
	}

	

	public Long getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getNotificationTitle() {
		return this.notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationStartdate() {
		final   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");	
		return this.notificationStartdate;
		
	}

	public void setNotificationStartdate(String notificationStartdate) {
		this.notificationStartdate = notificationStartdate;
	}

	public String getNotificationEnddate() {
		final   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");	
		return this.notificationEnddate;
	}

	public void setNotificationEnddate(String notificationEnddate) {
		this.notificationEnddate = notificationEnddate;
	}


	public int getCatId() {
		return this.catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public Message getMessages() {
		return this.messages;
	}

	public void setMessages(Message msg) {
		this.messages = msg;
	}

}
