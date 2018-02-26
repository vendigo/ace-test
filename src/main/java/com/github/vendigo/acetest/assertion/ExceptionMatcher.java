package com.github.vendigo.acetest.assertion;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class ExceptionMatcher {
    private static final String LINE_SEPARATOR = "\n";
    private static final String CAUSED_BY = "Caused by: ";

    public static void matchException(Throwable throwable, String stackTraceContains) {
        assertThat(throwable, notNullValue());
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        String stackTrace = stringWriter.toString();
        Optional<String> exceptionString = Stream.of(stackTrace.split(LINE_SEPARATOR))
                .filter(s -> s.contains(CAUSED_BY))
                .findFirst();
        if (exceptionString.isPresent()) {
            assertThat(exceptionString.get(), containsString(stackTraceContains));
        } else {
            throw new AssertionError("Stacktrace doesn't contain 'Caused by section'");
        }
    }
}
