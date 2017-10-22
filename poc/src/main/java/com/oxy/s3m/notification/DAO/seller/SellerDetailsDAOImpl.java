package com.oxy.s3m.notification.DAO.seller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oxy.s3m.connection.utils.CustMySqlConnectionUtils;
import com.oxy.s3m.connection.utils.NotificationHelper;
import com.oxy.s3m.connection.utils.badWordsFilter;
import com.oxy.s3m.notification.beans.seller.SellerDetailsBean;
import com.oxy.s3m.notification.exception.SellerException;
import com.oxy.s3m.notification.model.seller.Notification;
import com.oxy.s3m.notification.model.seller.SellerAddress;
import com.oxy.s3m.notification.model.seller.SellerCategories;
import com.oxy.s3m.notification.model.seller.SellerDetails;
import com.oxy.s3m.notification.model.seller.SellerInterests;
//import org.hibernate.SessionFactory;
//import com.oxy.s3m.notification.utils.MySqlConnectionUtils;

/**
 * Home object for domain model class SellerDetails.
 * @see com.s3m.CustomerDetails.SellerDetails
 * @author Hibernate Tools
 */
@Repository
public class SellerDetailsDAOImpl  implements SellerDetailsDAO{
	public static final String FIND_SELLER_BY_PHONE = "select seller_id from seller_address where seller_ph = ?";
	public static final String SELLER_DETAILS = "select max(seller_id) seller_id from seller_details";
	public static final String INSERT_SELLER = "INSERT INTO seller_details(seller_id, seller_name, device_id, fcm_token, date_created, active, status, agreement) values(?,?,?,?,?,?,?,?)";
	public static final String INSERT_SELLER_ADDRESS = "insert  into  seller_address (seller_id, seller_ph, seller_country, seller_state, seller_city, seller_area, address) values (?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_SELLER_INTEREST = "INSERT INTO seller_interests(seller_id, area_id) VALUES (?,?)";
	public static final String INSERT_SELLER_CATEGORIES = "INSERT INTO seller_categories(seller_id, cat_id) VALUES (?,?)";
	public static final String INSERT_SELLER_NOTIFICATION = "INSERT INTO notification(seller_id,notification_title,notification_startdate,notification_enddate,status,cat_id) VALUES (?,?,?,?,?,?)";
	public static final String INSERT_SELLER_MESSAGE = "INSERT INTO message(notification_id,msg_txt) VALUES (?,?)";
	public static final String GET_NOTIFICATIONS_TOKEN = "select distinct cd.fcm_token fcm_token from notification no,seller_interests si, customer_interests ci,"+
			"seller_details sd,"+  
			"customer_categories cc,"+
			"customer_details cd, message msg where no.seller_id = si.seller_id "+
			"and si.area_id = ci.area_id "+
			"and no.cat_id = cc.cat_id "+			
			"and ci.cust_id = cd.cust_id "+
			"and cc.cust_id = cd.cust_id "+
			"and no.notification_id = msg.notification_id "+
			"and sd.active = 1 "+
			"and sd.seller_id=? ";
	public static final String UPDATE_SELLER_NOTIFICATION = "UPDATE notification set status='sent' where notification_id=?";
	public static final String SELLER_NOTIFICATION = "select max(notification_id) notification_id from notification";
	public static final String SELLER_ACTIVE = "update seller_details set active=? where seller_id=?";

			
	

	private static final Logger LOGGER  = Logger
			.getLogger(SellerDetailsDAOImpl.class);

	

	public SellerDetails persist(SellerDetails transientInstance)
			throws SellerException {

		SellerAddress sAddress = transientInstance.getSellerAddresses().iterator().next();
		if(this.findSellerByPhone(sAddress.getSellerPh())){
			throw new SellerException("Seller already exist.");
		}

		Connection con = null;
		PreparedStatement stmtselect = null;		
		ResultSet rs = null;
		LOGGER.debug("persisting SellerDetails instance");
		try {
			
			con = CustMySqlConnectionUtils.getConnection();
			con.setAutoCommit(false);
			System.out.println("Insert Query>>" + SELLER_DETAILS);
			stmtselect = con.prepareStatement(SELLER_DETAILS);
			rs=stmtselect.executeQuery();
			rs.next();		

			long sellerId=rs.getInt("seller_id")+1;

			inserSellerDetails(transientInstance,con,sellerId);
			
			inserSellerAddress(transientInstance, con, sellerId);

			inserSellerCategories(transientInstance, con, sellerId);

			inserSellerInterest(transientInstance, con, sellerId);

			con.commit();
			transientInstance.setSellerId(rs.getInt("seller_id")+1);
			LOGGER.debug("persist successful");
		} catch (Exception re) {
			try {
				LOGGER.error("persist failed", re);
				con.rollback();				
				throw new SellerException("Error occured while saving seller details", re);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOGGER.error("rollback failed", e);
				e.printStackTrace();
			}
			LOGGER.error("persist failed", re);

		} finally{
			try {
				con.close();
			} catch (SQLException e) {
				LOGGER.error("Error while closing connection", e);
			}
		}
		return transientInstance;
	}

