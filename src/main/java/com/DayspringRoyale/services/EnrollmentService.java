package com.DayspringRoyale.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.Enrollment;
import com.DayspringRoyale.Model.EnrollmentStatus;
import com.DayspringRoyale.Repository.EnrollmentRepo;

@Service
public class EnrollmentService {
	@Autowired
	private EnrollmentRepo repo;

	public List<Enrollment> getAllEnrollment() {
		return repo.findAll();
	}

	public Optional<Enrollment> getEnrollmentById(int id) {
		return repo.findById(id);
	}

	public Enrollment newEnrollment(Enrollment enrollment) {
		return repo.save(enrollment);
	}

	public Enrollment changeStatus(int id, EnrollmentStatus newStatus) {
		Enrollment enrollment = repo.findById(id).orElseThrow(() ->
		new RuntimeException("No valid class"));
		if(enrollment.getStatus() != EnrollmentStatus.Pending) {
			throw new IllegalArgumentException("Student already" + enrollment.getStatus());
		}
		enrollment.setStatus(newStatus);
		return repo.save(enrollment);
	}

	public Enrollment rejectStatus(int id) {
		return changeStatus(id, EnrollmentStatus.Failed);
	}

	public Enrollment acceptStatus(int id) {
		return changeStatus(id, EnrollmentStatus.SuccessFull);
	}

}
