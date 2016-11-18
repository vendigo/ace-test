package com.github.vendigo.acetest.files;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileInputData {
    private Map<String, List<String>> data;

    @PostConstruct
    public void reset() {
        data = new HashMap<>();
    }

    public void addLine(String fileName, String line) {
        if (!data.containsKey(fileName)) {
            data.put(fileName, new ArrayList<>());
        }
        data.get(fileName).add(line);
    }

    public List<String> getLines(String fileName) {
        return data.get(fileName);
    }
}
