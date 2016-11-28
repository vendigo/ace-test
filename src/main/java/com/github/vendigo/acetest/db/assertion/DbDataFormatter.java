package com.github.vendigo.acetest.db.assertion;

import java.math.RoundingMode;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static com.google.common.math.DoubleMath.roundToLong;
import static java.util.stream.Collectors.toList;

public class DbDataFormatter {
    public static final String NULL_PLACEHOLDER = "{null}";
    public static final String EMPTY_STRING_PLACEHOLDER = "{empty}";
    public static final String EMPTY_STRING = "";

    private static Format dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static Format dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static Format numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

    public static List<Map<String, Object>> parseRecordsForInsert(List<Map<String, String>> rawRecords) {
        return rawRecords.stream()
                .map(DbDataFormatter::parseRowForInsert)
                .collect(toList());
    }

    public static List<Map<String, Object>> adjustRecordsForAssert(List<Map<String, Object>> rawRecords) {
        return rawRecords.stream()
                .map(DbDataFormatter::adjustRowForAssert)
                .collect(toList());
    }

    static Map<String, Object> parseRowForInsert(Map<String, String> map) {
        Map<String, Object> result = new HashMap<>();
        map.entrySet().stream()
                .filter(column -> parseObject(column.getValue()) != null)
                .forEach(column -> result.put(column.getKey().toUpperCase(), parseObject(column.getValue())));
        return result;
    }

    static Map<String, Object> adjustRowForAssert(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        map.entrySet().stream()
                .forEach(column -> result.put(column.getKey(), adjustObjectForAssert(column.getValue())));
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

    static Object adjustObjectForAssert(Object o) {
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
