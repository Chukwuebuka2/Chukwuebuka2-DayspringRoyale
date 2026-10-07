package com.DayspringRoyale.Model;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYear {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Integer startYear; 
	private Integer endYear;
	private LocalDate startdate;
	private LocalDate enddate;
	@OneToMany(mappedBy = "academicyear")
	private List<Enrollment> enrollment; 
	private boolean current;
	@Enumerated(EnumType.STRING)
	private Semester semester;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getStartYear() {
		return startYear;
	}
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}
	public Integer getEndYear() {
		return endYear;
	}
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}
	public LocalDate getStartdate() {
		return startdate;
	}
	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}
	public LocalDate getEnddate() {
		return enddate;
	}
	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}
	public boolean isCurrent() {
		return current;
	}
	public void setCurrent(boolean current) {
		this.current = current;
	}
	public List<Enrollment> getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(List<Enrollment> enrollment) {
		this.enrollment = enrollment;
	}
	
	@PrePersist
	@PreUpdate
	public void validateAndCalculateYear() {
		if (this.startYear != null) {
			this.endYear = this.startYear + 1;
		}
	}
	
	
	@Transient
	public String getFullYear() {
		if(startYear == null || endYear == null)
			return null;
		String startShort = String.valueOf(startYear % 100);
		String endShort = String.valueOf(endYear % 100);
		return startShort + "/" + endShort;
	}
	
	public String getcurrentSemester() {
		String year = getFullYear();
		if(year == null || semester == null)
			return null;
		return year + "." + semester.getNumber();
	}

}
