package com.oxy.hcm.dto;

public class Leaves {
	private long empId;
	private int leaveTypeId;
	private String leaveType;
	private String startDate;
	private String endDate;
	private int duration;
	private String comments;
	private String status;
	private int totalLeavesTaken;
	private int totalLeavesBalance;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public int getTotalLeavesTaken() {
		return totalLeavesTaken;
	}

	public void setTotalLeavesTaken(int totalLeavesTaken) {
		this.totalLeavesTaken = totalLeavesTaken;
	}

	public int getTotalLeavesBalance() {
		return totalLeavesBalance;
	}

	public void setTotalLeavesBalance(int totalLeavesBalance) {
		this.totalLeavesBalance = totalLeavesBalance;
	}
}
