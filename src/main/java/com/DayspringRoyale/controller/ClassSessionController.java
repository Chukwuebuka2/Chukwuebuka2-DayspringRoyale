package com.DayspringRoyale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DayspringRoyale.Model.ClassSession;
import com.DayspringRoyale.services.ClassSessionService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ClassSessionController {
	
	@Autowired
	private ClassSessionService service;
	
	@PreAuthorize("hasRole('LECTURER', 'COURSE_ADVISOR', 'HOD', 'FACAULTY')")
	@GetMapping("/classsession")
	public ResponseEntity<List<ClassSession>> getClasses(){
		return new ResponseEntity<>(service.getClasses(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('LECTURER')")
	@PostMapping("/classsession")
	public ResponseEntity<?> addClassSession(@RequestBody ClassSession classsession){
		try {
			ClassSession classes = service.addClassSession(classsession);
			return new ResponseEntity<>(classes, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("Invalid Input", HttpStatus.OK);
		}
	}

}
