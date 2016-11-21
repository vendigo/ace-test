package com.github.vendigo.acetest.assertion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.vendigo.acetest.assertion.DataFormatter.format;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.util.StringUtils.isEmpty;

public class DataMatcher {

    public static void assertData(List<Map<String, Object>> actualResults, List<Map<String, String>> expectedResults) {
        List<Map<String, String>> expected = expectedResults.stream()
                .map(DataMatcher::keyToUpperCase)
                .collect(toList());
        List<Map<String, String>> actual = actualResults.stream()
                .map(DataMatcher::valueToString)
                .collect(toList());
        assertThat(actual, containsInAnyOrder(expected.toArray()));
        assertThat(actual, hasSize(expected.size()));
    }

    private static Map<String, String> keyToUpperCase(Map<String, String> map) {
        Map<String, String> result = new HashMap<>();
        map.entrySet().stream().filter(column -> !isEmpty(column.getValue()))
                .forEach(column -> result.put(column.getKey().toUpperCase(), column.getValue()));
        return result;
    }

    private static Map<String, String> valueToString(Map<String, Object> map) {
        Map<String, String> result = new HashMap<>();
        map.entrySet().stream()
                .filter(column -> !isEmpty(format(column.getValue())))
                .forEach(column -> result.put(column.getKey(), format(column.getValue())));
        return result;
    }
}
