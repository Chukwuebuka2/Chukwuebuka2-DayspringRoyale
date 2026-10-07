package com.DayspringRoyale.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.TimeTableSlot;

@Repository
public interface TimeTableSlotRepo extends JpaRepository<TimeTableSlot, Integer> {

}
