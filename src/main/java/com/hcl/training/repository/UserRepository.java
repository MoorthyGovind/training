package com.hcl.training.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.training.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	User findByUserIdAndPassword(String userId, String password);

	User findByUserId(String userId);
}
