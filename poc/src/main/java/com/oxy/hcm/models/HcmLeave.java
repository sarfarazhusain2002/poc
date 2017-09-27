package com.oxy.hcm.models;

import java.io.Serializable;
import java.util.List;


public class HcmLeave implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int id;

	
	private String leaveType;

	
	private int noOfLeaves;

	private String year;

	private List<HcmLeaveTransaction> hcmLeaveTransactions;

	public HcmLeave() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLeaveType() {
		return this.leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public int getNoOfLeaves() {
		return this.noOfLeaves;
	}

	public void setNoOfLeaves(int noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<HcmLeaveTransaction> getHcmLeaveTransactions() {
		return this.hcmLeaveTransactions;
	}

	public void setHcmLeaveTransactions(List<HcmLeaveTransaction> hcmLeaveTransactions) {
		this.hcmLeaveTransactions = hcmLeaveTransactions;
	}

	public HcmLeaveTransaction addHcmLeaveTransaction(HcmLeaveTransaction hcmLeaveTransaction) {
		getHcmLeaveTransactions().add(hcmLeaveTransaction);
		hcmLeaveTransaction.setHcmLeave(this);

		return hcmLeaveTransaction;
	}

	public HcmLeaveTransaction removeHcmLeaveTransaction(HcmLeaveTransaction hcmLeaveTransaction) {
		getHcmLeaveTransactions().remove(hcmLeaveTransaction);
		hcmLeaveTransaction.setHcmLeave(null);

		return hcmLeaveTransaction;
	}

}