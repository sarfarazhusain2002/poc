package com.oxy.hcm.models;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the hcm_leave_transaction database table.
 * 
 */
public class HcmLeaveTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	private int transactionId;
	
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	private Date endDate;

	private String noOfLeaves;

	private String remarks;

	
	private Date starDate;

	private String status;
	
	private String leaveType;

	
	//private HcmEmp hcmEmp;

	
	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	private HcmLeave hcmLeave;

	public HcmLeaveTransaction() {
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNoOfLeaves() {
		return this.noOfLeaves;
	}

	public void setNoOfLeaves(String noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getStarDate() {
		return this.starDate;
	}

	public void setStarDate(Date starDate) {
		this.starDate = starDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/*public HcmEmp getHcmEmp() {
		return this.hcmEmp;
	}

	public void setHcmEmp(HcmEmp hcmEmp) {
		this.hcmEmp = hcmEmp;
	}*/

	public HcmLeave getHcmLeave() {
		return this.hcmLeave;
	}

	public void setHcmLeave(HcmLeave hcmLeave) {
		this.hcmLeave = hcmLeave;
	}

}