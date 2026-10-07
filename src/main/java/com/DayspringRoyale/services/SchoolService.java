package com.DayspringRoyale.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.School;
import com.DayspringRoyale.Repository.SchoolRepo;

@Service
public class SchoolService {
	@Autowired
	private SchoolRepo repo;

	public List<School> getAllSchool() {
		return repo.findAll();
	}

	public Optional<School> getSchool(int id) {
		return repo.findById(id);
	}

	public School addSchool(School school) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<School> searchSchool(String keyword) {
		return repo.searchSchool(keyword);
	}

}
