package com.github.vendigo.acetest.run;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.LauncherConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Component
@Slf4j
public class AppRunner {
    private static final String SPACE = " ";
    private final Config config;

    @Autowired
    public AppRunner(Config config) {
        this.config = config;
    }

    public Throwable runApplication(String appName, String params, boolean catchExceptions) throws Throwable {
        Optional<LauncherConfig> launcherConfig = config.getLaunchers()
                .stream()
                .filter(l -> l.getAppName().equals(appName))
                .findAny();
        if (launcherConfig.isPresent()) {
            String className = launcherConfig.get().getClassName();
            return run(className, params, catchExceptions);
        } else {
            throw new IllegalArgumentException("Unknown appName");
        }
    }

    private Throwable run(String className, String params, boolean catchExceptions) throws Throwable {
        try {
            log.info("Running {} with params: {}", className, params);
            Class<?> appClass = Class.forName(className);
            Method mainMethod = appClass.getMethod("main", String[].class);
            String[] args = params.split(SPACE);
            mainMethod.invoke(null, (Object) args);
        } catch (Throwable t) {
            if (catchExceptions) {
                return t;
            } else {
                throw t;
            }
        }
        return null;
    }
}
