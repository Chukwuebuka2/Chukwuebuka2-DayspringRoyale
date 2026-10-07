package com.DayspringRoyale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.DayspringRoyale.Model.AcademicYear;
import com.DayspringRoyale.Repository.AcademicyearRepo;

@Service
public class AcademicYearService {
	
	@Autowired
	private AcademicyearRepo repo;
	
	public void setCurrentAcademicYear(int id) throws Exception {
		repo.demoteAllYears();
		AcademicYear currentYear = repo.findById(id).orElseThrow(() ->  new Exception("Year Not Found"));
		currentYear.setCurrent(true);
		repo.save(currentYear);
	}

	public AcademicYear newAcademicYear(AcademicYear newyear) {
		return repo.save(newyear);
	}

	public AcademicYear getCurrentYear() {
		// TODO Auto-generated method stub
		return repo.findCurrent();
	}

}
