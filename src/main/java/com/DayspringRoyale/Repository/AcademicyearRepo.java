package com.DayspringRoyale.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.AcademicYear;

import jakarta.transaction.Transactional;

@Repository
public interface AcademicyearRepo extends JpaRepository<AcademicYear, Integer >{
	@Modifying
	@Transactional
	@Query("Update AcademicYear a SET a.current = false")
	void demoteAllYears();

	@Query("SELECT a FROM AcademicYear a Where a.current = true")
	AcademicYear findCurrent();
	
	List<AcademicYear> findByStartYear(int startYear);

}
