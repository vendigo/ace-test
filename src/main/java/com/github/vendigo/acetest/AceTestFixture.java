package com.github.vendigo.acetest;

public class AceTestFixture {
    private ATContext context = new ATContext();

    public AceTestFixture() {
        context.init();
    }

    public void runApplication(String appName, String params) throws Exception {
        context.getAppRunner().runApplication(appName, params);
    }

    public void fileLine(String fileName, String line) throws Exception {
        context.getFileInputData().addLine(fileName, line.trim());
    }

    public void createPropertyFile(String fileName) throws Exception {
        context.getPropertyFileCreator().createPropertyFile(fileName);
    }
}
