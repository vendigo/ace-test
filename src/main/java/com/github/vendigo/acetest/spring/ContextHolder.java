package com.github.vendigo.acetest.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextHolder {
    private static ApplicationContext appContext = null;

    public static synchronized ApplicationContext getAppContext() {
        if (appContext == null) {
            appContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        }
        return appContext;
    }
}
