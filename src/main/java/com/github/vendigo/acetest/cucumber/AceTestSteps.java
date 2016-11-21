package com.github.vendigo.acetest.cucumber;

import com.github.vendigo.acetest.SpringConfig;
import com.github.vendigo.acetest.input.PropertySetter;
import com.github.vendigo.acetest.run.AppRunner;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ContextConfiguration(classes = SpringConfig.class)
public class AceTestSteps {

    @Autowired
    PropertySetter propertySetter;
    @Autowired
    AppRunner appRunner;

    @Given("Properties:")
    public void properties(List<String> lines) {
        propertySetter.setProperties(lines);
    }

    @When("Application (.*) run with params: (.*)")
    public void applicationRun(String appName, String params) throws Exception {
        appRunner.runApplication(appName, params);
    }
}
