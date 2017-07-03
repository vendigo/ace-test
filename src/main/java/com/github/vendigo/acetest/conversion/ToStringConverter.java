package com.github.vendigo.acetest.conversion;

import java.time.LocalDate;
import java.time.LocalDateTime;

class ToStringConverter {
    static String convertForInserting(Object o) {
        if (o instanceof LocalDate) {
            return DateConversions.formatLocalDate((LocalDate)o);
        }
        if (o instanceof LocalDateTime) {
            return DateConversions.formatLocalDateTime((LocalDateTime)o);
        }
        return o.toString();
    }
}
