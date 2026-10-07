package com.DayspringRoyale.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DayspringRoyale.Model.Enrollment;
import com.DayspringRoyale.services.EnrollmentService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class EnrollmentController {
	@Autowired
	private EnrollmentService service;
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF', 'LECTURER', 'FACAULTY', 'HOD')")
	@GetMapping("/enrollment")
	public ResponseEntity<List<Enrollment>> getAllEnrollment(){
		return new ResponseEntity<> (service.getAllEnrollment(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF', 'LECTURER', 'FACAULTY', 'HOD')")
	@GetMapping("/enrollment/{id}")
	public ResponseEntity<?> getEnrollmentById(@PathVariable int id){
		Optional<Enrollment> enrollment = service.getEnrollmentById(id);
		if(enrollment.isPresent())
			return new ResponseEntity<>(enrollment, HttpStatus.OK);
		else
			return new ResponseEntity<>("Was Not Enrolled", HttpStatus.NOT_FOUND);
		
	}
	
	@PreAuthorize("hasRole('STUDENT')")
	@PostMapping("/enrollment")
	public ResponseEntity<?> newEnrollment(@RequestBody Enrollment enrollment){
		try {
			Enrollment enrollment1 = service.newEnrollment(enrollment);
			return new ResponseEntity<>(enrollment1, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('COURSE_ADVISOR')")
	@PatchMapping("/enrollment/{id}/rejected")
	public ResponseEntity<?> rejectStatus(@PathVariable int id){
		return new ResponseEntity<>(service.rejectStatus(id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('COURSE_ADVISOR')")
	@PatchMapping("/enrollment/{id}/accepted")
	public ResponseEntity<?> acceptStatus(@PathVariable int id){
		return new ResponseEntity<>(service.acceptStatus(id), HttpStatus.OK);
	}

}
