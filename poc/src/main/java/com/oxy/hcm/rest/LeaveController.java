package com.oxy.hcm.rest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxy.hcm.dto.LeaveType;
import com.oxy.hcm.dto.Leaves;
import com.oxy.hcm.services.leaves.LeavesService;

@RestController
@RequestMapping("/rest")
public class LeaveController {

	@Autowired
	@Resource(name = "leavesService")
	private LeavesService leavesService;

	@RequestMapping(value = "/getLeaveTypes", method = RequestMethod.GET)
	@ResponseBody
	public List<LeaveType> getLeaveTypes() {
		return leavesService.getLeaveTypes();
	}
	
	
	
	@RequestMapping(value = "/applyLeaves", method = RequestMethod.POST)
	@ResponseBody
	public String applyLeaves(@ModelAttribute Leaves leaves) {

		if (leaves.getStartDate() == null || leaves.getStartDate() == "") {
			return "Please select Start Date.";
		}

		if (leaves.getEndDate() == null || leaves.getEndDate() == "") {
			return "Please select End Date.";
		}

		leavesService.applyLeave(leaves);

		return "Leave request submitted.";
	}

	@RequestMapping(value = "/getLeaveBalanceByLeaveType", method = RequestMethod.GET)
	@ResponseBody
	public int getBalanceLeaves(@RequestParam("empId") long empId, @RequestParam("leaveType") int leaveType) {
		int balanceLeaves = 0;
		balanceLeaves = leavesService.getLeaveBalanceByLeaveType(Long.valueOf(empId), leaveType);
		return balanceLeaves;
	}

	@RequestMapping(value = "/viewBalanceLeaves", method = RequestMethod.GET)
	@ResponseBody
	public List<Leaves> viewBalanceLeaves(@RequestParam("empId") long empId) {
		return leavesService.viewBalance(Long.valueOf(empId));
	}

	@RequestMapping(value = "/viewHistory", method = RequestMethod.GET)
	@ResponseBody
	public List<Leaves> viewHistory(@RequestParam("empId") long empId) {
		return leavesService.viewHistory(Long.valueOf(empId));
	}
}
