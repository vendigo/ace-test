package com.github.vendigo.acetest.run;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.LauncherConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Component
@Slf4j
public class AppRunner {
    public static final String SPACE = " ";
    @Autowired
    Config config;

    public void runApplication(String appName, String params) {
        Optional<LauncherConfig> launcherConfig = config.getLaunchers()
                .stream()
                .filter(l -> l.getAppName().equals(appName))
                .findAny();
        if (launcherConfig.isPresent()) {
            String className = launcherConfig.get().getClassName();
            run(className, params);
        } else {
            throw new IllegalArgumentException("Unknown appName");
        }
    }

    @SneakyThrows
    private void run(String className, String params) {
        log.info("Running {} with params: {}", className, params);
        Class<?> appClass = Class.forName(className);
        Method mainMethod = appClass.getMethod("main", String[].class);
        String[] args = params.split(SPACE);
        mainMethod.invoke(null, (Object) args);
    }
}
