package com.github.vendigo.acetest.conversion;

import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

class NumberConversions {
    private static final double EPSILON = 0.0001;
    private static Format numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

    static Object parseNumber(String str) {
        try {
            return integerToLong((Number) numberFormat.parseObject(str));
        } catch (ParseException e) {
            return null;
        }
    }

    static Number integerToLong(Number n) {
        if (n instanceof Integer || n.doubleValue() - n.longValue() < EPSILON) {
            return n.longValue();
        }
        return n;
    }
}
