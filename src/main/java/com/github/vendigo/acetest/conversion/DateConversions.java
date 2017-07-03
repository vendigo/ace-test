package com.github.vendigo.acetest.conversion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

class DateConversions {
    private static DateTimeFormatter outDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Parses LocalDate or LocalDateTime from the String (from Scenario to Assertion)
     */
    static Object parseDate(String str) {
        try {
            return LocalDateTime.parse(str, dateTimeFormat);
        } catch (DateTimeParseException e1) {
            try {
                return LocalDate.parse(str, dateFormat);
            } catch (DateTimeParseException e2) {
                return null;
            }
        }
    }

    /**
     * Format LocalDate for inserting into db as String.
     */
    static String formatLocalDate(LocalDate localDate) {
        return localDate.format(dateFormat);
    }

    /**
     * Format LocalDateTime for inserting into db as String.
     */
    static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(outDateTimeFormat);
    }

    /**
     * Convert Date to LocalDate or LocalDateTime based on time. (from Db to Assertion)
     */
    static Object convertDate(Date date) {
        LocalDateTime localDateTime = toLocalDate(date);
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        if (hour + minute + second == 0) {
            return localDateTime.toLocalDate();
        } else {
            return localDateTime;
        }
    }

    private static LocalDateTime toLocalDate(Date date) {
        if (date instanceof java.sql.Date) {
            LocalDate localDate = ((java.sql.Date) date).toLocalDate();
            return LocalDateTime.of(localDate, LocalTime.of(0, 0));
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
