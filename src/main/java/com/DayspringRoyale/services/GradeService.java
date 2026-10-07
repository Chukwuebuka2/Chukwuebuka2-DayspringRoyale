package com.DayspringRoyale.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.Course;
import com.DayspringRoyale.Model.Enrollment;
import com.DayspringRoyale.Model.Grade;
import com.DayspringRoyale.Model.Student;
import com.DayspringRoyale.Repository.CourseRepo;
import com.DayspringRoyale.Repository.GradeRepo;
import com.DayspringRoyale.Repository.StudentRepo;

import jakarta.transaction.Transactional;

@Service
public class GradeService {
	@Autowired
	private GradeRepo repo;
	
	private StudentRepo studentrepo;
	
	@Autowired
	private CourseRepo courserepo;
	
	@Autowired
	private PromotionService promotionservice;

	public List<Grade> getStudentGrade(int studentId) {
		Student student = studentrepo.findById(studentId).orElseThrow(() ->
		new RuntimeException("Student Not Found"));
		return repo.getGradeByStudentId(student);
	}
		
	@Transactional
	public Grade saveGrade(Grade grade) {
		Grade saved = repo.save(grade);
		Enrollment e = saved.getEnrollment();
		promotionservice.cheakAndPromote(e.getStudent(), e.getAcademicyear().getStartYear());
		return saved;
	}

	public Optional<Grade> getGradeById(int studentId) {
		return repo.findById(studentId);
	}

	public List<Grade> getCourseGrade(int courseId) {
		Course course = courserepo.findById(courseId).orElseThrow(() ->
		new RuntimeException("Course not available"));
		return repo.getGradeByCourseId(course);
	}
}
