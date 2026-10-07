package com.DayspringRoyale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.University;
import com.DayspringRoyale.Repository.UniversityRepo;

@Service
public class UniversityService {
	@Autowired
	private UniversityRepo repo;

	public University addUniversity(University university) {
	return repo.save(university);
	}

	public University getUniversity() {
		return repo.findAll().stream().findFirst().orElse(null);
	}
	
	

}
