package com.hcl.training.service;

import com.hcl.training.dto.UserDto;
import com.hcl.training.entity.User;

@FunctionalInterface
public interface UserService {

	User login(UserDto userDto);
}
