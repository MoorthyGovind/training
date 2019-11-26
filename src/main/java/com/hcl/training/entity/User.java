package com.hcl.training.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="password")
	private String password;

	@OneToMany(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "userId")
    private Set<CourseEnroll> courseEnroll;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
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
	
	public Set<CourseEnroll> getCourseEnroll() {
		return courseEnroll;
	}

	public void setCourseEnroll(Set<CourseEnroll> courseEnroll) {
		this.courseEnroll = courseEnroll;
	}

}
