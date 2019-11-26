package com.hcl.training.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.training.common.AppConstants;
import com.hcl.training.dto.CourseEntrollDto;
import com.hcl.training.dto.CourseSearchDto;
import com.hcl.training.dto.ViewCourseDto;
import com.hcl.training.dto.ViewCourseEnrollDto;
import com.hcl.training.response.ApiResponse;
import com.hcl.training.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	@Autowired
	CourseService courseService;
	
	/**
	 * getAllCourses
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<List<ViewCourseDto>> getAllCourses(){
		logger.info("getAllCourses...");
		List<ViewCourseDto> viewCourseDtos = courseService.getAllCourses();
		return new ResponseEntity<>(viewCourseDtos, HttpStatus.OK);
	}
	
	/**
	 * courseEnroll
	 * @param courseEntrollDto
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value="", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> courseEnroll(@Valid @RequestBody CourseEntrollDto courseEntrollDto)
				throws SQLException{
		logger.info("course enroll...");
		ApiResponse response = courseService.courseEntroll(courseEntrollDto);
		if(response.getStatus().equals(AppConstants.SUCCESS)) {
			response.setStatusCode(HttpStatus.CREATED.value());
		}else {
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * getCoursesByUserId
	 * @param userId
	 * @return
	 */
	@GetMapping(value="/user/{userId}")
	public ResponseEntity<List<ViewCourseEnrollDto>> getCoursesByUserId(@PathVariable String userId){
		logger.info("getCoursesByUserId...");
		List<ViewCourseEnrollDto> viewCourseDtos = courseService.getCoursesByUserId(userId);
		return new ResponseEntity<>(viewCourseDtos, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping(value="/availabe/{userId}")
	public ResponseEntity<List<ViewCourseDto>> getAvailableCoursesByUserId(@PathVariable String userId){
		logger.info("getAvailableCoursesByUserId...");
		List<ViewCourseDto> viewCourseDtos = courseService.getAvailableCoursesByUserId(userId);
		return new ResponseEntity<>(viewCourseDtos, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param courseSearchDto
	 * @return
	 */
	@PostMapping(value="/search")
	public ResponseEntity<List<ViewCourseDto>> searchCourses(@RequestBody CourseSearchDto courseSearchDto){
		logger.info("searchCourses by search string...");
		List<ViewCourseDto> viewCourseDtos = courseService.getCourseBySearchValue(courseSearchDto);
		return new ResponseEntity<>(viewCourseDtos, HttpStatus.OK);
	}
}
