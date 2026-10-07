package com.DayspringRoyale.Model;

public enum Semester {
	
	First(1),
	Second(2),
	Summer(3);
	
	private final int number;
	Semester(int number){this.number = number;} 
	
	public int getNumber() {
		return number;
	}

}