	@Override
	public boolean findSellerByPhone(String phoneNo) throws SellerException {
		boolean bFound = false;

		Connection con = null;
		PreparedStatement stmtselect = null;
		ResultSet rs = null;
		LOGGER.debug("Find SellerDetails By Phone");
		try {
			//con = MySqlConnectionUtils.getConnection();
			con = CustMySqlConnectionUtils.getConnection();
			con.setAutoCommit(false);
			System.out.println("Select Query>>" + FIND_SELLER_BY_PHONE);
			stmtselect = con.prepareStatement(FIND_SELLER_BY_PHONE);
			stmtselect.setString(1, phoneNo);
			rs=stmtselect.executeQuery();
			while (rs.next()) {
				bFound = true;
			}
		} catch (Exception re) {
			LOGGER.error("Error occured while finding seller details", re);
			throw new SellerException("Error occured while finding seller details", re);
		} finally{
			try {
				con.close();
			} catch (SQLException e) {
				LOGGER.error("Error while closing connection", e);
			}
		}
		return bFound;
	}

	public void inserSellerDetails(SellerDetails transientInstance,Connection con,Long sellerId) throws SQLException{
		PreparedStatement stmtSeller=null;
		stmtSeller = con.prepareStatement(INSERT_SELLER);
		stmtSeller.setLong(1, sellerId);
		stmtSeller.setString(2, transientInstance.getSellerName());
		stmtSeller.setString(3, transientInstance.getDeviceId());
		stmtSeller.setString(4, transientInstance.getFcmToken());
		stmtSeller.setTimestamp(5, transientInstance.getDateCreated());
		stmtSeller.setBoolean(6, transientInstance.getActive());
		stmtSeller.setString(7, transientInstance.getStatus());
		stmtSeller.setString(8, transientInstance.getAgreement());
		stmtSeller.executeUpdate();
		stmtSeller.close();
	}

	public void inserSellerAddress(SellerDetails transientInstance,Connection con,Long sellerId) throws SQLException{
		PreparedStatement stmtSelleraddress=null;
		stmtSelleraddress= con.prepareStatement(INSERT_SELLER_ADDRESS);
		SellerAddress sellerAddress= transientInstance.getSellerAddresses().iterator().next();
		stmtSelleraddress.setLong(1, sellerId);
		stmtSelleraddress.setString(2, sellerAddress.getSellerPh());
		stmtSelleraddress.setString(3, sellerAddress.getSellerCountry());
		stmtSelleraddress.setString(4, sellerAddress.getSellerState());
		stmtSelleraddress.setString(5, sellerAddress.getSellerCity());
		stmtSelleraddress.setString(6, sellerAddress.getSellerArea());
		stmtSelleraddress.setString(7, sellerAddress.getAddress());
		stmtSelleraddress.executeUpdate();
		stmtSelleraddress.close();

	}

	public void inserSellerCategories(SellerDetails transientInstance,Connection con,Long sellerId) throws SQLException{
		PreparedStatement stmtSelleCategories=null;
		stmtSelleCategories= con.prepareStatement(INSERT_SELLER_CATEGORIES);
		//SellerCategories sellerCategories = transientInstance.getSellerCategorieses().iterator().next();
		for(SellerCategories sellerCategories : transientInstance.getSellerCategorieses()){
			stmtSelleCategories.setLong(1, sellerId);
			stmtSelleCategories.setLong(2, sellerCategories.getCategories().getId());
			stmtSelleCategories.executeUpdate();

		}
		stmtSelleCategories.close();
	}
	public void inserSellerInterest(SellerDetails transientInstance,Connection con,Long sellerId) throws SQLException{
		PreparedStatement stmtSellerInterests=null;
		stmtSellerInterests= con.prepareStatement(INSERT_SELLER_INTEREST);
		//SellerCategories sellerCategories = transientInstance.getSellerCategorieses().iterator().next();
		for(SellerInterests sellerInterests : transientInstance.getSellerInterests()){
			stmtSellerInterests.setLong(1, sellerId);
			stmtSellerInterests.setLong(2, sellerInterests.getMasterArea().getId());
			stmtSellerInterests.executeUpdate();

		}
		stmtSellerInterests.close();


	}

