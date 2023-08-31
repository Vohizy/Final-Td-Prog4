package com.example.prog4.service;

import java.time.LocalDate;

public class CalculatorAge {
    public static int age(LocalDate birthDate) {
        if (birthDate == null){
            return 0;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate reference = LocalDate.of(currentDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth());
        int birthYear = birthDate.getYear();
        int age = currentDate.getYear() - birthYear;
        if(reference.isAfter(birthDate)){
            age-= 1;
        }
        return age;
    }
}
