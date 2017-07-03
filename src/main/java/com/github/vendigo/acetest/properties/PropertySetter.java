package com.github.vendigo.acetest.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PropertySetter {
    private static final String EQUAL_SIGN = "=";
    private Map<String, String> placeholders = new HashMap<>();

    public void parseAndSetProperties(List<String> lines) {
        lines.forEach(line -> {
            String[] split = line.trim().split(EQUAL_SIGN);
            String key = split[0];
            String value = split[1];
            if (key != null && value != null) {
                value = resolvePlaceholders(value);
                log.info("Setting property: {}={}", key, value);
                System.setProperty(key, value);
            }
        });
    }

    private String resolvePlaceholders(String propertyValue) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = entry.getKey();
            String placeholderValue = entry.getValue();
            if (propertyValue.contains(wrap(placeholder))) {
                propertyValue = propertyValue.replace(wrap(placeholder), placeholderValue);
            }
        }
        return propertyValue;
    }

    private String wrap(String str) {
        return "${" + str + "}";
    }

    public void addPlaceholder(String placeholder, String value) {
        placeholders.put(placeholder, value);
    }
}
