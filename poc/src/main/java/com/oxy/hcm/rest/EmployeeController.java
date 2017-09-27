package com.oxy.hcm.rest;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxy.hcm.dto.Employee;
import com.oxy.hcm.services.employee.EmployeeService;

@RestController
@RequestMapping("/rest")
public class EmployeeController {

	@Autowired
	@Resource(name = "employeeService")
	private EmployeeService employeeService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		String result = null;
		try {
			long empId = Long.valueOf(username);
			Employee employee = employeeService.getEmpDetails(empId);

			if (employee != null && empId == employee.getEmpId()) {
				result = "Success";
			} else {
				result = "Login failed. User name or password is incorrect.";
			}

		} catch (Exception e) {
			System.out.println(e);
			result = "Login failed. User name or password is incorrect.";
		}

		return result;
	}

	@RequestMapping(value = "/empDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Employee getEmployeeDetails(@RequestParam("empId") long empId) {
		Employee employee = null;

		try {
			employee = employeeService.getEmpDetails(empId);
		} catch (Exception e) {
			System.out.println(e);
		}

		return employee;
	}
}
