package com.github.vendigo.acetest.config;

import com.github.vendigo.acetest.utils.Utils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class ConfigParser {
    private static final List<String> POSSIBLE_NAMES = Arrays.asList(
            "ace-test-settings.yaml",
            "ace-test-settings.yml",
            "ace-test-config.yml",
            "ace-test-config.yaml"
    );
    private static ClassLoader classLoader = ConfigParser.class.getClassLoader();

    public static Config readConfig() {
        Yaml parser = new Yaml();
        InputStream configStream = getConfigFile();
        return parser.loadAs(configStream, Config.class);
    }

    private static InputStream getConfigFile() {
        List<InputStream> configFile = POSSIBLE_NAMES.stream()
                .map(name -> classLoader.getResourceAsStream(name))
                .filter(Objects::nonNull)
                .collect(toList());
        return Utils.getFirst(configFile, () -> new RuntimeException("Ace test config file is not found"));
    }
}
