package com.hcl.training.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

	@NotBlank(message = "UserID should be mandatory")
	@Email(message = "Invalid Email address")
	private String userId;
	
	@NotBlank(message = "Password should be mandatory")
	private String password;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
