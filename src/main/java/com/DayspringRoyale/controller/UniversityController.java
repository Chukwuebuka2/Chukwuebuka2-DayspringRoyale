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

import com.DayspringRoyale.Model.University;
import com.DayspringRoyale.services.UniversityService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class UniversityController {
	@Autowired
	private UniversityService service;
	
	@PostMapping("/university")
	public ResponseEntity<?> addUniversity(@RequestBody University university){
		try {
			University newUni = service.addUniversity(university);
			return new ResponseEntity<>(newUni , HttpStatus.OK);
		}
		
		catch(Exception e) {
			return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/university")
	public ResponseEntity<University> getUniversity(){
		return new ResponseEntity<>(service.getUniversity(), HttpStatus.OK);
	}

}
