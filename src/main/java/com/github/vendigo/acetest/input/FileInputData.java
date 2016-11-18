package com.github.vendigo.acetest.input;

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

    public void addLine(String dataSetName, String line) {
        if (!data.containsKey(dataSetName)) {
            data.put(dataSetName, new ArrayList<>());
        }
        data.get(dataSetName).add(line);
    }

    public List<String> getLines(String dataSetName) {
        return data.get(dataSetName);
    }
}
