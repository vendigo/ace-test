package com.github.vendigo.acetest.conversion;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import static com.github.vendigo.acetest.conversion.DateConversions.convertDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class DateConversionsTest {
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
        Object result = DateConversions.parseDate("2016-11-28");
        assertThat(result, instanceOf(LocalDate.class));
    }

    @Test
    public void parseDateTime() throws Exception {
        Object result = DateConversions.parseDate("2016-11-28 15:00");
        assertThat(result, instanceOf(LocalDateTime.class));
    }

    private Date getDate(int year, Month month, int day, int hours, int minutes) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hours, minutes);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
