package com.github.vendigo.acetest.assertion

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.notNullValue
import java.io.PrintWriter
import java.io.StringWriter

private val LINE_SEPARATOR = "\n"
private val CAUSED_BY = "Caused by: "

fun matchException(throwable: Throwable?, stackTraceContains: String) {
    assertThat(throwable, notNullValue())
    val stringWriter = StringWriter()
    throwable!!.printStackTrace(PrintWriter(stringWriter))
    val stackTrace = stringWriter.toString()
    val exceptionString = stackTrace.split(LINE_SEPARATOR.toRegex())
            .firstOrNull { s -> s.contains(CAUSED_BY) }
    if (exceptionString != null) {
        assertThat(exceptionString, containsString(stackTraceContains))
    } else {
        throw AssertionError("Stacktrace doesn't contain 'Caused by' section")
    }
}

