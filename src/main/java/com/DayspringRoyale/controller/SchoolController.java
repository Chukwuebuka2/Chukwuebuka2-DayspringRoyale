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

import com.DayspringRoyale.Model.School;
import com.DayspringRoyale.services.SchoolService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class SchoolController {
	
	@Autowired
	private SchoolService service;
	

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/school")
	public ResponseEntity<List<School>> getAllSchool(){
		return new ResponseEntity<>(service.getAllSchool(), HttpStatus.OK);
	}
	

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/school/{id}")
	public ResponseEntity<?> getSchool(@PathVariable int id){
		Optional<School> school = service.getSchool(id);
		if (school.isPresent())
			return new ResponseEntity<>(school, HttpStatus.OK);
		else
			return new ResponseEntity<>("School Not Found", HttpStatus.NOT_FOUND);
	}

	@PreAuthorize("hasRole('Registry', 'SUPPORT_STAFF')")
	@PostMapping("/school")
	public ResponseEntity<?> addSchool(@RequestBody School school){
		
		try {
		School school1 = service.addSchool(school);
			return new ResponseEntity<>(school1, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("Invalid Input" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/school/search")
	public ResponseEntity<List<School>> searchSchool(@RequestParam String keyword){
		return new ResponseEntity<>(service.searchSchool(keyword), HttpStatus.OK);
	}
	

}
