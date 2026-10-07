package com.DayspringRoyale.services;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.Course;
import com.DayspringRoyale.Model.Department;
import com.DayspringRoyale.Repository.CourseRepo;
import com.DayspringRoyale.Repository.DepartmentRepo;

@Service
public class CourseService {
	@Autowired
	private CourseRepo repo;
	
	@Autowired
	private DepartmentRepo departmentrepo;

	public List<Course> getAllCourse() {
		return repo.findAll();
	}

	public Optional<Course> getCourseById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	public Course newCourse(Course course) {
		Department department = departmentrepo.findById(course.getDepartment()
				.getId()).orElseThrow(() -> new RuntimeException("Department Not Found"));
		course.setDepartment(department);
		return repo.save(course);
	}

	public List<Course> searchCourse(String keyword) {
		return repo.searchCourse(keyword);
	}

}
