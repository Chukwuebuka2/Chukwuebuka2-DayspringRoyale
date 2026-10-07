package com.DayspringRoyale.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.Course;
import com.DayspringRoyale.Model.EnrollmentStatus;
import com.DayspringRoyale.Model.Grade;
import com.DayspringRoyale.Model.Student;

@Repository
public interface GradeRepo extends JpaRepository<Grade, Integer> {
	@Query("""
			SELECT COALESCE (SUM(e.course.creditHours), 0)
			FROM Grade g
			JOIN g.enrollment e
			WHERE e.student.id = :studentId
			And e.academicyear.id = :termId
			And e.status = :status
			And g.score >= 50
			""")
	long passedCredits(@Param("studentId") int studentId,
					@Param("termId") int termId,
					@Param("status") EnrollmentStatus status);
	
	@Query("SELECT g FROM Grade g WHERE g.enrollment.student.id = :studentId")
	List<Grade> getGradeByStudentId(Student student);
	
	@Query("SELECT g FROM Grade g WHERE g.enrollment.course.id = :courseId")
	List<Grade> getGradeByCourseId(Course course);
}
