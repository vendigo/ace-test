package com.github.vendigo.acetest.db.assertion;

import java.math.RoundingMode;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static com.google.common.math.DoubleMath.roundToLong;
import static java.util.stream.Collectors.toList;

public class DbDataFormatter {
    public static final String NULL_PLACEHOLDER = "<null>";
    public static final String EMPTY_STRING_PLACEHOLDER = "<empty>";
    public static final String EMPTY_STRING = "";

    private static Format dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static Format dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static Format numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

    private static DateTimeFormatter outDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

    public static List<Map<String, Object>> parseRecords(List<Map<String, String>> rawRecords) {
        return rawRecords.stream()
                .map(DbDataFormatter::parseRow)
                .collect(toList());
    }

    public static List<Map<String, Object>> adjustRecords(List<Map<String, Object>> rawRecords) {
        return rawRecords.stream()
                .map(DbDataFormatter::adjustRow)
                .collect(toList());
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

    static Map<String, Object> parseRow(Map<String, String> map) {
        Map<String, Object> result = new HashMap<>();
        map.entrySet().stream()
                .filter(column -> parseObject(column.getValue()) != null)
                .forEach(column -> result.put(column.getKey().toUpperCase(), parseObject(column.getValue())));
        return result;
    }

    static Map<String, Object> adjustRow(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        map.entrySet().stream()
                .forEach(column -> result.put(column.getKey(), adjustNumber(column.getValue())));
        return result;
    }

    static Object parseObject(String str) {
        if (isPlaceholder(str)) {
            return resolvePlaceholder(str);
        }

        Optional<Object> parsed = Stream.of(dateTimeFormat, dateFormat, numberFormat)
                .map(format -> tryFormat(str, format))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        return parsed.orElse(str);
    }

    static Object adjustNumber(Object o) {
        if (o instanceof Number) {
            Number n = (Number) o;
            if (n instanceof Integer) {
                return n.longValue();
            } else if (n.doubleValue() - n.longValue() == 0) {
                return roundToLong(n.doubleValue(), RoundingMode.FLOOR);
            }
        }
        return o;
    }

    private static Object resolvePlaceholder(String str) {
        switch (str) {
            case NULL_PLACEHOLDER:
                return null;
            case EMPTY_STRING_PLACEHOLDER:
                return EMPTY_STRING;
        }
        return null;
    }

    private static boolean isPlaceholder(String str) {
        return str != null && (str.equals(NULL_PLACEHOLDER) || str.equals(EMPTY_STRING_PLACEHOLDER));
    }

    private static Optional<Object> tryFormat(String str, Format dateFormat) {
        try {
            return Optional.of(dateFormat.parseObject(str));
        } catch (ParseException e) {
            return Optional.empty();
        }
    }
}
