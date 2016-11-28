package com.github.vendigo.acetest.db.assertion;

import org.hamcrest.text.IsEmptyString;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

public class DbDataFormatterTest {
    @Test
    public void parseDate() throws Exception {
        Object parsed = DbDataFormatter.parseObject("2016-11-23");
        assertThat(parsed, instanceOf(Date.class));
    }

    @Test
    public void parseDateTime() throws Exception {
        Object parsed = DbDataFormatter.parseObject("2016-11-23 8:40");
        assertThat(parsed, instanceOf(Date.class));
    }

    @Test
    public void parseLong() throws Exception {
        Object parsed = DbDataFormatter.parseObject("154");
        assertThat(parsed, instanceOf(Long.class));
    }

    @Test
    public void parseDoubleWithoutFraction() throws Exception {
        Object parsed = DbDataFormatter.parseObject("154.0");
        assertThat(parsed, instanceOf(Long.class));
    }

    @Test
    public void parseDouble() throws Exception {
        Object parsed = DbDataFormatter.parseObject("154.01");
        assertThat(parsed, instanceOf(Double.class));
    }

    @Test
    public void parseString() throws Exception {
        Object parsed = DbDataFormatter.parseObject("Hello, now is 8:45");
        assertThat(parsed, instanceOf(String.class));
    }

    @Test
    public void parseNullPlaceholder() throws Exception {
        Object parsed = DbDataFormatter.parseObject("<null>");
        assertThat(parsed, nullValue());
    }

    @Test
    public void parseEmptyStringPlaceholder() throws Exception {
        Object parsed = DbDataFormatter.parseObject("<empty>");
        assertThat(parsed, instanceOf(String.class));
        assertThat((String)parsed, IsEmptyString.isEmptyString());
    }

    @Test
    public void adjustInteger() throws Exception {
        Object result = DbDataFormatter.adjustObject(100);
        assertThat(result, instanceOf(Long.class));
        assertThat(result, equalTo(100L));
    }

    @Test
    public void adjustDouble() throws Exception {
        Object result = DbDataFormatter.adjustObject(100.5);
        assertThat(result, instanceOf(Double.class));
        assertThat(result, equalTo(100.5));
    }

    @Test
    public void adjustDoubleToLong() throws Exception {
        Object result = DbDataFormatter.adjustObject(100.0);
        assertThat(result, instanceOf(Long.class));
        assertThat(result, equalTo(100L));
    }

    @Test
    public void adjustString() throws Exception {
        Object result = DbDataFormatter.adjustObject("Hello");
        assertThat(result, instanceOf(String.class));
        assertThat(result, equalTo("Hello"));
    }
}
