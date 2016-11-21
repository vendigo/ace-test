package com.github.vendigo.acetest.input;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropertySetter {
    public static final String EQUAL_SIGN = "=";

    public void setProperties(List<String> lines) {
        lines.forEach(line -> {
            String[] split = line.trim().split(EQUAL_SIGN);
            System.setProperty(split[0], split[1]);
        });
    }
}
