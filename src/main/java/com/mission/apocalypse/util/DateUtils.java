package com.mission.apocalypse.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateUtils {

    public static LocalDate parseDate(final String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        return LocalDate.parse(date, formatter);
    }

}
