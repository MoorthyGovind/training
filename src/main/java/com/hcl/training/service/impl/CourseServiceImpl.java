package com.hcl.training.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.training.common.AppConstants;
import com.hcl.training.dto.CourseEntrollDto;
import com.hcl.training.dto.CourseSearchDto;
import com.hcl.training.dto.ViewCourseDto;
import com.hcl.training.dto.ViewCourseEnrollDto;
import com.hcl.training.entity.Course;
import com.hcl.training.entity.CourseEnroll;
import com.hcl.training.entity.User;
import com.hcl.training.repository.CourseEnrollRepository;
import com.hcl.training.repository.CourseRepository;
import com.hcl.training.repository.UserRepository;
import com.hcl.training.response.ApiResponse;
import com.hcl.training.service.CourseService;
import com.hcl.training.util.ConverterUtil;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CourseEnrollRepository courseEnrollRepository;
	
	/**
	 * getAllCourses
	 */
	@Override
	public List<ViewCourseDto> getAllCourses(){
		List<ViewCourseDto> viewCourses = new ArrayList<>();
		List<Course> courses = courseRepository.findAll();
		courses.forEach(course -> viewCourses.add(ConverterUtil.convertCourseDto(course)));
		return viewCourses;
	}
	
	/**
	 * getCoursesByUserId by userId
	 */
	@Override
	public List<ViewCourseEnrollDto> getCoursesByUserId(String userId){
		List<ViewCourseEnrollDto> viewCourseEnrollDtos = new ArrayList<>();
		User user = userRepository.findByUserId(userId);
		Optional<User> isUser = Optional.ofNullable(user);
		if(isUser.isPresent()) {
			List<CourseEnroll> courseEnrolls = courseEnrollRepository.findByUserIdId(user.getId());
			courseEnrolls.forEach(courseEnroll -> viewCourseEnrollDtos.add(ConverterUtil.convertCourseEnrollDto(courseEnroll)));
		}
		return viewCourseEnrollDtos;
	}
	
	/**
	 * getAvailableCoursesByUserId
	 * @userId
	 */
	@Override
	public List<ViewCourseDto> getAvailableCoursesByUserId(String userId){
		List<ViewCourseDto> viewCourseDtos = new ArrayList<>();
		User user = userRepository.findByUserId(userId);
		Optional<User> isUser = Optional.ofNullable(user);
		if(isUser.isPresent()) {
			List<Course> availableCourses;
			List<Long> courseEnrolls = courseEnrollRepository.findAllCourseByUserId(user.getId());
			if(!courseEnrolls.isEmpty()) {
				availableCourses = courseRepository.findByIdNotIn(courseEnrolls);
			}else {
				availableCourses = courseRepository.findAll();
			}
			
			availableCourses.forEach(course -> viewCourseDtos.add(ConverterUtil.convertCourseDto(course)));
		}
		return viewCourseDtos;
	}
	
	/**
	 * @CourseEntrollDto courseEntrollDto
	 * add courseEntroll by userId
	 */
	@Override
	public ApiResponse courseEntroll(CourseEntrollDto courseEntrollDto){
		ApiResponse response = new ApiResponse(AppConstants.SUCCESS, 0, null);
		CourseEnroll courseEnroll = new CourseEnroll();
		User user = userRepository.findByUserId(courseEntrollDto.getUserId());
		Course course = courseRepository.findByCourseCode(courseEntrollDto.getCourseCode());
		Optional<User> isUser = Optional.ofNullable(user);
		Optional<Course> isCourse = Optional.ofNullable(course);
		if(isUser.isPresent() && isCourse.isPresent()) {
			CourseEnroll courseEntroll = courseEnrollRepository.findByUserIdIdAndCourseIdId(user.getId(), course.getId());
			Optional<CourseEnroll> isCourseEntroll = Optional.ofNullable(courseEntroll);
			if(isCourseEntroll.isPresent()) {
				response.setStatus(AppConstants.FAILURE);
				response.setMessage(AppConstants.RECORD_ALREADY_EXISTS);
			}else {
				courseEnroll.setUserId(user);
				courseEnroll.setCourseId(course);
				courseEnroll.setRemarks(courseEntrollDto.getRemarks());

				//Set Start date and End Date of the course.
				courseEnroll.setStartDate(new Date());
				Date endDate = ConverterUtil.calculateEndDate(course);
				courseEnroll.setEndDate(endDate);
				
				courseEnrollRepository.save(courseEnroll);
				
				response.setStatus(AppConstants.SUCCESS);
				response.setMessage(AppConstants.ENROLLED_SUCCESSFULLY);
			}
			
		}else {
			response.setStatus(AppConstants.FAILURE);
			response.setMessage(AppConstants.NO_RECORD_FOUND);
		}
		
		return response;
	}
	 
	/**getCourseBySearchValue
	 * @CourseSearchDto courseSearchDto
	 * return List<ViewCourseDto>
	 */
	@Override
	public List<ViewCourseDto> getCourseBySearchValue(CourseSearchDto courseSearchDto){
		List<ViewCourseDto> viewCourseDtos = new ArrayList<>();
		List<Course> courses = courseRepository.findCourseBySearchValue("%"+courseSearchDto.getSerachValue()+"%");
		courses.forEach(course -> viewCourseDtos.add(ConverterUtil.convertCourseDto(course)));
		
		return viewCourseDtos;
	}
	
}
