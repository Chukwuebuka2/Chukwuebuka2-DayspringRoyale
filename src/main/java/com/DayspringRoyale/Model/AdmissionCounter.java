package com.DayspringRoyale.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AdmissionCounter {
	@Id
	private Integer year;
	private Integer lastSequence;
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getLastSequence() {
		return lastSequence;
	}
	public void setLastSequence(Integer lastSequence) {
		this.lastSequence = lastSequence;
	}
	
	

}
