package com.hcl.training.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.training.common.AppConstants;
import com.hcl.training.dto.UserDto;
import com.hcl.training.entity.User;
import com.hcl.training.exception.CustomExceptionHandler;
import com.hcl.training.response.ApiResponse;
import com.hcl.training.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	User user = new User();
	 
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		
		user.setId(1L);
		user.setUserId("moorthy127@gmail.com");
		user.setPassword("start@123");
		user.setCreatedDate(new Date());
		user.setCourseEnroll(null);
		user.setCreatedBy("admin");
		user.setStatus(true);
	}
	
	@Test
	public void testLogin() {
		UserDto userDto = new UserDto();
		userDto.setUserId("moorthy127@gmail.com");
		userDto.setPassword("start@123");
		when(userService.login(userDto)).thenReturn(user);
		
		ResponseEntity<ApiResponse> response = userController.login(userDto);
		assertEquals(AppConstants.SUCCESS, response.getBody().getStatus());
		assertEquals(200, response.getBody().getStatusCode());
	}
	
	@Test
	public void testInvalidLogin() {
		UserDto userDto = new UserDto();
		userDto.setUserId("moorthy127@gmail.com");
		userDto.setPassword("start");
		when(userService.login(userDto)).thenReturn(null);
		ResponseEntity<ApiResponse> response = userController.login(userDto);
		assertEquals(AppConstants.FAILURE, response.getBody().getStatus());
	}
	
	@Test
	public void testInvalidData() throws Exception{

		WebRequest webrequest = null;
		UserDto userDto = new UserDto();
		userDto.setUserId("moorthy127");
		userDto.setPassword("start");

		//MvcResult for mockmvc performed
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andReturn();
			new CustomExceptionHandler().handleException(result.getResolvedException(), webrequest);
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String asJsonString(final Object obj) throws Exception {
            return new ObjectMapper().writeValueAsString(obj);
    }
}
