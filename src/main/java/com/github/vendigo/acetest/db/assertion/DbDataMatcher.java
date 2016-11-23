package com.github.vendigo.acetest.db.assertion;

import com.google.common.collect.Iterables;

import java.util.*;

import static com.github.vendigo.acetest.db.assertion.DbDataFormatter.adjustNumber;
import static com.github.vendigo.acetest.db.assertion.DbDataFormatter.parseObject;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class DbDataMatcher {

    public static void assertData(List<Map<String, Object>> actualResults, List<Map<String, String>> expectedResults) {
        List<Map<String, Object>> expected = expectedResults.stream()
                .map(DbDataMatcher::parseExpectedResults)
                .collect(toList());
        List<Map<String, Object>> actual = actualResults.stream()
                .map(DbDataMatcher::adjustActualResults)
                .collect(toList());
        assertThat(actual, containsInAnyOrder(expected.toArray()));
        assertThat(actual, hasSize(expected.size()));
    }

    private static Map<String, Object> parseExpectedResults(Map<String, String> map) {
        Map<String, Object> result = new HashMap<>();
        map.entrySet().stream()
                .filter(column -> parseObject(column.getValue()) != null)
                .forEach(column -> result.put(column.getKey().toUpperCase(), parseObject(column.getValue())));
        return result;
    }

    private static Map<String, Object> adjustActualResults(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        map.entrySet().stream()
                .forEach(column -> result.put(column.getKey(), adjustNumber(column.getValue())));
        return result;
    }

    @SuppressWarnings("ConstantConditions")
    public static Set<String> collectColumnNames(List<Map<String, String>> records) {
        return Iterables.<Map<String, String>>getFirst(records, Collections.emptyMap()).keySet();
    }
}
