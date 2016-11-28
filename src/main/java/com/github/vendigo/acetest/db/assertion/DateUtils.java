package com.github.vendigo.acetest.db.assertion;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    private static DateTimeFormatter outDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

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
