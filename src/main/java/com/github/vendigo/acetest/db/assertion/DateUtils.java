package com.github.vendigo.acetest.db.assertion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateUtils {
    private static DateTimeFormatter outDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Object parseDate(String str) {
        try {
            return LocalDateTime.parse(str, dateTimeFormat);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(str, dateFormat);
        }
    }

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
