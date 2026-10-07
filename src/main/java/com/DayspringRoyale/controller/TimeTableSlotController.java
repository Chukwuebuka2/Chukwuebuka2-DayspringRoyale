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

import com.DayspringRoyale.Model.TimeTableSlot;
import com.DayspringRoyale.services.TimeTableSlotService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class TimeTableSlotController {
	@Autowired
	private TimeTableSlotService service;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/timeslot")
	public ResponseEntity<List<TimeTableSlot>> getTimeSlot(){
		return new ResponseEntity<>(service.getTimeSlot(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('HOD','FACAULTY')")
	@PostMapping("/timeslot")
	public ResponseEntity<?> addTimeSlot(@RequestBody TimeTableSlot timetableslot){
		try {
			TimeTableSlot timeslot = service.addTimeSlot(timetableslot);
			return new ResponseEntity<>(timeslot, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Invalid", HttpStatus.BAD_REQUEST);
		}
	}

}
