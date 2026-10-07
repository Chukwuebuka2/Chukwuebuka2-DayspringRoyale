package com.DayspringRoyale.Model;

public enum StudentLevel {
	Level_100,
	Level_200,
	Level_300,
	Level_400,
	Level_500;
	
	public StudentLevel next() {
		StudentLevel[] values = values();
		return ordinal() + 1 < values.length ? values[ordinal() + 1] : null;
	}
	
	public boolean isFinal() {
		return next() == null; 
	}

}
