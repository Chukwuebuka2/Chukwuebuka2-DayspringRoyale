package com.DayspringRoyale.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
	@Query("SELECT c FROM Course c WHERE " +
				"LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
					"LOWER(c.code) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Course> searchCourse(String keyword);

}
