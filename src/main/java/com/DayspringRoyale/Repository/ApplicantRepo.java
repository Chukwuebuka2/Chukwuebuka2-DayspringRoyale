package com.DayspringRoyale.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.Applicant;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant, Integer>{

}
