package com.DayspringRoyale.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.Attendance;
import com.DayspringRoyale.Model.ClassSession;
import com.DayspringRoyale.Model.Student;
import com.DayspringRoyale.Repository.AttendanceRepo;
import com.DayspringRoyale.Repository.ClassSessionRepo;
import com.DayspringRoyale.Repository.StudentRepo;

@Service
public class AttendanceService {
	
	@Autowired
	private AttendanceRepo repo;

	@Autowired
	private StudentRepo studentrepo;
	
	@Autowired
	private ClassSessionRepo classsessionrepo;
	

	public List<Attendance> getAttendance() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}


	public Attendance addAttendance(Attendance attendance) {
		Student student = studentrepo.findById(attendance.getStudent()
				.getId()).orElseThrow(() -> new RuntimeException("Student Not Found"));
		ClassSession classsession = classsessionrepo.findById(attendance.getClasssession()
				.getId()).orElseThrow(() -> new RuntimeException("Class Not Found"));
		
		attendance.setStudent(student);
		attendance.setClasssession(classsession);
		return repo.save(attendance);
	}


	public List<Attendance> searchAttendance(String keyword) {
		return repo.searchAttendance(keyword);
	}


	public List<Attendance> updateAttendance(List<Attendance> attendance) {
		return repo.saveAll(attendance);
	}

}
