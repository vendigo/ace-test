package com.github.vendigo.acetest.db.assertion;

import com.google.common.collect.Iterables;

import java.util.*;

import static com.github.vendigo.acetest.db.assertion.DbDataFormatter.format;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.util.StringUtils.isEmpty;

public class DbDataMatcher {

    public static void assertData(List<Map<String, Object>> actualResults, List<Map<String, String>> expectedResults) {
        List<Map<String, String>> expected = expectedResults.stream()
                .map(DbDataMatcher::keyToUpperCase)
                .collect(toList());
        List<Map<String, String>> actual = actualResults.stream()
                .map(DbDataMatcher::valueToString)
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

    @SuppressWarnings("ConstantConditions")
    public static Set<String> collectColumnNames(List<Map<String, String>> records) {
        return Iterables.<Map<String, String>>getFirst(records, Collections.emptyMap()).keySet();
    }
}
