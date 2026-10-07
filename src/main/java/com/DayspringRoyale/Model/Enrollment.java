package com.DayspringRoyale.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	@OneToOne(mappedBy = "enrollment")
	private Grade grade;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate enrollmentdate;
	@Enumerated(EnumType.STRING)
	private EnrollmentStatus status;
	@ManyToOne
	@JoinColumn(name = "academicyear_id")
	private AcademicYear academicyear;
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public EnrollmentStatus getStatus() {
		return status;
	}
	public void setStatus(EnrollmentStatus status) {
		this.status = status;
	}
	public LocalDate getEnrollmentdate() {
		return enrollmentdate;
	}
	public void setEnrollmentdate(LocalDate enrollmentdate) {
		this.enrollmentdate = enrollmentdate;
	}
	public EnrollmentStatus getStaus() {
		return status;
	}
	public void setStaus(EnrollmentStatus staus) {
		this.status = staus;
	}
	public AcademicYear getAcademicyear() {
		return academicyear;
	}
	public void setAcademicyear(AcademicYear academicyear) {
		this.academicyear = academicyear;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}

}
