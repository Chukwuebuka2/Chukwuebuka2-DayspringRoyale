package com.DayspringRoyale.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DayspringRoyale.Model.Staff;
import com.DayspringRoyale.Model.Student;
import com.DayspringRoyale.Model.SystemRole;
import com.DayspringRoyale.Model.User;
import com.DayspringRoyale.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@PostMapping("/register/student")
	public void addNewStudent(@RequestBody Student student){
		service.addNewStudent(student);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@PostMapping("/register/staff")
	public void addNewStaff(@RequestBody Staff staff, Set<SystemRole> role) {
		service.addNewStaff(staff, role);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		return  service.verify(user);
	}


}
