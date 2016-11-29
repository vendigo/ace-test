package com.github.vendigo.acetest.conversion;

import java.util.Date;

import static com.github.vendigo.acetest.conversion.NumberConversions.integerToLong;

public class ObjectConverter {
    public static Object convertForAssertion(Object o) {
        if (o instanceof Number) {
            return integerToLong((Number)o);
        }
        if (o instanceof Date) {
            return DateConversions.convertDate((Date)o);
        }
        return o;
    }
}
