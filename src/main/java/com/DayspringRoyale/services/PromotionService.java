package com.DayspringRoyale.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.AcademicYear;
import com.DayspringRoyale.Model.EnrollmentStatus;
import com.DayspringRoyale.Model.Student;
import com.DayspringRoyale.Model.StudentLevel;
import com.DayspringRoyale.Model.StudentStatus;
import com.DayspringRoyale.Repository.AcademicyearRepo;
import com.DayspringRoyale.Repository.GradeRepo;
import com.DayspringRoyale.Repository.StudentRepo;

import jakarta.transaction.Transactional;

@Service
public class PromotionService {
	private int REQUIRED_CREDIT_BY_TERM = 20;
	@Autowired
	private AcademicyearRepo academicyearrepo;
	
	@Autowired
	private GradeRepo graderepo;
	
	@Autowired
	private StudentRepo studentrepo;
	
	@Transactional
	public boolean cheakAndPromote(Student s, int startYear) {
		if (s.getLastPromotedYear() != null && s.getLastPromotedYear() >= startYear)
			return false;
		List<AcademicYear> terms = academicyearrepo.findByStartYear(startYear);
		boolean ok = terms.stream().allMatch(t -> 
			graderepo.passedCredits(s.getId(), t.getId(), EnrollmentStatus.SuccessFull) >= REQUIRED_CREDIT_BY_TERM );
		if(!ok)
			return false;
		
		StudentLevel next = s.getLevel().next();
		if (next != null)s.setLevel(next);
		else s.setStatus(StudentStatus.Graduated);
		s.setLastPromotedYear(startYear);
		studentrepo.save(s);
		return true;
	}

}
