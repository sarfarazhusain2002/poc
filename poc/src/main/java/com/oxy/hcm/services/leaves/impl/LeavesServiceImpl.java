package com.oxy.hcm.services.leaves.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxy.hcm.dao.leaves.LeavesDao;
import com.oxy.hcm.dto.LeaveType;
import com.oxy.hcm.dto.Leaves;
import com.oxy.hcm.services.leaves.LeavesService;

@Service("leavesService")
public class LeavesServiceImpl implements LeavesService {

	@Autowired
	@Resource(name = "leavesDao")
	private LeavesDao leavesDao;

	@Override
	public boolean applyLeave(final Leaves leaves) {

		boolean bInserted = false;
		try {
			final com.oxy.hcm.models.Leaves leavesM = new com.oxy.hcm.models.Leaves();
			BeanUtils.copyProperties(leavesM, leaves);
			bInserted = leavesDao.applyLeave(leavesM);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bInserted;
	}

	@Override
	public List<Leaves> viewBalance(final long empId) {
		List<Leaves> leaves = null;
		try {

			final List<com.oxy.hcm.models.Leaves> tLeaves = leavesDao.viewBalance(empId);
			if (CollectionUtils.isNotEmpty(tLeaves)) {

				leaves = new ArrayList<Leaves>();

				for (com.oxy.hcm.models.Leaves l : tLeaves) {
					final Leaves leave = new Leaves();
					BeanUtils.copyProperties(leave, l);
					leaves.add(leave);
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return leaves;
	}

	@Override
	public List<Leaves> viewHistory(final long empId) {
		List<Leaves> leaves = null;
		try {

			final List<com.oxy.hcm.models.Leaves> tLeaves = leavesDao.viewHistory(empId);
			if (CollectionUtils.isNotEmpty(tLeaves)) {

				leaves = new ArrayList<Leaves>();

				for (com.oxy.hcm.models.Leaves l : tLeaves) {
					final Leaves leave = new Leaves();
					BeanUtils.copyProperties(leave, l);
					leaves.add(leave);
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return leaves;
	}

	@Override
	public List<LeaveType> getLeaveTypes() {

		List<LeaveType> leaveTypes = null;
		try {
			List<com.oxy.hcm.models.LeaveType> lTypes = leavesDao.getLeaveTypes();
			if (CollectionUtils.isNotEmpty(lTypes)) {
				leaveTypes = new ArrayList<LeaveType>();

				for (com.oxy.hcm.models.LeaveType lType : lTypes) {
					final LeaveType leaveType = new LeaveType();
					BeanUtils.copyProperties(leaveType, lType);
					leaveTypes.add(leaveType);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveTypes;
	}

	@Override
	public int getLeaveBalanceByLeaveType(final long empId, final int leaveType) {
		int balanceLeaves = 0;

		try {
			balanceLeaves = leavesDao.getLeaveBalanceByLeaveType(empId, leaveType);
		} catch (Exception e) {
			System.out.println(e);
		}
		return balanceLeaves;
	}

}
