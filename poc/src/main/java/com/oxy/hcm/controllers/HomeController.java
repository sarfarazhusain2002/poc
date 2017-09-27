package com.oxy.hcm.controllers;

import java.util.List;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oxy.hcm.dto.Employee;
import com.oxy.hcm.dto.LeaveType;
import com.oxy.hcm.services.employee.EmployeeService;
import com.oxy.hcm.services.leaves.LeavesService;

@Controller
public class HomeController {

	@Autowired
	@Resource(name = "employeeService")
	private EmployeeService employeeService;

	@Autowired
	@Resource(name = "leavesService")
	private LeavesService leavesService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		final String empId = request.getParameter("empId");
		final Employee emp = employeeService.getEmpDetails(Long.valueOf(empId));
		request.setAttribute("employee", emp);

		final List<LeaveType> leaveTypes = leavesService.getLeaveTypes();
		request.setAttribute("leaveTypes", leaveTypes);

		return "home";
	}
}
