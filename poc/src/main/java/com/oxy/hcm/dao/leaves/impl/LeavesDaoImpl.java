package com.oxy.hcm.dao.leaves.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.oxy.hcm.dao.leaves.LeavesDao;
import com.oxy.hcm.models.LeaveType;
import com.oxy.hcm.models.Leaves;
import com.oxy.hcm.utils.connections.MySqlConnectionUtils;

@Repository("leavesDao")
public class LeavesDaoImpl implements LeavesDao {

	public static final String QRY_LEAVE_TYPES_ALL = "SELECT id, leave_type, no_of_leaves, year FROM hcm_leave;";
	public static final String QRY_LEAVE_TOTAL = "SELECT hl.no_of_leaves FROM hcm_leave hl where hl.id = ?";
	public static final String QRY_LEAVE_TAKEN = "SELECT SUM(hlt.no_of_leaves) as leaves_taken FROM hcm_leave_transaction hlt where hlt.status='Approved' and hlt.id = ? and hlt.emp_id=?";
	public static final String QRY_APPLY_LEAVE = "INSERT INTO hcm_leave_transaction(emp_id, id, status, remarks, no_of_leaves, star_date, end_date) values(?,?,?,?,?,?,?)";
	public static final String QRY_LEAVE_BALANCE = "SELECT e.emp_id, e.name, l.id as leave_type_id, l.leave_type, l.no_of_leaves from hcm_emp e JOIN hcm_leave l where e.emp_id=? group by l.id, e.emp_id order by e.emp_id";
	public static final String QRY_LEAVE_TAKEN_SUM = "select SUM(hlt.no_of_leaves) as total_leaves, hlt.id as leave_type_id, hlt.emp_id from  hcm_leave_transaction hlt where hlt.status='Approved' and hlt.emp_id=? group by hlt.id";
	public static final String QRY_LEAVE_HISTORY = "select hlt.emp_id, hlt.id as leave_type_id, hl.leave_type, hlt.status, hlt.remarks, hlt.no_of_leaves, hlt.star_date, hlt.end_date from hcm_leave_transaction hlt, hcm_leave hl where hlt.id=hl.id and hlt.emp_id =?;";

