package com.github.vendigo.acetest.db.assertion;

import com.google.common.collect.Iterables;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.vendigo.acetest.db.assertion.DbDataFormatter.adjustRecordsForAssert;
import static com.github.vendigo.acetest.db.assertion.DbDataFormatter.parseRecordsForInsert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class DbDataMatcher {

    public static void assertData(List<Map<String, Object>> actualResults, List<Map<String, String>> expectedResults) {
        List<Map<String, Object>> expected = parseRecordsForInsert(expectedResults);
        List<Map<String, Object>> actual = adjustRecordsForAssert(actualResults);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
        assertThat(actual, hasSize(expected.size()));
    }

    @SuppressWarnings("ConstantConditions")
    public static Set<String> collectColumnNames(List<Map<String, String>> records) {
        return Iterables.<Map<String, String>>getFirst(records, Collections.emptyMap()).keySet();
    }
}
