package com.oxy.hcm.models;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the hcm_emp database table.
 * 
 */

public class HcmEmp implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int empId;
	private String email;
	private String name;
	private String phoneNo;
	private String imageUrl;
	private HcmRole hcmRole;
	private List<HcmLeaveTransaction> hcmLeaveTransactions;

	public HcmEmp(int empId,String email, String name,String phoneNo,String imageUrl, List<HcmLeaveTransaction> hcmLeaveTransactions) {
		this.empId=empId;
		this.email=email;
		this.name=name;
		this.phoneNo=phoneNo;
		this.imageUrl = imageUrl;
		this.hcmLeaveTransactions=hcmLeaveTransactions;
	}

	public int getEmpId() {
		return this.empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public HcmRole getHcmRole() {
		return this.hcmRole;
	}

	public void setHcmRole(HcmRole hcmRole) {
		this.hcmRole = hcmRole;
	}

	public List<HcmLeaveTransaction> getHcmLeaveTransactions() {
		return this.hcmLeaveTransactions;
	}

	public void setHcmLeaveTransactions(List<HcmLeaveTransaction> hcmLeaveTransactions) {
		this.hcmLeaveTransactions = hcmLeaveTransactions;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/*public HcmLeaveTransaction addHcmLeaveTransaction(HcmLeaveTransaction hcmLeaveTransaction) {
		getHcmLeaveTransactions().add(hcmLeaveTransaction);
		hcmLeaveTransaction.setHcmEmp(this);

		return hcmLeaveTransaction;
	}

	public HcmLeaveTransaction removeHcmLeaveTransaction(HcmLeaveTransaction hcmLeaveTransaction) {
		getHcmLeaveTransactions().remove(hcmLeaveTransaction);
		hcmLeaveTransaction.setHcmEmp(null);

		return hcmLeaveTransaction;
	}*/

}