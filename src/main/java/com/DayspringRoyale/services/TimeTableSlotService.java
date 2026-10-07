package com.DayspringRoyale.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.Course;
import com.DayspringRoyale.Model.TimeTableSlot;
import com.DayspringRoyale.Repository.CourseRepo;
import com.DayspringRoyale.Repository.TimeTableSlotRepo;
@Service
public class TimeTableSlotService {
	
	@Autowired
	private TimeTableSlotRepo repo;
	
	@Autowired
	private CourseRepo courserepo;

	public List<TimeTableSlot> getTimeSlot() {
		return repo.findAll();
	}

	public TimeTableSlot addTimeSlot(TimeTableSlot timetableslot) {
		Course course = courserepo.findById(timetableslot.getCourse()
				.getId()).orElseThrow(() -> new RuntimeException("Course Not Found"));
		timetableslot.setCourse(course);
		return null;
	}

}
