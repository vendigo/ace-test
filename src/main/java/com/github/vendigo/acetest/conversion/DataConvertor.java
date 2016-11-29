package com.github.vendigo.acetest.conversion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DataConvertor {
    public static List<Map<String, Object>> parseDataForAssertions(List<Map<String, String>> inputData) {
        return inputData.stream()
                .map(DataConvertor.parseRow(String::toUpperCase, StringParser::parseString))
                .collect(toList());
    }

    public static List<Map<String, Object>> convertDataForAssertions(List<Map<String, Object>> inputData) {
        return inputData.stream()
                .map(DataConvertor.parseRow(String::toUpperCase, ObjectConverter::convertForAssertion))
                .collect(Collectors.toList());
    }

    public static List<Map<String, String>> formatDataForInsert(List<Map<String, Object>> inputData) {
        return inputData.stream()
                .map(DataConvertor.parseRow(String::toUpperCase, ToStringConverter::convertForInserting))
                .collect(Collectors.toList());
    }

    public static <T, U> Function<Map<String, T>, Map<String, U>> parseRow(Function<String, String> keyConvert,
                                                                             Function<T, U> valueConvert) {
        return (inputRow) -> {
            Map<String, U> resultMap = new HashMap<>();
            inputRow.forEach((key, value) -> {
                String convertedKey = keyConvert.apply(key);
                U convertedValue = valueConvert.apply(value);
                if (convertedKey != null && convertedValue != null) {
                    resultMap.put(convertedKey, convertedValue);
                }
            });
            return resultMap;
        };
    }
}
