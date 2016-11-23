package com.github.vendigo.acetest.files;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FileMatcher {
    public static void assertFileLines(List<String> actualLines, List<String> expectedLines) {
        List<String> actual = actualLines.stream().map(String::trim).collect(toList());
        List<String> expected = expectedLines.stream().map(String::trim).collect(toList());
        assertThat(actual, equalTo(expected));
    }
}
