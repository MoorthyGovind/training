package com.hcl.training.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ViewCourseEnrollDto {

	private String userId;

	private String courseCode;
	private String courseName;
	private String remarks;
	
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date startDate;

	@JsonFormat(pattern="dd-MM-yyyy")
	private Date endDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
