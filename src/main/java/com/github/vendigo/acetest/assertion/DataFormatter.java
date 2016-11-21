package com.github.vendigo.acetest.assertion;

import java.util.Date;

public class DataFormatter {
    public static final String DATE_FORMAT = "%1$tY-%1$tm-%1$td";
    public static final String FLOAT_FORMAT = "%.2f";

    public static String format(Object o) {
        if (o == null) {
            return null;
        }

        if (o instanceof Date) {
            return String.format(DATE_FORMAT, o);
        } else if (o instanceof Double) {
            return String.format(FLOAT_FORMAT, o);
        } else {
            return o.toString();
        }
    }
}
