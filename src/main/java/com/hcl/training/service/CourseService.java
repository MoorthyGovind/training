package com.hcl.training.service;

import java.util.List;

import com.hcl.training.dto.CourseEntrollDto;
import com.hcl.training.dto.CourseSearchDto;
import com.hcl.training.dto.ViewCourseDto;
import com.hcl.training.dto.ViewCourseEnrollDto;
import com.hcl.training.response.ApiResponse;

public interface CourseService {

	List<ViewCourseDto> getAllCourses();
	
	List<ViewCourseEnrollDto> getCoursesByUserId(String userId);
	
	List<ViewCourseDto> getAvailableCoursesByUserId(String userId);
	
	ApiResponse courseEntroll(CourseEntrollDto courseEntrollDto);
	
	List<ViewCourseDto> getCourseBySearchValue(CourseSearchDto courseSearchDto);
}
