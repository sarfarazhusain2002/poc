package com.oxy.hcm.services.supervisor;

import java.util.List;

import com.oxy.hcm.dto.LeaveType;
import com.oxy.hcm.dto.Leaves;
import com.oxy.hcm.models.HcmEmp;

public interface SupervisorService {
	

	List<HcmEmp> getEmpList(final long empId);

	void Approval(int leaveId,String status);
}
