package com.hcl.training.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
import com.hcl.training.service.impl.CourseServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class CourseServiceImplTest {

	@InjectMocks
	CourseServiceImpl courseServiceImpl;
	
	@Mock
	CourseRepository courseRepository;
	
	@Mock
	CourseEnrollRepository courseEnrollRepository;
	
	@Mock
	UserRepository userRepository;
	
	User user = new User();
	Course course = new Course();
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		//User Details
		user.setId(1L);
		user.setUserId("moorthy127@gmail.com");
		user.setStatus(true);
		
		//Course Details
		course.setId(1L);
		course.setCourseCode("C001");
		course.setCourseDetail("Courses Covered By 2 Weeks");
		course.setCourseEnroll(null);
		course.setCourseName("Spring Boot");
		course.setCourseDuration(1);
		course.setDurationType("Week");
	}
	
	@Test
	public void testGetAllCourses() {
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		when(courseRepository.findAll()).thenReturn(courses);
		List<ViewCourseDto> viewCourseDtos = courseServiceImpl.getAllCourses();
		assertEquals(1, viewCourseDtos.size());
		viewCourseDtos.forEach(dto ->{
			assertThat(dto.getCourseCode()).isNotEmpty();
			assertThat(dto.getCourseName()).isNotEmpty();
			assertThat(dto.getCourseDuration()).isNotEmpty();
			assertThat(dto.getCourseDetail()).isNotEmpty();
		});
	}
	
	@Test
	public void testGetCoursesByUserId() {
		List<CourseEnroll> courseEnrolls = new ArrayList<>();
		
		CourseEnroll courseEnroll = new CourseEnroll();
		courseEnroll.setCourseId(course);
		courseEnroll.setStartDate(new Date());
		courseEnroll.setEndDate(new Date());
		courseEnroll.setRemarks("enroll");
		courseEnroll.setUserId(user);
		courseEnrolls.add(courseEnroll);
		
		when(userRepository.findByUserId("moorthy127@gmail.com")).thenReturn(user);
		when(courseEnrollRepository.findByUserIdId(user.getId())).thenReturn(courseEnrolls);
		
		List<ViewCourseEnrollDto> viewCourseEnrollDtos = courseServiceImpl.getCoursesByUserId("moorthy127@gmail.com");
		assertThat(viewCourseEnrollDtos).hasSize(1);
		
		viewCourseEnrollDtos.forEach(dto ->{			
			assertThat(dto.getUserId()).isNotEmpty();
			assertThat(dto.getCourseCode()).isNotEmpty();
			assertThat(dto.getCourseName()).isNotEmpty();
			assertThat(dto.getRemarks()).isNotEmpty();
			assertThat(dto.getStartDate()).isNotNull();
			assertThat(dto.getEndDate()).isNotNull();
		});

	}
	
	@Test
	public void testGetAvailableCoursesByUserId() {
		List<Long> courseEnrolls = new ArrayList<>();
		List<Course> courses = new ArrayList<>();

		courseEnrolls.add(1L);
		courses.add(course);
		
		when(userRepository.findByUserId("moorthy127@gmail.com")).thenReturn(user);
		when(courseEnrollRepository.findAllCourseByUserId(user.getId())).thenReturn(courseEnrolls);
		when(courseRepository.findByIdNotIn(courseEnrolls)).thenReturn(courses);

		List<ViewCourseDto> availableCourses = courseServiceImpl.getAvailableCoursesByUserId("moorthy127@gmail.com");
		assertThat(availableCourses).isNotEmpty();
	}
	
	@Test
	public void testGetAvailableCoursesByUserIdEmpty() {
		List<Long> courseEnrolls = new ArrayList<>();
		List<Course> courses = new ArrayList<>();

		courses.add(course);
		
		when(userRepository.findByUserId("moorthy127@gmail.com")).thenReturn(user);
		when(courseEnrollRepository.findAllCourseByUserId(user.getId())).thenReturn(courseEnrolls);
		when(courseRepository.findAll()).thenReturn(courses);

		List<ViewCourseDto> availableCourses = courseServiceImpl.getAvailableCoursesByUserId("moorthy127@gmail.com");
		assertThat(availableCourses).isNotEmpty();
	}
	
	@Test
	public void testGetCourseBySearchValue() {
		CourseSearchDto courseSearchDto= new CourseSearchDto();
		courseSearchDto.setSerachValue("Spring");
		
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		
		when(courseRepository.findCourseBySearchValue("%Spring%")).thenReturn(courses);
		
		List<ViewCourseDto> viewCourseDtos = courseServiceImpl.getCourseBySearchValue(courseSearchDto);
		assertThat(viewCourseDtos).isNotEmpty();
	}
	
	@Test
	public void testCourseEntrollRecordAlreadyExists() throws Exception {
		CourseEnroll courseEnroll = new CourseEnroll();
		courseEnroll.setCourseId(course);
		courseEnroll.setEndDate(new Date());
		courseEnroll.setStartDate(new Date());
		courseEnroll.setId(1L);
		courseEnroll.setRemarks("Course Enrolled");
		courseEnroll.setUserId(user);
		
		CourseEntrollDto courseEntrollDto = new CourseEntrollDto();
		courseEntrollDto.setCourseCode("C001");
		courseEntrollDto.setRemarks("Course Enrolled");
		courseEntrollDto.setUserId("moorthy127@gmail.com");
		
		when(userRepository.findByUserId("moorthy127@gmail.com")).thenReturn(user);
		when(courseRepository.findByCourseCode("C001")).thenReturn(course);
		when(courseEnrollRepository.findByUserIdIdAndCourseIdId(1L, 1L)).thenReturn(courseEnroll);
		ApiResponse apiResponse = courseServiceImpl.courseEntroll(courseEntrollDto);
		assertEquals(AppConstants.FAILURE, apiResponse.getStatus());
		assertEquals(0, apiResponse.getStatusCode());
		assertEquals(AppConstants.RECORD_ALREADY_EXISTS, apiResponse.getMessage());
	}
	
	@Test
	public void testCourseEntrollNoRecordFound() throws Exception{
		CourseEntrollDto courseEntrollDto = new CourseEntrollDto();
		courseEntrollDto.setCourseCode("C001");
		courseEntrollDto.setRemarks("Course Enrolled");
		courseEntrollDto.setUserId("moorthy@gmail.com");
		
		when(userRepository.findByUserId("moorthy@gmail.com")).thenReturn(null);
		when(courseRepository.findByCourseCode("C001")).thenReturn(course);
		ApiResponse apiResponse = courseServiceImpl.courseEntroll(courseEntrollDto);

		assertEquals(AppConstants.FAILURE, apiResponse.getStatus());
		assertEquals(0, apiResponse.getStatusCode());
		assertEquals(AppConstants.NO_RECORD_FOUND, apiResponse.getMessage());
	}
	
	@Test
	public void testCourseEntrollByWeek() throws Exception{
		CourseEnroll courseEnroll = new CourseEnroll();
		
		CourseEntrollDto courseEntrollDto = new CourseEntrollDto();
		courseEntrollDto.setCourseCode("C001");
		courseEntrollDto.setRemarks("Course Enrolled");
		courseEntrollDto.setUserId("moorthy127@gmail.com");
		
		when(userRepository.findByUserId("moorthy127@gmail.com")).thenReturn(user);
		when(courseRepository.findByCourseCode("C001")).thenReturn(course);
		when(courseEnrollRepository.findByUserIdIdAndCourseIdId(1L, 1L)).thenReturn(null);
		when(courseEnrollRepository.save(courseEnroll)).thenReturn(courseEnroll);

		ApiResponse apiResponse = courseServiceImpl.courseEntroll(courseEntrollDto);
		assertEquals(AppConstants.SUCCESS, apiResponse.getStatus());
		assertEquals(0, apiResponse.getStatusCode());
		assertEquals(AppConstants.ENROLLED_SUCCESSFULLY, apiResponse.getMessage());
	}
	
	@Test
	public void testCourseEntrollByDay() throws Exception{
		CourseEnroll courseEnroll = new CourseEnroll();
		
		CourseEntrollDto courseEntrollDto = new CourseEntrollDto();
		courseEntrollDto.setCourseCode("C002");
		courseEntrollDto.setRemarks("Course Enrolled");
		courseEntrollDto.setUserId("moorthy127@gmail.com");
		
		course.setDurationType("Day");
		
		when(userRepository.findByUserId("moorthy127@gmail.com")).thenReturn(user);
		when(courseRepository.findByCourseCode("C002")).thenReturn(course);
		when(courseEnrollRepository.findByUserIdIdAndCourseIdId(1L, 1L)).thenReturn(null);
		when(courseEnrollRepository.save(courseEnroll)).thenReturn(courseEnroll);

		ApiResponse apiResponse = courseServiceImpl.courseEntroll(courseEntrollDto);
		assertEquals(AppConstants.SUCCESS, apiResponse.getStatus());
		assertEquals(0, apiResponse.getStatusCode());
		assertEquals(AppConstants.ENROLLED_SUCCESSFULLY, apiResponse.getMessage());
	}
}
