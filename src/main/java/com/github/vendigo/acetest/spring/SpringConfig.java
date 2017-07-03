package com.github.vendigo.acetest.spring;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.ConfigParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.github.vendigo.acetest")
public class SpringConfig {
    @Bean
    public Config config() {
        return ConfigParser.readConfig();
    }
}
