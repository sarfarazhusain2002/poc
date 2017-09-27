package com.oxy.hcm.dao.employee.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.oxy.hcm.dao.employee.EmployeeDao;
import com.oxy.hcm.models.Employee;
import com.oxy.hcm.utils.connections.MySqlConnectionUtils;

@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

	public static final String QRY_EMP_DETAILS = "select e.emp_id, e.name, e.email, e.phoneNo, e.role_id, e.image_url, r.role_name, m.sup_emp_id from hcm_emp e, hcm_emp_mapping m, hcm_role r where (e.emp_id=m.emp_id or m.sup_emp_id=e.emp_id)  and e.role_id=r.role_id and e.emp_id=?";

	@Override
	public Employee getEmpDetails(final long empId) {

		if (empId == 0) {
			return null;
		}

		Employee employee = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = MySqlConnectionUtils.getConnection();
			stmt = con.prepareStatement(QRY_EMP_DETAILS);
			stmt.setLong(1, empId);
			rs = stmt.executeQuery();

			employee = new Employee();
			while (rs.next()) {
				employee.setEmpId(rs.getInt("emp_id"));
				employee.setName(rs.getString("name"));
				employee.setEmail(rs.getString("email"));
				employee.setPhoneNo(rs.getString("phoneNo"));
				employee.setRoleId(rs.getInt("role_id"));
				employee.setImageUrl(rs.getString("image_url"));
				employee.setRoleName(rs.getString("role_name"));
				employee.setSuperVisorId(rs.getInt("sup_emp_id"));
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

		return employee;
	}

}
