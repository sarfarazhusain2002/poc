package com.oxy.hcm.dto;

import java.util.List;

public class Employee {
	private long empId;
	private String name;
	private String email;
	private String phoneNo;
	private long roleId;
	private String roleName;
	private long superVisorId;
	private String superVisorName;
	private String imageUrl;
	private List<Leaves> leaves;

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public long getSuperVisorId() {
		return superVisorId;
	}

	public void setSuperVisorId(long superVisorId) {
		this.superVisorId = superVisorId;
	}

	public String getSuperVisorName() {
		return superVisorName;
	}

	public void setSuperVisorName(String superVisorName) {
		this.superVisorName = superVisorName;
	}

	public List<Leaves> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leaves> leaves) {
		this.leaves = leaves;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
