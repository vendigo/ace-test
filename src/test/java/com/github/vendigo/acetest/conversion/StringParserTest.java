package com.github.vendigo.acetest.conversion;

import org.hamcrest.text.IsEmptyString;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

public class StringParserTest {
    @Test
    public void parseDate() throws Exception {
        Object parsed = StringParser.parseString("2016-11-23");
        assertThat(parsed, instanceOf(LocalDate.class));
    }

    @Test
    public void parseDateTime() throws Exception {
        Object parsed = StringParser.parseString("2016-11-23 08:40");
        assertThat(parsed, instanceOf(LocalDateTime.class));
    }

    @Test
    public void parseLong() throws Exception {
        Object parsed = StringParser.parseString("154");
        assertThat(parsed, instanceOf(Long.class));
    }

    @Test
    public void parseDoubleWithoutFraction() throws Exception {
        Object parsed = StringParser.parseString("154.0");
        assertThat(parsed, instanceOf(Long.class));
    }

    @Test
    public void parseDouble() throws Exception {
        Object parsed = StringParser.parseString("154.01");
        assertThat(parsed, instanceOf(Double.class));
    }

    @Test
    public void parseString() throws Exception {
        Object parsed = StringParser.parseString("Hello, now is 8:45");
        assertThat(parsed, instanceOf(String.class));
    }

    @Test
    public void parseNullPlaceholder() throws Exception {
        Object parsed = StringParser.parseString("{null}");
        assertThat(parsed, nullValue());
    }

    @Test
    public void parseEmptyStringPlaceholder() throws Exception {
        Object parsed = StringParser.parseString("{empty}");
        assertThat(parsed, instanceOf(String.class));
        assertThat((String)parsed, IsEmptyString.isEmptyString());
    }

    @Test
    public void parseQuotedString() throws Exception {
        Object parsed = StringParser.parseString("\"100500\"");
        assertThat(parsed, instanceOf(String.class));
        assertThat(parsed, equalTo("100500"));
    }
}
