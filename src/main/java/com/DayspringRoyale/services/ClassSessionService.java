package com.DayspringRoyale.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.ClassSession;
import com.DayspringRoyale.Model.TimeTableSlot;
import com.DayspringRoyale.Repository.ClassSessionRepo;
import com.DayspringRoyale.Repository.TimeTableSlotRepo;

@Service
public class ClassSessionService {
	@Autowired
	private ClassSessionRepo repo;
	
	@Autowired
	private TimeTableSlotRepo timetablerepo;

	public List<ClassSession> getClasses() {
		return repo.findAll();
	}

	public ClassSession addClassSession(ClassSession classsession) {
		TimeTableSlot timetableslot = timetablerepo.findById(classsession
				.getTimetableslot().getId()).orElseThrow(()-> new RuntimeException());
		
		classsession.setTimetableslot(timetableslot);
		return repo.save(classsession);
	}

}
