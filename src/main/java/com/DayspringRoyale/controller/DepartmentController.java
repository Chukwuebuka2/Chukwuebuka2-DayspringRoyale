package com.DayspringRoyale.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.DayspringRoyale.Model.Department;
import com.DayspringRoyale.services.DepartmentService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class DepartmentController {
	@Autowired
	private DepartmentService service;
	
	@PreAuthorize("isAutheticated()")
	@GetMapping("/department")
	public ResponseEntity<List<Department>> getAllDepartment(){
		return new ResponseEntity<>(service.getAllDepartment(), HttpStatus.OK);
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/department/{id}")
	public ResponseEntity<?> getDepartmentById(@PathVariable int id){
		Optional<Department> department = service.getDepartmentById(id);
		if(department.isPresent())
			return new ResponseEntity<>(department, HttpStatus.OK);
		else
			return new ResponseEntity<>("Department Doesn't Exist", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@PostMapping("/department")
	public ResponseEntity<?> addDepartment(@RequestBody Department department){
		try {
		Department department1 = service.addDepartment(department);
		return new ResponseEntity<>(department1, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			return new ResponseEntity<>("Failed To Add", HttpStatus.BAD_GATEWAY);
		}
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/department/search")
	public ResponseEntity<List<Department>> searchDepartment(@RequestParam String keyword){
		return new ResponseEntity<>(service.searchDepartment(keyword), HttpStatus.OK);
	}

}
