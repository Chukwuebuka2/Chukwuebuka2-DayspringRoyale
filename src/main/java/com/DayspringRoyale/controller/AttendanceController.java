package com.DayspringRoyale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DayspringRoyale.Model.Attendance;
import com.DayspringRoyale.services.AttendanceService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN', 'SUPER_ADMIN')")
public class AttendanceController {
	@Autowired
	private AttendanceService service;
	
	@PreAuthorize("hasRole('LECTURER', 'HOD', 'COURSE_ADVISOR')")
	@GetMapping("/attendance")
	public ResponseEntity<List<Attendance>> getAttendance(){
		return new ResponseEntity<>(service.getAttendance(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('LECTURER')")
	@PostMapping("/attendance")
	public ResponseEntity<?> addAttendance(@RequestBody Attendance attendance){
		try {
			Attendance attend = service.addAttendance(attendance);
			return new ResponseEntity<>(attend, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("invalid Input", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('LECTURER')")
	@PatchMapping("/attendance")
	public ResponseEntity<List<Attendance>> updateAttendance(@RequestBody List<Attendance> attendance){
		return new ResponseEntity<>(service.updateAttendance(attendance), HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('LECTURER', 'HOD')")
	@GetMapping("/attendance/search")
	public ResponseEntity<List<Attendance>> searchAttendance(@RequestParam String keyword){
		return new ResponseEntity<>(service.searchAttendance(keyword), HttpStatus.OK);
	}

}
