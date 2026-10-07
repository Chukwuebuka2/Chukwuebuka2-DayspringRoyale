package com.DayspringRoyale.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.University;

@Repository
public interface UniversityRepo extends JpaRepository<University, Integer>{

}
