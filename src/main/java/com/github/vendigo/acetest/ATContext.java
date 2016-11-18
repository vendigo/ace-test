package com.github.vendigo.acetest;

import com.github.vendigo.acetest.files.FileInputData;
import com.github.vendigo.acetest.files.PropertyFileCreator;
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

    public FileInputData getFileInputData() {
        return appContext.getBean(FileInputData.class);
    }

    public PropertyFileCreator getPropertyFileCreator() {
        return appContext.getBean(PropertyFileCreator.class);
    }
}
