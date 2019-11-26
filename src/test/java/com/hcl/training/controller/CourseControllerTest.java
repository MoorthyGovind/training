package com.hcl.training.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hcl.training.common.AppConstants;
import com.hcl.training.dto.CourseEntrollDto;
import com.hcl.training.dto.CourseSearchDto;
import com.hcl.training.dto.ViewCourseDto;
import com.hcl.training.dto.ViewCourseEnrollDto;
import com.hcl.training.response.ApiResponse;
import com.hcl.training.service.CourseService;

@RunWith(SpringJUnit4ClassRunner.class)
public class CourseControllerTest {

	@InjectMocks
	CourseController courseController;
	
	@Mock
	CourseService courseService;
	
	private MockMvc mockMvc;
	List<ViewCourseDto> viewCourseDtos = new ArrayList<>();
	List<ViewCourseEnrollDto> viewCourseEnrollDtos = new ArrayList<>();
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
		
		ViewCourseDto viewCourseDto = new ViewCourseDto();
		viewCourseDto.setCourseCode("C001");
		viewCourseDto.setCourseName("Spring Boot");
		viewCourseDto.setCourseDetail("Course Detailed");
		viewCourseDto.setCourseDuration("8 Months");
		viewCourseDtos.add(viewCourseDto);
	}
	
	@Test
	public void testGetAllCourses() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	public void testGetCoursesByUserId() throws Exception {
		when(courseService.getCoursesByUserId("moorthy127@gmail.com")).thenReturn(viewCourseEnrollDtos);
      
		ResponseEntity<List<ViewCourseEnrollDto>> dtos = courseController.getCoursesByUserId("moorthy127@gmail.com");
		assertEquals(HttpStatus.OK.value(), dtos.getStatusCode().value());
	}
	
	@Test
	public void testGetAvailableCoursesByUserId() throws Exception {
		when(courseService.getAvailableCoursesByUserId("moorthy127@gmail.com")).thenReturn(viewCourseDtos);
      
		ResponseEntity<List<ViewCourseDto>> dtos = courseController.getAvailableCoursesByUserId("moorthy127@gmail.com");
		assertEquals(HttpStatus.OK.value(), dtos.getStatusCode().value());
	}
	
	@Test
	public void testSearchCourses() {
		CourseSearchDto searchDto = new CourseSearchDto();
		searchDto.setSerachValue("Spring");
		when(courseService.getCourseBySearchValue(searchDto)).thenReturn(viewCourseDtos);
		
		ResponseEntity<List<ViewCourseDto>> dtos = courseController.searchCourses(searchDto);
		assertEquals(HttpStatus.OK.value(), dtos.getStatusCode().value());

	}
	
	@Test
	public void testCourseEnroll() throws Exception {
		ApiResponse apiResponse = new ApiResponse(AppConstants.SUCCESS, HttpStatus.OK.value(), AppConstants.ENROLLED_SUCCESSFULLY);
		CourseEntrollDto courseEnrollDto = new CourseEntrollDto();
		courseEnrollDto.setCourseCode("C001");
		courseEnrollDto.setUserId("moorthy127@gmail.com");
		courseEnrollDto.setRemarks("Course Entrolled");
		when(courseService.courseEntroll(courseEnrollDto)).thenReturn(apiResponse);
		
		ResponseEntity<ApiResponse> response = courseController.courseEnroll(courseEnrollDto);
		assertEquals(AppConstants.SUCCESS, response.getBody().getStatus());
		assertEquals(201, response.getBody().getStatusCode());
		assertEquals(AppConstants.ENROLLED_SUCCESSFULLY, response.getBody().getMessage());
	}
	
	@Test
	public void testCourseEnrollBadRequest() throws Exception {
		ApiResponse apiResponse = new ApiResponse(AppConstants.FAILURE, HttpStatus.BAD_REQUEST.value(), AppConstants.NO_RECORD_FOUND);
		CourseEntrollDto courseEnrollDto = new CourseEntrollDto();
		courseEnrollDto.setCourseCode("C022");
		courseEnrollDto.setUserId("moorthy@gmail.com");
		courseEnrollDto.setRemarks("Course Entrolled");
		when(courseService.courseEntroll(courseEnrollDto)).thenReturn(apiResponse);
		
		ResponseEntity<ApiResponse> response = courseController.courseEnroll(courseEnrollDto);
		assertEquals(AppConstants.FAILURE, response.getBody().getStatus());
		assertEquals(400, response.getBody().getStatusCode());
		assertEquals(AppConstants.NO_RECORD_FOUND, response.getBody().getMessage());
	}
}
