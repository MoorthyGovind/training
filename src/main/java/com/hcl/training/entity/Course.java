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
public class Course extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="course_code")
	private String courseCode;
	
	@Column(name="course_name")
	private String courseName;
	
	@Column(name="course_duration")
	private int courseDuration;

	@Column(name="duration_type")
	private String durationType;
	
	@Column(name="course_detail")
	private String courseDetail;

	@OneToMany(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "courseId")
    private Set<CourseEnroll> courseEnroll;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(int courseDuration) {
		this.courseDuration = courseDuration;
	}

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public String getCourseDetail() {
		return courseDetail;
	}

	public void setCourseDetail(String courseDetail) {
		this.courseDetail = courseDetail;
	}

	public void setCourseEnroll(Set<CourseEnroll> courseEnroll) {
		this.courseEnroll = courseEnroll;
	}

}
