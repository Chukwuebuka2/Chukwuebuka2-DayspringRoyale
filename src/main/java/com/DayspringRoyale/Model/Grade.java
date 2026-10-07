package com.DayspringRoyale.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne
	@JoinColumn(name = "enrollment_id")
	private Enrollment enrollment;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	@OneToMany(mappedBy = "grade")
	private List<Attendance> attendance;
	@Min(0)
	@Max(10)
	private double assignment;
	@Min(0)
	@Max(10)
	private double quizscore;
	@Min(0)
	@Max(15)
	private double midsemester;
	@Min(0)
	@Max(60)
	private double exam;
	private double score;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Enrollment getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	public List<Attendance> getAttendance() {
		return attendance;
	}
	public void setAttendance(List<Attendance> attendance) {
		this.attendance = attendance;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public double getAttendancescore() {
		if(attendance == null || attendance.isEmpty())
			return 0;
		int counted = 0;
		int present = 0;
		for (Attendance a : attendance) {
			if (a.getStatus() == AttendanceStatus.Excused
					|| a.getStatus() == AttendanceStatus.Cancelled) continue;
			counted++;
		}
		if (counted == 0) 
			return 0;
		
		return Math.round((double) present/counted * 5 * 10)/10;
	}
	public double getAssignment() {
		return assignment;
	}
	public void setAssignment(double assignment) {
		this.assignment = assignment;
	}
	public double getQuizscore() {
		return quizscore;
	}
	public void setQuizscore(double quizscore) {
		this.quizscore = quizscore;
	}
	public double getMidsemester() {
		return midsemester;
	}
	public void setMidsemester(double midsemester) {
		this.midsemester = midsemester;
	}
	public double getExam() {
		return exam;
	}
	public void setExam(double exam) {
		this.exam = exam;
	}
	
	public double gettotalscore() {
		double attendancescore = getAttendancescore();
		double total = attendancescore + assignment + quizscore + midsemester + exam;
		return total;
	}
	
	@PrePersist
	@PreUpdate
	void updateScore() {
		this.score = gettotalscore();
	}
	
	public double getScore() {
		return score;
	}
	
	public String getGrade() {
		double total = gettotalscore(); 
		if (total <= 100 && total >= 80 )
			return "A";
		else if (total <= 79 && total >= 60)
			return "B";
		else if (total <= 59 && total >= 50)
			return "C";
		else if (total <= 49 && total >= 45)
			return "D";
		else if (total <= 44 && total >= 40)
			return "E";
		else
			return "F";
	}

}
