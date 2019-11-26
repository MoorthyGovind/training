package com.hcl.training.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcl.training.entity.CourseEnroll;

@Repository
@Transactional
public interface CourseEnrollRepository extends CrudRepository<CourseEnroll, Long>{
	
	List<CourseEnroll> findByUserIdId(long id);
	
	@Query("SELECT c.courseId.id FROM CourseEnroll c WHERE c.userId.id = :id") 
    List<Long> findAllCourseByUserId(@Param("id") long id);
	
	CourseEnroll findByUserIdIdAndCourseIdId(long userId, long courseId);
}
