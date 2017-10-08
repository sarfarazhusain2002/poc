package com.oxy.s3m.notification.DAO.seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oxy.s3m.connection.utils.CustMySqlConnectionUtils;
import com.oxy.s3m.notification.exception.SellerException;
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

}
