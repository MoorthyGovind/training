package com.hcl.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.training.dto.UserDto;
import com.hcl.training.entity.User;
import com.hcl.training.repository.UserRepository;
import com.hcl.training.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	/**
	 * login check with userId and password
	 */
	@Override
	public User login(UserDto userDto) {
		return userRepository.findByUserIdAndPassword(userDto.getUserId(), userDto.getPassword());
	}
}
