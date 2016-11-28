package com.github.vendigo.acetest.db.assertion;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import static com.github.vendigo.acetest.db.assertion.DateUtils.convertDate;
import static com.github.vendigo.acetest.db.assertion.DateUtils.formatDateTime;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class DateUtilsClass {
    @Test
    public void formatDbDate() throws Exception {
        Date date = getDate(2016, Month.NOVEMBER, 24, 0, 0);
        String formattedDate = formatDateTime(date);
        assertThat(formattedDate, equalTo("2016-11-24"));
    }

    @Test
    public void formatDbDateTime() throws Exception {
        Date date = getDate(2016, Month.NOVEMBER, 24, 8, 25);
        String formattedDate = formatDateTime(date);
        assertThat(formattedDate, equalTo("2016-11-24 08:25:00.00"));
    }

    @Test
    public void convertToLocalDateTime() throws Exception {
        Date date = getDate(2016, Month.NOVEMBER, 24, 8, 25);
        Object result = convertDate(date);
        assertThat(result, instanceOf(LocalDateTime.class));
    }

    @Test
    public void convertToLocalDate() throws Exception {
        Date date = getDate(2016, Month.NOVEMBER, 24, 0, 0);
        Object result = convertDate(date);
        assertThat(result, instanceOf(LocalDate.class));
    }

    @Test
    public void parseDate() throws Exception {
        Object result = DateUtils.parseDate("2016-11-28");
        assertThat(result, instanceOf(LocalDate.class));
    }

    @Test
    public void parseDateTime() throws Exception {
        Object result = DateUtils.parseDate("2016-11-28 15:00");
        assertThat(result, instanceOf(LocalDateTime.class));
    }

    private Date getDate(int year, Month month, int day, int hours, int minutes) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hours, minutes);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
