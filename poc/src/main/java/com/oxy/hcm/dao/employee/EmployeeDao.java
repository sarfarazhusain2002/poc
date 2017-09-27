package com.oxy.hcm.dao.employee;

import com.oxy.hcm.models.Employee;


public interface EmployeeDao {
	Employee getEmpDetails(final long empId);
}
