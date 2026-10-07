package com.DayspringRoyale.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;
	@ManyToOne
	@JoinColumn(name = "classsession_id")
	private ClassSession classsession;
	@ManyToOne
	@JoinColumn(name = "grade_id")
	private Grade grade;

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
	

	public AttendanceStatus getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}

	public ClassSession getClasssession() {
		return classsession;
	}

	public void setClasssession(ClassSession classsession) {
		this.classsession = classsession;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	public int getpresentvalue() {
		if (status == AttendanceStatus.Present)
			return 1;
		else
			return 0;
	}

}
