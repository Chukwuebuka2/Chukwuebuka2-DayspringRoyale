package com.DayspringRoyale.controller;

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

import com.DayspringRoyale.Model.AcademicYear;
import com.DayspringRoyale.services.AcademicYearService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class AcademicYearController {
	
	@Autowired
	private AcademicYearService service;
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@PostMapping("/academicyear")
	public ResponseEntity<?> newAcademicYear(@RequestBody AcademicYear newyear){
		try {
		AcademicYear year = service.newAcademicYear(newyear);
		return new ResponseEntity<>(year, HttpStatus.OK);
		}
		catch (Exception e) {
		return new ResponseEntity<>("Not  Valid", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/academicyear")
	public ResponseEntity<AcademicYear> getCurrentYear(){
		return new ResponseEntity<>(service.getCurrentYear(), HttpStatus.OK);
	}

}
 