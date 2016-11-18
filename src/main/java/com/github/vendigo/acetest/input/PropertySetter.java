package com.github.vendigo.acetest.input;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class PropertySetter {
    public static final String EQUAL_SIGN = "=";
    private List<String> lines;

    @PostConstruct
    public void reset() {
        lines = new ArrayList<>();
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public void setProperties() {
        lines.forEach(line -> {
            String[] split = line.trim().split(EQUAL_SIGN);
            System.setProperty(split[0], split[1]);
        });
    }
}
