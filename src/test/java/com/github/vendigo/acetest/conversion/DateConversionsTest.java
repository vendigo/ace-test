package com.github.vendigo.acetest.conversion;

import org.junit.Test;

import java.time.*;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class DateConversionsTest {
    @Test
    public void convertToLocalDateTime() {
        Date date = getDate(2016, Month.NOVEMBER, 24, 8, 25);
        Object result = DateConversions.convertDate(date);
        assertThat(result, instanceOf(LocalDateTime.class));
    }

    @Test
    public void convertToLocalDate() {
        Date date = getDate(2016, Month.NOVEMBER, 24, 0, 0);
        Object result = DateConversions.convertDate(date);
        assertThat(result, instanceOf(LocalDate.class));
    }

    @Test
    public void convertToLocalTime() {
        Date date = getDate(2016, Month.NOVEMBER, 24, 0, 0);
        Object result = DateConversions.convertTime(new java.sql.Time(date.getTime()));
        assertThat(result, instanceOf(LocalTime.class));
    }

    @Test
    public void parseDate() {
        Object result = DateConversions.parseDate("2016-11-28");
        assertThat(result, instanceOf(LocalDate.class));
    }

    @Test
    public void parseDateTime() {
        Object result = DateConversions.parseDate("2016-11-28 15:00");
        assertThat(result, instanceOf(LocalDateTime.class));
    }

    @Test
    public void parseTime() {
        Object result = DateConversions.parseDate("15:00");
        assertThat(result, instanceOf(LocalTime.class));
    }

    private Date getDate(int year, Month month, int day, int hours, int minutes) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hours, minutes);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
