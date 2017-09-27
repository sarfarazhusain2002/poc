package com.oxy.hcm.services.leaves;

import java.util.List;

import com.oxy.hcm.dto.LeaveType;
import com.oxy.hcm.dto.Leaves;

public interface LeavesService {
	boolean applyLeave(final Leaves leaves);

	List<Leaves> viewBalance(final long empId);

	List<Leaves> viewHistory(final long empId);
	
	List<LeaveType> getLeaveTypes();
	
	int getLeaveBalanceByLeaveType(final long empId, final int leaveType);
}
