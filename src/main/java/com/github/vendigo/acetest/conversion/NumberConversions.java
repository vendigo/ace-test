package com.github.vendigo.acetest.conversion;

import java.math.RoundingMode;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.google.common.math.DoubleMath.roundToLong;

public class NumberConversions {
    private static Format numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

    public static Object parseNumber(String str) {
        try {
            return integerToLong((Number)numberFormat.parseObject(str));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Number integerToLong(Number n) {
        if (n instanceof Integer) {
            return n.longValue();
        } else if (n.doubleValue() - n.longValue() == 0) {
            return roundToLong(n.doubleValue(), RoundingMode.FLOOR);
        }
        return n;
    }
}
