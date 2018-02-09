package com.github.vendigo.acetest.conversion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class ToStringConverter {
    static String convertForInserting(Object o) {
        if (o instanceof LocalDate) {
            return DateConversions.formatLocalDate((LocalDate) o);
        }
        if (o instanceof LocalDateTime) {
            return DateConversions.formatLocalDateTime((LocalDateTime) o);
        }
        if (o instanceof LocalTime) {
            return DateConversions.formatLocalTime((LocalTime) o);
        }
        return o.toString();
    }
}
