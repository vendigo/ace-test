package com.github.vendigo.acetest.assertion;

import com.google.common.collect.Iterables;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssertionUtils {
    @SuppressWarnings("ConstantConditions")
    public static Set<String> collectColumnNames(List<Map<String, String>> records) {
        return Iterables.<Map<String, String>>getFirst(records, Collections.emptyMap()).keySet();
    }
}
