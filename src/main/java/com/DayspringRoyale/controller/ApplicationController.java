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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DayspringRoyale.Model.Applicant;
import com.DayspringRoyale.services.ApplicationService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class ApplicationController {
	
	@Autowired
	private ApplicationService service;
	
	
	@PostMapping("/apply")
	public ResponseEntity<?> apply(@RequestPart Applicant applicant, 
			@RequestPart MultipartFile image, @RequestPart List<MultipartFile> document){
		try {
			Applicant apply = service.apply(applicant, image, document);
			return new ResponseEntity<>(apply, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("Invalid Application", HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@GetMapping("/application")
	public ResponseEntity<List<Applicant>> applications(){
		return new ResponseEntity<>(service.application(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@GetMapping("/application/{id}")
	public ResponseEntity<?> application(@PathVariable int id){
		
		Optional<Applicant> applicant = service.application(id);
		
		if(applicant.isPresent()) {
			return new ResponseEntity<>(applicant, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("Applicant Not Found", HttpStatus.BAD_GATEWAY);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@PatchMapping("/applicatipn/{id}/reject")
	public ResponseEntity<Applicant> rejectApllication(@PathVariable int id){
		return new ResponseEntity<>(service.rejectApplication(id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@PatchMapping("/applicatipn/{id}/accecpt")
	public ResponseEntity<Applicant> acceptApllication(@PathVariable int id){
		return new ResponseEntity<>(service.acceptApplication(id), HttpStatus.OK);
	}

}
