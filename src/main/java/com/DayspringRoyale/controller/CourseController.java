package com.DayspringRoyale.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DayspringRoyale.Model.Course;
import com.DayspringRoyale.services.CourseService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class CourseController {
	@Autowired
	CourseService service;
	
	@PreAuthorize("isAuthenticated() and !hasRole('APPLICANT')")
	@GetMapping("/course")
	public ResponseEntity<List<Course>> getAllCourse(){
		return new ResponseEntity<>(service.getAllCourse(), HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated() and !hasRole('APPLICANT')")
	@GetMapping("/course/{id}")
	public ResponseEntity<?> getCourseById(@PathVariable int id){
		Optional<Course> course = service.getCourseById(id);
		if (course.isPresent())
			return new ResponseEntity<>(course, HttpStatus.OK);
		else
			return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@PostMapping("/course")
	public ResponseEntity<?> newCourse(@RequestBody Course course){
		try {
			Course course1 = service.newCourse(course);
			return new ResponseEntity<>(course1, HttpStatus.OK);
		}
		
		catch (Exception e) {
			return new ResponseEntity<>("Input not Valid", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("isAuthenticated() and !hasRole('APPLICANT')")
	@GetMapping("/course/search")
	public ResponseEntity<List<Course>> searchCourse(@RequestParam String keyword){
		return new ResponseEntity<> (service.searchCourse(keyword), HttpStatus.OK);
	}
	

}
