package com.oxy.hcm.dao.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.oxy.hcm.dto.Product;
import com.oxy.hcm.dto.TransactionDetails;
import com.oxy.hcm.models.Employee;
import com.oxy.hcm.utils.connections.MySqlConnectionUtils;

@Repository("productDao")
public class ProductDaoImpl implements ProductDao {
	
	public static final String PRODUCT_DETAILS = "select * from  product_details where product_id=?";
	public static final String ADD_PRODUCT = "INSERT INTO product_details(product_id, product_name, price, discount, quantity, curr) values(?,?,?,?,?,?)";
	public static final String ADD_TRANSACTION = "INSERT INTO product_transaction_details(transaction_id,product_id,price,discount,quantity,curr,final_price,discount_price,Payment_transaction_id) values(?,?,?,?,?,?,?,?,?)";


	@Override
	public Product getProduct(long productId) {
		if (productId == 0) {
			return null;
		}

		Product product = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = MySqlConnectionUtils.getConnection();
			stmt = con.prepareStatement(PRODUCT_DETAILS);
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();

			product = new Product();
			while (rs.next()) {
				product.setProduct_name(rs.getString("product_name"));
				product.setPrice(rs.getString("price"));
				product.setDiscount(rs.getString("discount"));
				product.setQuantity(rs.getString("quantity"));
				product.setProduct_id(Long.valueOf(rs.getInt("product_id")));
				product.setCurr(rs.getString("curr"));
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return product;

	}


	@Override
	public boolean addProduct(Product product) {
		
		boolean bInserted=false;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = MySqlConnectionUtils.getConnection();
			System.out.println("Insert Query>>" + ADD_PRODUCT);
			stmt = con.prepareStatement(ADD_PRODUCT);
			System.out.println(product.getProduct_id());
			System.out.println(product.getProduct_name());
			stmt.setLong(1, product.getProduct_id());
			stmt.setString(2, product.getProduct_name());
			stmt.setString(3, product.getPrice());
			stmt.setString(4, product.getDiscount()!=null && !product.getDiscount().equals("")?product.getDiscount():"0");
			stmt.setString(5, "10");
			stmt.setString(6, "DHR");
			
			int r = stmt.executeUpdate();
			if (r == 1) {
				bInserted = true;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return bInserted;

	}


	@Override
	public boolean addTransactionDetails(TransactionDetails transactionDetails) {
		boolean bInserted=false;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = MySqlConnectionUtils.getConnection();
			System.out.println("Insert Query>>" + ADD_TRANSACTION);
			stmt = con.prepareStatement(ADD_TRANSACTION);
			System.out.println(transactionDetails.getTransaction_id());
			System.out.println(transactionDetails.getProduct_id());
			stmt.setLong(1,transactionDetails.getTransaction_id());
			stmt.setLong(2,transactionDetails.getProduct_id());
			stmt.setString(3,transactionDetails.getPrice());
			stmt.setString(4,transactionDetails.getDiscount()!=null && !transactionDetails.getDiscount().equals("")?transactionDetails.getDiscount():"0");
			stmt.setString(5,transactionDetails.getQuantity());
			stmt.setString(6,transactionDetails.getCurr());
			stmt.setString(7,transactionDetails.getFinal_price());
			stmt.setString(8,transactionDetails.getDiscount_price());
			stmt.setString(9,transactionDetails.getPayment_transaction_id());
			int r = stmt.executeUpdate();
			if (r == 1) {
				bInserted = true;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return bInserted;

	}

}
