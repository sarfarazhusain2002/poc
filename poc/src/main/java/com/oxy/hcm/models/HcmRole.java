package com.oxy.hcm.models;

import java.io.Serializable;

import java.util.List;



public class HcmRole implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int roleId;

	private String roleName;

	
	private List<HcmEmp> hcmEmps;

	public HcmRole() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<HcmEmp> getHcmEmps() {
		return this.hcmEmps;
	}

	public void setHcmEmps(List<HcmEmp> hcmEmps) {
		this.hcmEmps = hcmEmps;
	}

	public HcmEmp addHcmEmp(HcmEmp hcmEmp) {
		getHcmEmps().add(hcmEmp);
		hcmEmp.setHcmRole(this);

		return hcmEmp;
	}

	public HcmEmp removeHcmEmp(HcmEmp hcmEmp) {
		getHcmEmps().remove(hcmEmp);
		hcmEmp.setHcmRole(null);

		return hcmEmp;
	}

}