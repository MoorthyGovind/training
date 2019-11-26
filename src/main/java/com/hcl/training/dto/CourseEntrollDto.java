package com.hcl.training.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CourseEntrollDto {

	@NotBlank(message="userId is mandatory")
	@Email
	private String userId;

	@NotBlank(message="courseCode is mandatory")
	private String courseCode;
	
	private String remarks;
	
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
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
