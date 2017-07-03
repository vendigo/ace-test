package com.github.vendigo.acetest.assertion;

import com.github.vendigo.acetest.utils.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.vendigo.acetest.conversion.DataConverter.convertDataForAssertions;
import static com.github.vendigo.acetest.conversion.DataConverter.parseDataForAssertions;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class DbDataMatcher {

    public static void assertData(List<Map<String, Object>> actualResults, List<Map<String, String>> expectedResults) {
        List<Map<String, Object>> expected = parseDataForAssertions(expectedResults);
        List<Map<String, Object>> actual = convertDataForAssertions(actualResults);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
        assertThat(actual, hasSize(expected.size()));
    }

    @SuppressWarnings("ConstantConditions")
    public static Set<String> collectColumnNames(List<Map<String, String>> records) {
        return Utils.<Map<String, String>>getFirst(records, Collections.emptyMap()).keySet();
    }
}
