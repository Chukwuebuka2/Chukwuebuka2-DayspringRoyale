package com.DayspringRoyale.Model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Entity;
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
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableSlot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private DayOfWeek dayofweek;
	private LocalTime starttime;
	private LocalTime endTime;
	private String location;
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff staff;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DayOfWeek getDayofweek() {
		return dayofweek;
	}
	public void setDayofweek(DayOfWeek dayofweek) {
		this.dayofweek = dayofweek;
	}
	public LocalTime getStarttime() {
		return starttime;
	}
	public void setStarttime(LocalTime starttime) {
		this.starttime = starttime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	

}
