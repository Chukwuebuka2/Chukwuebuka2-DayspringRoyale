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
import org.springframework.web.bind.annotation.RestController;

import com.DayspringRoyale.Model.Grade;
import com.DayspringRoyale.services.GradeService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class GradeController {
	
	@Autowired
	private GradeService service;
	
	@PreAuthorize("hasRole('LECTURER', 'DEAN', 'REGISTRY', 'HOD', 'FACAULTY', 'SUPPORT_STAFF')")
	@GetMapping("/grade/{studentId}")
	public ResponseEntity<?> getStudentGrade(@PathVariable int studentId){
			List<Grade> scores = service.getStudentGrade(studentId);
			return new ResponseEntity<>(scores, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('LECTURER', 'DEAN', 'REGISTRY','FACAULTY', 'HOD', 'SUPPORT_STAFF')")
	@GetMapping("/grade")
	public ResponseEntity<?> getcourseGrade(@PathVariable int courseId){
			List<Grade> scores = service.getCourseGrade(courseId);
			return new ResponseEntity<>(scores, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('HOD', 'FACAULTY')")
	@PostMapping("/grade")
	public ResponseEntity<Grade> saveGrade(@RequestBody Grade grade){
		return new ResponseEntity<>(service.saveGrade(grade), HttpStatus.OK);
	}
}
