package com.DayspringRoyale.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.School;

@Repository
public interface SchoolRepo extends JpaRepository<School, Integer>{
	
	@Query("SELECT s FROM School s WHERE " +
			"LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<School> searchSchool(String keyword); 

}
