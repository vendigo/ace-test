package com.github.vendigo.acetest.conversion;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class ObjectConverterTest {
    @Test
    public void convertInteger() throws Exception {
        Object result = ObjectConverter.convertForAssertion(100);
        assertThat(result, instanceOf(Long.class));
        assertThat(result, equalTo(100L));
    }

    @Test
    public void convertDouble() throws Exception {
        Object result = ObjectConverter.convertForAssertion(100.5);
        assertThat(result, instanceOf(Double.class));
        assertThat(result, equalTo(100.5));
    }

    @Test
    public void convertDoubleToLong() throws Exception {
        Object result = ObjectConverter.convertForAssertion(100.0);
        assertThat(result, instanceOf(Long.class));
        assertThat(result, equalTo(100L));
    }

    @Test
    public void convertString() throws Exception {
        Object result = ObjectConverter.convertForAssertion("Hello");
        assertThat(result, instanceOf(String.class));
        assertThat(result, equalTo("Hello"));
    }
}
