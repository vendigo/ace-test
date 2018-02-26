package com.github.vendigo.acetest.cucumber;

import com.github.vendigo.acetest.assertion.ExceptionMatcher;
import com.github.vendigo.acetest.db.dao.CrudService;
import com.github.vendigo.acetest.files.FileManager;
import com.github.vendigo.acetest.properties.PropertySetter;
import com.github.vendigo.acetest.run.AppRunner;
import com.github.vendigo.acetest.spring.ContextHolder;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

import static com.github.vendigo.acetest.assertion.DbDataMatcher.assertData;
import static com.github.vendigo.acetest.assertion.DbDataMatcher.collectColumnNames;
import static com.github.vendigo.acetest.conversion.DataConverter.parseDataForInsert;
import static com.github.vendigo.acetest.files.FileMatcher.assertFileLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

public class AceTestSteps {
    private PropertySetter propertySetter;
    private CrudService crudService;
    private AppRunner appRunner;
    private FileManager fileManager;

    public AceTestSteps() {
        ApplicationContext appContext = ContextHolder.getAppContext();
        propertySetter = appContext.getBean(PropertySetter.class);
        crudService = appContext.getBean(CrudService.class);
        appRunner = appContext.getBean(AppRunner.class);
        fileManager = appContext.getBean(FileManager.class);
    }

    @Before
    public void setUp() {
        fileManager.createFolders();
    }

    @After
    public void tearDown() {
        fileManager.deleteTempFolder();
    }

    @Given("^Properties:$")
    public void properties(List<String> lines) {
        propertySetter.parseAndSetProperties(lines);
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
        crudService.deleteAll(tableName);
        crudService.insert(tableName, parseDataForInsert(records));
    }

    @Given("^(.*) table (.*) with records:$")
    public void tableWithRecords(String dbName, String tableName, List<Map<String, String>> records) {
        crudService.deleteAll(dbName, tableName);
        crudService.insert(dbName, tableName, parseDataForInsert(records));
    }

    @Given("^File (.*) in folder (.*) with lines:$")
    public void textFile(String fileName, String folderName, List<String> lines) throws Exception {
        fileManager.createTestFile(folderName, fileName, lines);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @When("^Application (.*) runs with params: '(.*)'$")
    public void applicationRun(String appName, String params) throws Throwable {
        appRunner.runApplication(appName, params, false);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @When("^Application (.*) runs$")
    public void applicationRun(String appName) throws Throwable {
        appRunner.runApplication(appName, "", false);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @When("^Application (.*) runs with params: '(.*)', it fails with exception: (.*)$")
    public void applicationFails(String appName, String params, String stackTraceContains) throws Throwable {
        Throwable throwable = appRunner.runApplication(appName, params, true);
        ExceptionMatcher.matchException(throwable, stackTraceContains);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @When("^Application (.*) runs, it fails with exception: (.*)$")
    public void applicationRunFails(String appName, String stackTraceContains) throws Throwable {
        Throwable throwable = appRunner.runApplication(appName, "", true);
        ExceptionMatcher.matchException(throwable, stackTraceContains);
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

    @Then("^Folder (.*) will have files: (.*)$")
    public void folderHasFiles(String folderName, List<String> expectedFileNames) {
        List<String> actualFileNames = fileManager.fileList(folderName);
        assertThat(actualFileNames, containsInAnyOrder(expectedFileNames.toArray()));
        assertThat(actualFileNames, hasSize(expectedFileNames.size()));
    }

    @Then("^Folder (.*) will be empty$")
    public void folderIsEmpty(String folderName) {
        List<String> actualFileNames = fileManager.fileList(folderName);
        assertThat(actualFileNames, hasSize(0));
    }

    @Then("^File (.*) in folder (.*) will have lines:$")
    public void fileHasLines(String fileName, String folderName, List<String> expectedLines) throws Exception {
        List<String> actualLines = fileManager.readFile(folderName, fileName);
        assertFileLines(actualLines, expectedLines);
    }
}
