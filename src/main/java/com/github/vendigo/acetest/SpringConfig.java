package com.github.vendigo.acetest;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.ConfigParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public Config config() {
        return ConfigParser.readConfig();
    }
}
