package com.github.vendigo.acetest.spring;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.ConfigParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.github.vendigo.acetest")
public class SpringConfig {
    private static final List<String> POSSIBLE_CONFIG_NAMES = Arrays.asList(
            "ace-test-settings.yaml",
            "ace-test-settings.yml",
            "ace-test-config.yml",
            "ace-test-config.yaml"
    );

    @Bean
    public Config config() {
        return ConfigParser.readConfig(POSSIBLE_CONFIG_NAMES);
    }
}
