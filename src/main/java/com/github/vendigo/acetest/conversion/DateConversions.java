package com.github.vendigo.acetest.conversion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateConversions {
    private static DateTimeFormatter outDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Parses LocalDate or LocalDateTime from the String (Scenario -> assertion)
     */
    public static Object parseDate(String str) {
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
     * Format LocalDate for inserting into as String.
     */
    public static String formatLocalDate(LocalDate localDate) {
        return localDate.format(dateFormat);
    }

    /**
     * Format LocalDateTime for inserting into db as String.
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(outDateTimeFormat);
    }

    /**
     * Format Date for inserting into db as String.
     */
    public static String formatDateTime(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        if (hour + minute + second == 0) {
            return localDateTime.format(DateTimeFormatter.ISO_DATE);
        } else {
            return localDateTime.format(outDateTimeFormat);
        }
    }

    /**
     * Convert Date to LocalDate or LocalDateTime based on time. (Db -> Assertion)
     */
    public static Object convertDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        if (hour + minute + second == 0) {
            return localDateTime.toLocalDate();
        } else {
            return localDateTime;
        }
    }
}
