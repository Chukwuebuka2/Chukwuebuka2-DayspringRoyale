package com.DayspringRoyale.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.ClassSession;

@Repository
public interface ClassSessionRepo extends JpaRepository<ClassSession, Integer>{

}
