package com.oxy.hcm.services.employee.impl;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxy.hcm.dao.employee.EmployeeDao;
import com.oxy.hcm.dto.Employee;
import com.oxy.hcm.services.employee.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	@Resource(name = "employeeDao")
	private EmployeeDao employeeDao;

	@Override
	public Employee getEmpDetails(long empId) {
		if (empId == 0) {
			return null;
		}

		Employee employee = null;

		try {
			com.oxy.hcm.models.Employee emp = employeeDao.getEmpDetails(empId);
			employee = new Employee();
			BeanUtils.copyProperties(employee, emp);
		} catch (Exception e) {
			System.out.println(e);
		}
		return employee;
	}

}
