package com.hcl.training.entity;

import java.util.Date;

import javax.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

public class BaseEntity {

	@Column(name="status")
	private boolean status;

	@Column(name="created_by")
	private String createdBy;
	
	@CreationTimestamp
	@Column(name="created_date")
	private Date createdDate;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
