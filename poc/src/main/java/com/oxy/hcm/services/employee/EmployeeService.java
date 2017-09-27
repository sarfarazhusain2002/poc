package com.oxy.hcm.services.employee;

import com.oxy.hcm.dto.Employee;

public interface EmployeeService {
	
	Employee getEmpDetails(final long empId);
	
}
