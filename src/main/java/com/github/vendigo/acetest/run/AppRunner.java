package com.github.vendigo.acetest.run;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.LauncherConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Component
public class AppRunner {
    public static final String SPACE = " ";
    @Autowired
    Config config;

    public void runApplication(String appName, String params) throws Exception {
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

    private void run(String className, String params) throws Exception {
        Class<?> appClass = Class.forName(className);
        Method mainMethod = appClass.getMethod("main", String[].class);
        String[] args = params.split(SPACE);
        mainMethod.invoke(null, (Object)args);
    }
}
