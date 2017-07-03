package com.github.vendigo.acetest.conversion;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NumberConversionsTest {
    @Test
    public void integerToLong() throws Exception {
        assertThat(NumberConversions.integerToLong(5), equalTo(5L));
    }

    @Test
    public void longToLong() throws Exception {
        assertThat(NumberConversions.integerToLong(5L), equalTo(5L));
    }

    @Test
    public void integerAsDoubleToLong() throws Exception {
        assertThat(NumberConversions.integerToLong(5.0), equalTo(5L));
    }
}
