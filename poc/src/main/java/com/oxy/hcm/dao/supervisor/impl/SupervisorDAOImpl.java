package com.oxy.hcm.dao.supervisor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oxy.hcm.dao.supervisor.SupervisorDAO;
import com.oxy.hcm.models.HcmEmp;
import com.oxy.hcm.models.HcmLeaveTransaction;
import com.oxy.hcm.utils.connections.MySqlConnectionUtils;

@Repository("supervisorDao")
public class SupervisorDAOImpl implements SupervisorDAO {

	@Override
	public List<HcmEmp> getEmpList(final long empId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<HcmEmp> hcmLeaveList = new ArrayList<HcmEmp>();
		Connection con = MySqlConnectionUtils.getConnection();
		String sql = "select he.* from hcm_emp_mapping hem,hcm_emp he where hem.sup_emp_id=" + empId
				+ " and he.emp_id = hem.emp_id ";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				hcmLeaveList.add(new HcmEmp(rs.getInt("emp_id"), rs.getString("email"), rs.getString("name"),
						rs.getString("phoneNo"),rs.getString("image_url"), getLeaveTransactionList(rs.getInt("emp_id"))));

			}

		} catch (Exception e) {
			// Handle errors for JDBC
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return hcmLeaveList;
	}

	public List<HcmLeaveTransaction> getLeaveTransactionList(int empId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<HcmLeaveTransaction> hcmLeaveTransactionList = new ArrayList<HcmLeaveTransaction>();
		Connection con = MySqlConnectionUtils.getConnection();
		String sql = "select * from hcm_leave_transaction hlt where  emp_id =? and status='PENDING' ";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, empId);
			rs = ps.executeQuery();

			while (rs.next()) {
				HcmLeaveTransaction hcmLeaveTransaction = new HcmLeaveTransaction();
				hcmLeaveTransaction.setTransactionId(rs.getInt("transaction_id"));
				hcmLeaveTransaction.setEndDate(rs.getDate("end_date"));
				hcmLeaveTransaction.setStarDate(rs.getDate("star_date"));
				hcmLeaveTransaction.setRemarks(rs.getString("remarks"));
				hcmLeaveTransaction.setStatus(rs.getString("status"));
				hcmLeaveTransaction.setLeaveType(rs.getInt("id") == 1 ? "EARNED" : "SICK");
				hcmLeaveTransactionList.add(hcmLeaveTransaction);
			}

		} catch (Exception e) {
			// Handle errors for JDBC
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return hcmLeaveTransactionList;
	}

	@Override
	public void Approval(int leaveId, String status) {
		PreparedStatement ps = null;
		Connection con = MySqlConnectionUtils.getConnection();
		String sql = "update  hcm_leave_transaction set status=?  where  transaction_id =?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, leaveId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (ps != null) {
					ps.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public HcmEmp get(int empId) {
		return null;
	}

	@Override
	public List<HcmEmp> list() {
		return null;
	}

}
