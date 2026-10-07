package com.DayspringRoyale.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.Attendance;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer>{
	@Query("SELECT a FROM Attendance a WHERE "+
				"LOWER(a.student) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " + 
			"LOWER(a.classsession) LIKE LOWER(CONCAT('%', :keyword,'%'))" )
	List<Attendance> searchAttendance(String keyword);

}
