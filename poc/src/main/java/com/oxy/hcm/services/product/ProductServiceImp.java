package com.oxy.hcm.services.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxy.hcm.dao.product.ProductDao;
import com.oxy.hcm.dao.supervisor.SupervisorDAO;
import com.oxy.hcm.dto.Product;
import com.oxy.hcm.dto.ResponseTransaction;
import com.oxy.hcm.dto.TransactionDetails;
import com.oxy.hcm.utils.connections.MySqlConnectionUtils;

@Service("productService")
public class ProductServiceImp implements ProductService {

	@Autowired
	@Resource(name = "productDao")
	private ProductDao productDao;


	@Override
	public Product getProduct(long productId) {

		return productDao.getProduct(productId);
	}


	@Override
	public Product addProduct(Product product) {		 
		if(!productDao.addProduct(product))
			product=null;

		return product;
	}


	@Override
	public ResponseTransaction addTransaction(List<TransactionDetails> lsttransactionDetails) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResponseTransaction responseTransaction= new ResponseTransaction();
		try{
			con = MySqlConnectionUtils.getConnection();
			System.out.println("Insert Query>>" + "select max(transaction_id) from product_transaction_details");
			stmt = con.prepareStatement("select max(transaction_id) transaction_id from product_transaction_details");
			rs=stmt.executeQuery();
			rs.next();
			for(TransactionDetails transactionDetails:lsttransactionDetails){
				System.out.println("inside for loop "+rs.getString("transaction_id"));
				transactionDetails.setTransaction_id(rs.getLong("transaction_id")+1);
				responseTransaction.setOrder_id(String.valueOf(rs.getLong("transaction_id")+1));
				productDao.addTransactionDetails(transactionDetails);

			}
			//flag=true;
		}catch (Exception e) {
			System.out.println(e);
			responseTransaction.setStatus(e.toString());
			return responseTransaction;
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
				responseTransaction.setStatus(e.toString());
				return responseTransaction;
			}

		}

		//System.out.println("Flag>>>"+flag);
		responseTransaction.setStatus("Success");
		return responseTransaction;
	}


	@SuppressWarnings("finally")
	@Override
	public List<TransactionDetails> getTransactionDetails() {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<TransactionDetails> lstTransactionDetails = new ArrayList<TransactionDetails>();
		try{
			con = MySqlConnectionUtils.getConnection();
			System.out.println("Insert Query>>" + "select *, pd.quantity - ptd.quantity as remaining_stock from product_details pd , product_transaction_details ptd where ptd.product_id = pd.product_id and date(pd.product_date)<=date(ptd.transaction_date");
			stmt = con.prepareStatement("select *, pd.quantity - ptd.quantity as remaining_stock from product_details pd , product_transaction_details ptd where ptd.product_id = pd.product_id and date(pd.product_date)<=date(ptd.transaction_date)");
			rs=stmt.executeQuery();

			while (rs.next()) {
				TransactionDetails transactionDetails = new TransactionDetails();
				transactionDetails.setCurr(rs.getString("curr"));
				transactionDetails.setDiscount(rs.getString("discount"));
				transactionDetails.setDiscount_price(rs.getString("discount_price"));
				transactionDetails.setQuantity(rs.getString("remaining_stock"));
				transactionDetails.setProductName(rs.getString("product_name"));
				lstTransactionDetails.add(transactionDetails);

			}


		}catch (Exception e) {
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

			} catch (final SQLException e) {
				e.printStackTrace();
				//return flag;
			}

			// TODO Auto-generated method stub
			return lstTransactionDetails;
		}

	}
}