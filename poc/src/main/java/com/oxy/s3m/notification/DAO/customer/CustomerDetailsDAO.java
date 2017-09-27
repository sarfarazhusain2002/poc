package com.oxy.s3m.notification.DAO.customer;
// Generated Sep 2, 2017 10:03:50 PM by Hibernate Tools 5.2.3.Final

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.oxy.s3m.connection.utils.CustMySqlConnectionUtils;
import com.oxy.s3m.notification.beans.customer.Message;
import com.oxy.s3m.notification.beans.customer.Notification;
import com.oxy.s3m.notification.beans.customer.ReqGetNotification;
import com.oxy.s3m.notification.exception.CustomerException;
//import com.oxy.s3m.notification.exception.SellerException;
import com.oxy.s3m.notification.model.customer.CustomerAddress;
import com.oxy.s3m.notification.model.customer.CustomerCategories;
import com.oxy.s3m.notification.model.customer.CustomerDetails;
import com.oxy.s3m.notification.model.customer.CustomerInterests;
//import org.hibernate.SessionFactory;

/**
 * Home object for domain model class CustomerDetails.
 * @see com.oxy.s3m.CustomerDetailsBean.CustomerDetails
 * @author Hibernate Tools
 */
@Repository
public class CustomerDetailsDAO {
	public static final String CUSTOMER_DETAILS = "select max(cust_id) cust_id from customer_details";
	public static final String INSERT_CUSTOMER = "INSERT INTO customer_details(cust_id, active, cust_name, date_created, device_id, fcm_token) values(?,?,?,?,?,?)";
	public static final String INSERT_CUSTOMER_ADDRESS = "insert  into  customer_address (address, cust_area, cust_city, cust_country, cust_ph, cust_state, customer_id,email) values (?, ?, ?, ?, ?, ?, ?,?)";
	public static final String UPDATE_CUSTOMER = "update customer_details set device_id=?,fcm_token=? where cust_id=?";
	public static final String CHECK_MOBILE_NO = "select * from customer_address where cust_ph=?";
	public static final String GET_NOTIFICATIONS = "select distinct  no.notification_enddate enddate ,no.notification_title title,msg.msg_txt msg from notification no,seller_interests si, customer_interests ci,"+
			"seller_details sd,"+  
			"customer_categories cc,"+
			"customer_details cd, message msg where no.seller_id = si.seller_id "+
			"and si.area_id = ci.area_id "+
			"and no.cat_id = cc.cat_id "+			
			"and ci.cust_id = cd.cust_id "+
			"and cc.cust_id = cd.cust_id "+
			"and no.notification_id = msg.notification_id "+
			"and cd.cust_id=? "+
			"and no.notification_enddate >=?";
	public static final String INSERT_CUSTOMER_INTEREST = "INSERT INTO customer_interests(cust_id, area_id) VALUES (?,?)";
	public static final String INSERT_CUSTOMER_CATEGORIES = "INSERT INTO customer_categories(cust_id, cat_id) VALUES (?,?)";


	private static final Log LOGGER = LogFactory.getLog(CustomerDetailsDAO.class);




