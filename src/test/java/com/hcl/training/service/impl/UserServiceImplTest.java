package com.hcl.training.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcl.training.dto.UserDto;
import com.hcl.training.entity.User;
import com.hcl.training.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserRepository userRepository;
	
	User user = new User();

	@Before
	public void init() {
		user.setCourseEnroll(null);
		user.setId(1L);
		user.setUserId("moorthy127@gmail.com");
		user.setPassword("start@123");
		user.setCreatedBy("admin");
		user.setCreatedDate(new Date());
		user.setStatus(true);
	}
	
	@Test
	public void testLogin() {
		UserDto userDto = new UserDto();
		userDto.setUserId("moorthy127@gmail.com");
		userDto.setPassword("start@123");
		
		when(userRepository.findByUserIdAndPassword("moorthy127@gmail.com", "start@123")).thenReturn(user);
		User userDetail = userServiceImpl.login(userDto);
		assertEquals(userDetail.getUserId(), user.getUserId());
		assertEquals(userDetail.getPassword(), user.getPassword());
		assertEquals(userDetail.getCourseEnroll(), user.getCourseEnroll());
		assertEquals(userDetail.getStatus(), user.getStatus());
		assertEquals(userDetail.getId(), user.getId());
	}
}
