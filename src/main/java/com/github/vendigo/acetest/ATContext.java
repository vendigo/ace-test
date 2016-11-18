package com.github.vendigo.acetest;

import com.github.vendigo.acetest.input.FileInputData;
import com.github.vendigo.acetest.input.PropertySetter;
import com.github.vendigo.acetest.run.AppRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ATContext {
    private AnnotationConfigApplicationContext appContext;

    public void init() {
        appContext = new AnnotationConfigApplicationContext("com.github.vendigo.acetest");
    }

    public AppRunner getAppRunner() {
        return appContext.getBean(AppRunner.class);
    }

    public FileInputData getInputData() {
        return appContext.getBean(FileInputData.class);
    }

    public PropertySetter getPropertySetter() {
        return appContext.getBean(PropertySetter.class);
    }
}