	@Override
	public SellerDetailsBean sendNotification(Notification notification) throws SellerException {
		Connection con = null;		
		con=CustMySqlConnectionUtils.getConnection();
		SellerDetailsBean notificationBean = new SellerDetailsBean();
		badWordsFilter badwordfilter = new badWordsFilter();
		//NotificationHelper helper = new NotificationHelper();
		ResultSet rs = null;
		PreparedStatement stmtselect=null;
		List<String> deviceToken= new ArrayList<String>();
		try {
			con.setAutoCommit(false);
			Long not_id= insertNotification(notification, con);
			insertMsg(notification,con,not_id);
			con.commit();
			if(badwordfilter.filterBadwords(notification.getNotificationTitle())) {
				if(badwordfilter.filterBadwords(notification.getMessages().getMsgTxt())) {
					System.out.println("Notification Querry:"+GET_NOTIFICATIONS_TOKEN +"id="+notification.getSellerId());
					stmtselect = con.prepareStatement(GET_NOTIFICATIONS_TOKEN);
					stmtselect.setLong(1, notification.getSellerId());
					rs=stmtselect.executeQuery();
					while(rs.next()) {
						System.out.println("Fcm Token : "+rs.getString("fcm_token"));
						deviceToken.add(rs.getString("fcm_token"));
					}
					if(deviceToken==null || deviceToken.size()==0) {
						notificationBean.setMessage("Either seller is not activate or no Customer exist for your criteria");
					//notificationBean.setSellerId(Integer.pnotification.getSellerId());
					return notificationBean;}
					String pushNotificationmsg=NotificationHelper.sendPushNotification(deviceToken, notification.getNotificationTitle(), notification.getMessages().getMsgTxt());
					notificationBean.setMessage(pushNotificationmsg);
				}else {
					notificationBean.setMessage("Message body contains censored words");
					//notificationBean.setSellerId(Integer.pnotification.getSellerId());
					return notificationBean;
				}
			}else {
				notificationBean.setMessage("Title contains censored words");
				return notificationBean;
			}
			stmtselect = con.prepareStatement(UPDATE_SELLER_NOTIFICATION);
			System.out.println("Update Query :"+UPDATE_SELLER_NOTIFICATION +">>>"+not_id);
			stmtselect.setLong(1, not_id);
			stmtselect.executeUpdate();
			con.commit();
			notificationBean.setMessage("Message Sent Successfully");
			notificationBean.setSellerId((int) (long) notification.getSellerId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return notificationBean;
	}
	
	public Long insertNotification(Notification transientInstance,Connection con) throws SQLException{
		PreparedStatement stmtNotification=null;
		stmtNotification= con.prepareStatement(INSERT_SELLER_NOTIFICATION);
		Long not_id=0L;
		//SellerCategories sellerCategories = transientInstance.getSellerCategorieses().iterator().next();
			System.out.println("StartDate:"+transientInstance.getNotificationStartdate().toString());
			stmtNotification.setLong(1, transientInstance.getSellerId());
			stmtNotification.setString(2, transientInstance.getNotificationTitle());
			stmtNotification.setTimestamp(3, Timestamp.valueOf(transientInstance.getNotificationStartdate().toString()));
			stmtNotification.setTimestamp(4, Timestamp.valueOf(transientInstance.getNotificationEnddate().toString()));
			stmtNotification.setString(5, "Process");
			stmtNotification.setLong(6, transientInstance.getCatId());
			stmtNotification.executeUpdate();
			stmtNotification = con.prepareStatement(SELLER_NOTIFICATION);
			ResultSet rs = stmtNotification.executeQuery();
			rs.next();
				not_id=(long) rs.getInt(1);
	       
		stmtNotification.close();
		return not_id;


	}
	public void insertMsg(Notification transientInstance,Connection con,Long notId) throws SQLException{
		PreparedStatement stmtSellerInterests=null;
		stmtSellerInterests= con.prepareStatement(INSERT_SELLER_MESSAGE);
		//SellerCategories sellerCategories = transientInstance.getSellerCategorieses().iterator().next();
		
			stmtSellerInterests.setLong(1, notId);
			stmtSellerInterests.setString(2, transientInstance.getMessages().getMsgTxt());
			stmtSellerInterests.executeUpdate();

		
		stmtSellerInterests.close();


	}

	@Override
	public SellerDetails active(SellerDetails transientInstance) throws SellerException {
		Connection con = null;
		PreparedStatement stmtselect = null;		
		ResultSet rs = null;
		
		con = CustMySqlConnectionUtils.getConnection();
		System.out.println("Insert Query>>" + SELLER_ACTIVE);
		try {
			stmtselect = con.prepareStatement(SELLER_ACTIVE);
			stmtselect.setBoolean(1, transientInstance.getActive());
			stmtselect.setInt(2, transientInstance.getSellerId());
			
			System.out.println("Active : "+transientInstance.getActive() +"sellerId : "+transientInstance.getSellerId());
			stmtselect.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			transientInstance.setStatus("Failed");
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			

		return transientInstance;
	}

}
