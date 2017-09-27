package com.oxy.hcm.services.supervisor.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxy.hcm.dao.supervisor.SupervisorDAO;
import com.oxy.hcm.models.HcmEmp;
import com.oxy.hcm.services.supervisor.SupervisorService;

@Service("supervisorService")
public class SupervisorServiceImpl implements SupervisorService {

	@Autowired
	@Resource(name = "supervisorDao")
	private SupervisorDAO supervisorDao;
	
	@Override
	public List<HcmEmp> getEmpList(final long empId) {
		return  supervisorDao.getEmpList(empId);
	}

	@Override
	public void Approval(int leaveId, String status) {
		supervisorDao.Approval(leaveId,status);

	}

}