	final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public boolean applyLeave(final Leaves leaves) {
		boolean bInserted = false;

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = MySqlConnectionUtils.getConnection();
			System.out.println("Insert Query>>" + QRY_APPLY_LEAVE);
			stmt = con.prepareStatement(QRY_APPLY_LEAVE);
			System.out.println(leaves.getEmpId());
			System.out.println(leaves.getLeaveTypeId());
			stmt.setLong(1, leaves.getEmpId());
			stmt.setLong(2, Long.valueOf(leaves.getLeaveTypeId()));
			stmt.setString(3, "PENDING");
			stmt.setString(4, leaves.getComments());
			stmt.setLong(5, leaves.getDuration());
			stmt.setDate(6, convertDate(leaves.getStartDate()));
			stmt.setDate(7, convertDate(leaves.getEndDate()));
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

	private java.sql.Date convertDate(final String date) {

		java.sql.Date sqlDate = null;
		try {
			java.util.Date parsed = format.parse(date);
			sqlDate = new java.sql.Date(parsed.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlDate;
	}

	@Override
	public List<Leaves> viewBalance(final long empId) {

		List<Leaves> leaves = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = MySqlConnectionUtils.getConnection();

			stmt = con.prepareStatement(QRY_LEAVE_BALANCE);
			stmt.setLong(1, empId);
			rs = stmt.executeQuery();
			leaves = new ArrayList<Leaves>();
			while (rs.next()) {
				final Leaves leave = new Leaves();
				leave.setLeaveTypeId(rs.getInt("leave_type_id"));
				leave.setLeaveType(rs.getString("leave_type"));
				int totalLeaves = rs.getInt("no_of_leaves");
				leave.setTotalLeavesBalance(totalLeaves);
				// int leaveTaken = rs.getInt("total_leaves");
				// leave.setTotalLeavesTaken(leaveTaken);
				// leave.setTotalLeavesBalance(totalLeaves - leaveTaken);
				leaves.add(leave);
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

		Connection con2 = null;
		PreparedStatement stmt2 = null;
		ResultSet rs2 = null;

		try {
			con2 = MySqlConnectionUtils.getConnection();

			stmt2 = con2.prepareStatement(QRY_LEAVE_TAKEN_SUM);
			stmt2.setLong(1, empId);
			rs2 = stmt2.executeQuery();
			while (rs2.next()) {
				int total_leave_taken = rs2.getInt("total_leaves");
				int leave_type_id = rs2.getInt("leave_type_id");

				if (CollectionUtils.isNotEmpty(leaves)) {
					for (Leaves leave : leaves) {
						if (leave.getLeaveTypeId() == leave_type_id) {
							leave.setTotalLeavesTaken(total_leave_taken);
							leave.setTotalLeavesBalance(leave.getTotalLeavesBalance() - total_leave_taken);
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs2 != null) {
					rs2.close();
				}
				if (stmt2 != null) {
					stmt2.close();
				}
				if (con2 != null && !con2.isClosed()) {
					con2.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return leaves;
	}

	@Override
	public List<Leaves> viewHistory(long empId) {
		List<Leaves> leaves = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = MySqlConnectionUtils.getConnection();

			stmt = con.prepareStatement(QRY_LEAVE_HISTORY);
			stmt.setLong(1, empId);
			rs = stmt.executeQuery();
			
			leaves = new ArrayList<Leaves>();
			while (rs.next()) {
				final Leaves leave = new Leaves();
				leave.setEmpId(rs.getLong("emp_id"));
				leave.setLeaveTypeId(rs.getInt("leave_type_id"));
				leave.setLeaveType(rs.getString("leave_type"));
				leave.setStatus(rs.getString("status"));
				leave.setComments(rs.getString("remarks"));
				leave.setTotalLeavesTaken(rs.getInt("no_of_leaves"));
				leave.setStartDate(format.format(rs.getDate("star_date")));
				leave.setEndDate(format.format(rs.getDate("end_date")));
				leaves.add(leave);
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
		return leaves;
	}

	@Override
	public List<LeaveType> getLeaveTypes() {

		List<LeaveType> leaveTypes = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = MySqlConnectionUtils.getConnection();
			stmt = con.prepareStatement(QRY_LEAVE_TYPES_ALL);
			rs = stmt.executeQuery();

			leaveTypes = new ArrayList<LeaveType>();
			while (rs.next()) {
				final LeaveType leaveType = new LeaveType();
				// id, leave_type, no_of_leaves, year
				leaveType.setId(rs.getInt("id"));
				leaveType.setType(rs.getString("leave_type"));
				leaveType.setNoOfLeaves(rs.getInt("no_of_leaves"));
				leaveType.setYear(rs.getInt("year"));
				leaveTypes.add(leaveType);
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
		return leaveTypes;
	}

	@Override
	public int getLeaveBalanceByLeaveType(final long empId, final int leaveType) {
		if (empId == 0 || leaveType == 0) {
			return 0;
		}

		int totalLeaves = getTotalLeaves(empId, leaveType);
		int takenLeaves = getLeavesTaken(empId, leaveType);

		return totalLeaves - takenLeaves;
	}

	private int getTotalLeaves(final long empId, final int leaveType) {
		if (empId == 0 || leaveType == 0) {
			return 0;
		}
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int totalLeaves = 0;
		try {
			con = MySqlConnectionUtils.getConnection();
			stmt = con.prepareStatement(QRY_LEAVE_TOTAL);
			stmt.setInt(1, leaveType);
			rs = stmt.executeQuery();

			while (rs.next()) {
				totalLeaves = rs.getInt("no_of_leaves");
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
		return totalLeaves;
	}

	private int getLeavesTaken(final long empId, final int leaveType) {
		if (empId == 0 || leaveType == 0) {
			return 0;
		}
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int takenLeaves = 0;
		try {
			con = MySqlConnectionUtils.getConnection();
			stmt = con.prepareStatement(QRY_LEAVE_TAKEN);
			stmt.setInt(1, leaveType);
			stmt.setInt(2, Integer.parseInt(empId + ""));
			rs = stmt.executeQuery();

			while (rs.next()) {
				takenLeaves = rs.getInt("leaves_taken");
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
		return takenLeaves;
	}
}