	public CustomerDetails persist(CustomerDetails transientInstance) throws CustomerException {
		Connection con = null;
		PreparedStatement stmtselect = null;		
		ResultSet rs = null;
		LOGGER.debug("persisting CustomerDetails instance");
		try {
			con = CustMySqlConnectionUtils.getConnection();
			con.setAutoCommit(false);




			// Start******Checking Mobile No Exist*****************//
			CustomerAddress cutomerAddress= transientInstance.getCustomerAddress();

			if(this.findSellerByPhone(cutomerAddress.getCustPh(),con)){
				throw new CustomerException("Customer already exist.");
			}

			// End******Checking Mobile No Exist*****************//

			// Start******Fetching highest cust id from customer details*****************//
			System.out.println("Insert Query>>" + CUSTOMER_DETAILS);
			stmtselect = con.prepareStatement(CUSTOMER_DETAILS);
			rs=stmtselect.executeQuery();
			rs.next();
			long custId=rs.getInt("cust_id")+1;
			// End******Fetching highest cust id from customer details******************//
			inserCustomerDetails(transientInstance, con, custId);

			inserCustomerAddress(transientInstance, con, custId);

			inserCustomerCategories(transientInstance, con, custId);

			inserCustomerInterest(transientInstance, con, custId);



			con.commit();
			transientInstance.setCustId(rs.getInt("cust_id")+1);
			transientInstance.setStatus("Success");
			LOGGER.debug("persist successful");
		} 
		catch (CustomerException re){
			//con.rollback();
			throw new CustomerException("Customer Already Exist", re);
		}
		catch (Exception re) {

			try {
				LOGGER.error("persist failed", re);
				con.rollback();				
				throw new CustomerException("Error occured while saving customer details", re);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOGGER.error("rollback failed", e);
				e.printStackTrace();
			}

		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return transientInstance;
	}

	public void updateCustomer(CustomerDetails instance) {
		LOGGER.debug("updating  CustomerDetails instance");
		Connection con = null;
		PreparedStatement updateCust = null;

		try {
			con = CustMySqlConnectionUtils.getConnection();			
			updateCust=con.prepareStatement(UPDATE_CUSTOMER);
			updateCust.setString(1, instance.getDeviceId());
			updateCust.setString(2, instance.getFcmToken());
			updateCust.setLong(3, instance.getCustId());
			updateCust.executeUpdate();
			LOGGER.debug("attach successful");
		} catch (RuntimeException re) {
			LOGGER.error("attach failed", re);
			throw re;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Notification> getNotifications(ReqGetNotification notification){

		LOGGER.debug("updating  CustomerDetails instance");
		Connection con = null;
		PreparedStatement getNotifications = null;
		ResultSet rs = null;
		List<Notification> lstNotification = new ArrayList<Notification> ();
		

		try {
			con = CustMySqlConnectionUtils.getConnection();			
			getNotifications=con.prepareStatement(GET_NOTIFICATIONS);
			System.out.println("SQL>>>"+GET_NOTIFICATIONS);
			System.out.println("Para1>>"+notification.getCustId());
			System.out.println("Para2>>"+notification.getNotificationEnddate());
			getNotifications.setInt(1, notification.getCustId());
			getNotifications.setString(2, notification.getNotificationEnddate());			
			rs=getNotifications.executeQuery();
			while (rs.next()){
				Notification not = new Notification();
				Message msg = new Message();
				not.setNotificationEnddate(rs.getDate("enddate"));
				not.setNotificationTitle(rs.getString("title"));
				msg.setMsgTxt(rs.getString("msg"));
				System.out.println(rs.getDate("enddate"));
				not.setMessages(msg);
				lstNotification.add(not);
			}
			LOGGER.debug("attach successful");
		} catch (RuntimeException re) {
			LOGGER.error("attach failed", re);
			throw re;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return lstNotification;

	}


	public void inserCustomerDetails(CustomerDetails transientInstance,Connection con,Long custId) throws SQLException{
		PreparedStatement stmtcustomer=null;
		stmtcustomer = con.prepareStatement(INSERT_CUSTOMER);
		stmtcustomer.setLong(1, custId);
		stmtcustomer.setBoolean(2, transientInstance.getActive());
		stmtcustomer.setString(3, transientInstance.getCustName());
		stmtcustomer.setTimestamp(4, transientInstance.getDateCreated());
		stmtcustomer.setString(5, transientInstance.getDeviceId());
		stmtcustomer.setString(6, transientInstance.getFcmToken());			
		stmtcustomer.executeUpdate();	
		stmtcustomer.close();
	}


	public void inserCustomerAddress(CustomerDetails transientInstance,Connection con,Long custId) throws SQLException{
		PreparedStatement stmtcustomeraddress=null;
		CustomerAddress cutomerAddress= transientInstance.getCustomerAddress();
		stmtcustomeraddress= con.prepareStatement(INSERT_CUSTOMER_ADDRESS);			
		stmtcustomeraddress.setString(1, cutomerAddress.getAddress());
		stmtcustomeraddress.setString(2, cutomerAddress.getCustArea());
		stmtcustomeraddress.setString(3, cutomerAddress.getCustCity());
		stmtcustomeraddress.setString(4, cutomerAddress.getCustCountry());
		stmtcustomeraddress.setString(5, cutomerAddress.getCustPh());
		stmtcustomeraddress.setString(6, cutomerAddress.getCustState());
		stmtcustomeraddress.setLong(7, custId);
		stmtcustomeraddress.setString(8, cutomerAddress.getEmail());
		stmtcustomeraddress.executeUpdate();
		stmtcustomeraddress.close();

	}


	public void inserCustomerCategories(CustomerDetails transientInstance,Connection con,Long custId) throws SQLException{
		PreparedStatement stmtcustomerCategories=null;
		stmtcustomerCategories= con.prepareStatement(INSERT_CUSTOMER_CATEGORIES);
		//SellerCategories sellerCategories = transientInstance.getSellerCategorieses().iterator().next();
		for(CustomerCategories customerCategories : transientInstance.getCustCategoriesCollection()){
			stmtcustomerCategories.setLong(1, custId);
			stmtcustomerCategories.setLong(2, customerCategories.getCategories().getId());
			stmtcustomerCategories.executeUpdate();

		}
		stmtcustomerCategories.close();
	}
	public void inserCustomerInterest(CustomerDetails transientInstance,Connection con,Long custId) throws SQLException{
		PreparedStatement stmtSellerInterests=null;
		stmtSellerInterests= con.prepareStatement(INSERT_CUSTOMER_INTEREST);
		//SellerCategories sellerCategories = transientInstance.getSellerCategorieses().iterator().next();
		for(CustomerInterests customerInterests : transientInstance.getCustomerInterests()){
			stmtSellerInterests.setLong(1, custId);
			stmtSellerInterests.setLong(2, customerInterests.getMasterArea().getId());
			//stmtSellerInterests.setLong(3, customerInterests.getCategories().getId());
			stmtSellerInterests.executeUpdate();

		}
		stmtSellerInterests.close();


	}


	public boolean findSellerByPhone(String phoneNo,Connection con) throws CustomerException {
		boolean bFound = false;


		PreparedStatement stmtselect = null;
		ResultSet rs = null;
		LOGGER.debug("Find SellerDetails By Phone");
		try {
			//con = MySqlConnectionUtils.getConnection();

			con.setAutoCommit(false);
			System.out.println("Select Query>>" + CHECK_MOBILE_NO);
			stmtselect = con.prepareStatement(CHECK_MOBILE_NO);
			stmtselect.setString(1, phoneNo);
			rs=stmtselect.executeQuery();
			while (rs.next()) {
				bFound = true;
			}
		} catch (Exception re) {
			LOGGER.error("Error occured while finding seller details", re);
			throw new CustomerException("Error occured while finding seller details", re);
		} 
		return bFound;
	}




}
