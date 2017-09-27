package com.oxy.hcm.models;

import java.io.Serializable;



/**
 * The persistent class for the hcm_emp_mapping database table.
 * 
 */
public class HcmEmpMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int empId;

	
	private int supEmpId;

	public HcmEmpMapping() {
	}

	public int getEmpId() {
		return this.empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getSupEmpId() {
		return this.supEmpId;
	}

	public void setSupEmpId(int supEmpId) {
		this.supEmpId = supEmpId;
	}

}