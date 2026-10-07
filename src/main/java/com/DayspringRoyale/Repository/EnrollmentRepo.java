package com.DayspringRoyale.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.Enrollment;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Integer> {

}
