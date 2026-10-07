package com.DayspringRoyale.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.AdmissionCounter;

import jakarta.persistence.LockModeType;

@Repository
public interface AdmissionCounterRepo extends JpaRepository<AdmissionCounter, Integer> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT c fROM AdmissionCounter c WHERE c.year= :year")
	Optional<AdmissionCounter> findByYearForUpdate(@Param("year") int year);

}
