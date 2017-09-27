package com.oxy.hcm.dao.supervisor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oxy.hcm.models.HcmEmp;



@Component
public interface SupervisorDAO {
	
	public List<HcmEmp> getEmpList(final long empId);
    
    public void Approval(int leaveId, String status);
     
    public HcmEmp get(int empId);
     
    public List<HcmEmp> list();

}
