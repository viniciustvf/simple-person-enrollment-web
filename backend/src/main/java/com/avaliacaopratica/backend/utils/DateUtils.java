package com.avaliacaopratica.backend.utils;

import com.avaliacaopratica.backend.exceptions.IntegrityViolation;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static DateTimeFormatter dtfUSDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static String localDateToStr(LocalDate date) {
        return dtfUSDate.format(date);
    }

    public static LocalDate jsonToLocalDate(String dateStr) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateStr);
        return zonedDateTime.toLocalDate();
    }

    public static Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IntegrityViolation("Data de aniversário nula.");
        }

        Integer age = Period.between(birthDate, LocalDate.now()).getYears();

        if (birthDate.getDayOfYear() > LocalDate.now().getDayOfYear()) {
            age--;
        }
        return age;
    }
}
