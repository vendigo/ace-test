package com.github.vendigo.acetest.conversion;

import static liquibase.util.StringUtils.trimToNull;

class StringParser {
    private static final String NULL_PLACEHOLDER = "{null}";
    private static final String EMPTY_STRING_PLACEHOLDER = "{empty}";
    private static final String EMPTY_STRING = "";
    private static final String QUOTED_STRING_REGEX  = "\".*\"";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    static Object parseString(String str) {
        str = trimToNull(str);
        if (str == null) {
            return null;
        }

        if (str.matches(QUOTED_STRING_REGEX)) {
            return unQuote(str);
        }

        if (isPlaceholder(str)) {
            return resolvePlaceholder(str);
        }

        Boolean booleanValue = parseBool(str);
        if (booleanValue != null) {
            return booleanValue;
        }

        Object date = DateConversions.parseDate(str);
        if (date != null) {
            return date;
        }

        Object number = NumberConversions.parseNumber(str);
        if (number != null) {
            return number;
        }

        return str;
    }

    private static Boolean parseBool(String str) {
        if (str.equalsIgnoreCase(TRUE)) {
            return Boolean.TRUE;
        }

        if (str.equalsIgnoreCase(FALSE)) {
            return Boolean.FALSE;
        }

        return null;
    }

    private static Object unQuote(String str) {
        return str.substring(1, str.length()-1);
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
}
