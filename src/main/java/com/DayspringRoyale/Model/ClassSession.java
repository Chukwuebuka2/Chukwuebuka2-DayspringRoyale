package com.DayspringRoyale.Model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassSession {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "timetableslot_id")
	private TimeTableSlot timetableslot;
	
	@OneToMany(mappedBy = "classsession")
	private List<Attendance> attendance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TimeTableSlot getTimetableslot() {
		return timetableslot;
	}

	public void setTimetableslot(TimeTableSlot timetableslot) {
		this.timetableslot = timetableslot;
	}

	public List<Attendance> getAttendance() {
		return attendance;
	}

	public void setAttendance(List<Attendance> attendance) {
		this.attendance = attendance;
	}
	
	

}
