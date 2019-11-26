package com.hcl.training.util;

import java.util.Calendar;
import java.util.Date;

import com.hcl.training.dto.ViewCourseDto;
import com.hcl.training.dto.ViewCourseEnrollDto;
import com.hcl.training.entity.Course;
import com.hcl.training.entity.CourseEnroll;

public class ConverterUtil {

	private ConverterUtil(){
	}
	
	/**
	 * convertCourseDto -> entity values set to the dto model
	 * @param course
	 * @return
	 */
	public static ViewCourseDto convertCourseDto(Course course) {
		ViewCourseDto courseDto = new ViewCourseDto();
		courseDto.setCourseCode(course.getCourseCode());
		courseDto.setCourseName(course.getCourseName());
		courseDto.setCourseDuration(course.getCourseDuration()+" "+course.getDurationType());
		courseDto.setCourseDetail(course.getCourseDetail());
		return courseDto;
	}
	
	/**
	 * 
	 * @param courseEnroll
	 * @return
	 */
	public static ViewCourseEnrollDto convertCourseEnrollDto(CourseEnroll courseEnroll) {
		ViewCourseEnrollDto courseEnrollDto = new ViewCourseEnrollDto();
		courseEnrollDto.setCourseCode(courseEnroll.getCourseId().getCourseCode());
		courseEnrollDto.setCourseName(courseEnroll.getCourseId().getCourseName());
		courseEnrollDto.setStartDate(courseEnroll.getStartDate());
		courseEnrollDto.setEndDate(courseEnroll.getEndDate());
		courseEnrollDto.setRemarks(courseEnroll.getRemarks());
		courseEnrollDto.setUserId(courseEnroll.getUserId().getUserId());
		return courseEnrollDto;
	}
	
	/**
	 * 
	 * @param course
	 * @return
	 */
	public static Date calculateEndDate(Course course) {
        // convert date to calendar
        Calendar calendar = Calendar.getInstance();
        switch(course.getDurationType()) {
        	case "Week":
        		int week = course.getCourseDuration() * 7;
        		calendar.add(Calendar.DATE, week);
        		break;
        	case "Day":
        		calendar.add(Calendar.DATE, course.getCourseDuration());
        		break;
        	default:
	    		break;
        }
        
        // convert calendar to date
        return calendar.getTime();
	}
}
