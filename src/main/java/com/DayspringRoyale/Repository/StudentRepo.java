package com.DayspringRoyale.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.Department;
import com.DayspringRoyale.Model.School;
import com.DayspringRoyale.Model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>{
	@Query("SELECT DISTINCT s FROM Student s " +
			"LEFT JOIN Enrollment e ON e.student = s " + 
			"LEFT JOIN e.course c WHERE " +
				"LOWER(s.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(s.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
				"LOWER(s.matricnumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Student> searchStudent(String keyword);

	@Query("SELECT s FROM Student s WHERE s.school.id = :schoolId")
	List<Student> getStudentBySchool(School school);

	@Query("SELECT s FROM Student s WHERE s.department.id = :deptId")
	List<Student> getStudentByDeprtment(Department dept);

}
