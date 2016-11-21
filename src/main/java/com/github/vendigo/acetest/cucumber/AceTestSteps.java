package com.github.vendigo.acetest.cucumber;

import com.github.vendigo.acetest.SpringConfig;
import com.github.vendigo.acetest.db.dao.CrudService;
import com.github.vendigo.acetest.input.PropertySetter;
import com.github.vendigo.acetest.run.AppRunner;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

import static com.github.vendigo.acetest.assertion.AssertionUtils.collectColumnNames;
import static com.github.vendigo.acetest.assertion.DataMatcher.assertData;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@ContextConfiguration(classes = SpringConfig.class)
public class AceTestSteps {

    @Autowired
    PropertySetter propertySetter;
    @Autowired
    CrudService crudService;
    @Autowired
    AppRunner appRunner;

    @Given("^Properties:$")
    public void properties(List<String> lines) {
        propertySetter.setProperties(lines);
    }

    @Given("^Table (.*) is empty$")
    public void tableIsEmptyOneDb(String tableName) {
        crudService.deleteAll(tableName);
    }

    @Given("^(.*) table (.*) is empty$")
    public void tableIsEmpty(String dbName, String tableName) {
        crudService.deleteAll(dbName, tableName);
    }

    @Given("^Table (.*) with records:$")
    public void tableWithRecordsOneDb(String tableName, List<Map<String, String>> records) {
        crudService.insert(tableName, records);
    }

    @Given("^(.*) table (.*) with records:$")
    public void tableWithRecords(String dbName, String tableName, List<Map<String, String>> records) {
        crudService.insert(dbName, tableName, records);
    }

    @When("^Application (.*) run with params: (.*)$")
    public void applicationRun(String appName, String params) throws Exception {
        appRunner.runApplication(appName, params);
    }

    @Then("^Table (.*) will have records:$")
    public void tableWillHaveRecordsOneDb(String tableName, List<Map<String, String>> expectedResults) {
        List<Map<String, Object>> actualResults = crudService.selectAll(tableName,
                collectColumnNames(expectedResults));
        assertData(actualResults, expectedResults);
    }

    @Then("^(.*) table (.*) will have records:$")
    public void tableWillHaveRecords(String dbName, String tableName, List<Map<String, String>> expectedResults) {
        List<Map<String, Object>> actualResults = crudService.selectAll(dbName, tableName,
                collectColumnNames(expectedResults));
        assertData(actualResults, expectedResults);
    }

    @Then("^Table (.*) will be empty$")
    public void tableWillBeEmptyOneDb(String tableName) {
        assertThat(crudService.count(tableName), equalTo(0));
    }

    @Then("^(.*) table (.*) will be empty$")
    public void tableWillBeEmpty(String dbName, String tableName) {
        assertThat(crudService.count(dbName, tableName), equalTo(0));
    }
}
