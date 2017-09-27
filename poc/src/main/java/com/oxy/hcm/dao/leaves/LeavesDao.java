package com.oxy.hcm.dao.leaves;

import java.util.List;

import com.oxy.hcm.models.LeaveType;
import com.oxy.hcm.models.Leaves;

public interface LeavesDao {

	boolean applyLeave(final Leaves leaves);

	List<Leaves> viewBalance(final long empId);

	List<Leaves> viewHistory(final long empId);
	
	int getLeaveBalanceByLeaveType(final long empId, final int leaveType);
	
	List<LeaveType> getLeaveTypes();
}
