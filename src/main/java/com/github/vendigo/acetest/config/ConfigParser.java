package com.github.vendigo.acetest.config;

import com.github.vendigo.acetest.utils.Utils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class ConfigParser {
    private static ClassLoader classLoader = ConfigParser.class.getClassLoader();

    public static Config readConfig(List<String> configFileNames) {
        Yaml parser = new Yaml();
        InputStream configStream = getConfigFile(configFileNames);
        return parser.loadAs(configStream, Config.class);
    }

    private static InputStream getConfigFile(List<String> configFileNames) {
        List<InputStream> configFile = configFileNames.stream()
                .map(name -> classLoader.getResourceAsStream(name))
                .filter(Objects::nonNull)
                .collect(toList());
        return Utils.getFirst(configFile, () -> new RuntimeException("Ace test config file is not found. " +
                "Possible names: " + configFileNames));
    }
}
