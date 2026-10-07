package com.DayspringRoyale.services;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.AdmissionCounter;
import com.DayspringRoyale.Repository.AdmissionCounterRepo;

import jakarta.transaction.Transactional;

@Service
public class MatricNumberService {

    @Autowired
    private AdmissionCounterRepo counterRepo;

    @Transactional
    public String generateMatricNumber() {
        int currentYear = Year.now().getValue();
        
        AdmissionCounter counter = counterRepo
                .findByYearForUpdate(currentYear)
                .orElseGet(() -> {
                    AdmissionCounter c = new AdmissionCounter();
                    c.setYear(currentYear);
                    c.setLastSequence(0);
                    return c;
                });

        int nextSeq = counter.getLastSequence() + 1;
        counter.setLastSequence(nextSeq);
        counterRepo.save(counter);

        return currentYear + "/" + String.format("%04d", nextSeq);
    }
}