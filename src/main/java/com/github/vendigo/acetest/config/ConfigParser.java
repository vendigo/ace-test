package com.github.vendigo.acetest.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ConfigParser {
    private static final String SETTiNGS_FILE_NAME = "ace-test-settings.yaml";
    private static ClassLoader classLoader = ConfigParser.class.getClassLoader();

    public static Config readConfig() {
        Yaml parser = new Yaml();
        InputStream configStream = classLoader.getResourceAsStream(SETTiNGS_FILE_NAME);
        return parser.loadAs(configStream, Config.class);
    }
}
