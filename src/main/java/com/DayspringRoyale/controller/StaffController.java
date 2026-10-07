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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DayspringRoyale.Model.Staff;
import com.DayspringRoyale.services.StaffService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class StaffController {
	
	@Autowired
	private StaffService service;

	@PreAuthorize("hasRole('REGISTRY','SUPPORT_STAFF')")
	@GetMapping("/staff")
	public ResponseEntity<List<Staff>> getAllStaff(){
		return new ResponseEntity<>(service.getAllStaff(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@GetMapping("/staff/{id}")
	public ResponseEntity<?> getStaffById(@PathVariable int id){
		Optional<Staff> staff = service.getStaffById(id);
		if(staff.isPresent())
			return new ResponseEntity<>(staff, HttpStatus.OK);
		else
			return new ResponseEntity<>("Staff Not Found", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@PostMapping("/staff")
	public ResponseEntity<?> addNewStaff(@RequestPart Staff staff,
			@RequestPart MultipartFile imagefile, @RequestPart List<MultipartFile> document){
		try {
			Staff staff1 = service.addNewStaff(staff, imagefile, document);
			return new ResponseEntity<>(staff1, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF', 'DEAN')")
	@GetMapping("/staff/school/{schoolId}")
	public ResponseEntity<List<Staff>> getStaffBySchool(@PathVariable int schoolId){
		List<Staff> staff = service.getStaffBySchool(schoolId);
		return new ResponseEntity<>(staff, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF', 'DEAN', 'HOD', 'FACAULTY', 'Student')")
	@GetMapping("/staff/department/{deptIp}")
	public ResponseEntity<List<Staff>> getStaffByDepartment(@PathVariable int deptId){
		List<Staff> staff = service.getStaffByDepartment(deptId);
		return new ResponseEntity<>(staff, HttpStatus.OK);
	}
	
	@GetMapping("/staff/search")
	public ResponseEntity<List<Staff>> searchStaff(String keyword){
		List<Staff> staff = service.searchStaff(keyword);
		return new ResponseEntity<>(staff, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('REGISTRY')")
	@PostMapping("/staff/hod")
	public ResponseEntity<String> assignHOD(@PathVariable int staffId){
		return new ResponseEntity<>("New HOD Assigned", HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('REGISTRY')")
	@PostMapping("/staff/dean")
	public ResponseEntity<String> assignDean(@PathVariable int staffId){
		return new ResponseEntity<>("New Dean Assigned", HttpStatus.OK);
	}
}
