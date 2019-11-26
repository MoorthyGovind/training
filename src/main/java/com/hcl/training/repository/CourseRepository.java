package com.hcl.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.training.entity.Course;


@Repository
public interface CourseRepository extends CrudRepository<Course, Long>{

	@Override
	List<Course> findAll();

	Course findByCourseCode(String courseCode);
	
	List<Course> findByIdNotIn(List<Long> ids);
	
	@Query("SELECT c FROM Course c WHERE c.courseCode like :searchValue OR c.courseName like :searchValue")
	public  List<Course> findCourseBySearchValue(String searchValue);
}
