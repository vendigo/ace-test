package com.github.vendigo.acetest;

public class AceTestFixture {
    private ATContext context = new ATContext();

    public AceTestFixture() {
        context.init();
    }

    public void runApplication(String appName, String params) throws Exception {
        context.getAppRunner().runApplication(appName, params);
    }

    public void addProperty(String line) throws Exception {
        context.getPropertySetter().addLine(line);
    }

    public void setProperties() throws Exception {
        context.getPropertySetter().setProperties();
    }
}
