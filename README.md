![ace-test](/ace-test-logo.png?raw=true "ace-test")

[![Build Status](https://travis-ci.org/vendigo/ace-test.svg?branch=master)](https://travis-ci.org/vendigo/ace-test)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.vendigo/ace-test.svg)](http://search.maven.org/#search%7Cga%7C1%7Cace-test)

### Dependency

```xml
<dependency>
    <groupId>com.github.vendigo</groupId>
    <artifactId>ace-test</artifactId>
    <version>1.1.1</version>
</dependency>
```

### Configuration

In test resources create file **ace-test-settings.yaml** with the following structure:
(Other possible names: ace-test-settings.yml, ace-test-config.yaml, ace-test-config.yml)

```yaml
launchers:
  - appName: TestApp1
    className: test.app1.Launcher1
  - appName: TestApp2
    className: test.app2.Launcher2
datasources:
  - dbName: devDb1
    url: jdbc:h2:mem:devdb1
    schemaFile: schema.sql
  - dbName: devDb2
    url: jdbc:h2:mem:devdb2
    liquibaseConfig: /liquibase/_liquibase.xml
    liquibaseContexts: prod
folders:
  - in
  - out
  - failed
```

### Test launcher

```java
@RunWith(Cucumber.class)
@CucumberOptions(glue = {"com.github.vendigo.acetest.cucumber"})
public class CucumberTest {
}
```

### Tips

* AceTestSteps are Spring independent, this means you can write your own steps with @ContextConfiguration and use them together.
* Paths to all test folders are exposed to properties foldername.dir and foldername.folder which can be referenced as '${foldername.dir}'
(e.g. ${in.dir}, ${out.dir}, ${out.folder}, etc).
* Date format in scenarios: yyyy-MM-dd. For dateTime: yyyy-MM-dd hh:mm.
* String can be quoted with "".
* There are placeholders {null} and {empty} (empty String).
* Db assertion ignores order, File assertion - does not.
* Folder assertion ignores order.
* All Strings in data tables are trimmed. Use quotation to work around this (e.g. "I need this spaces   ").
* Exception matching checks on 'contains' in "ExceptionClass: exceptionMessage".
* Don't forget to add empty property file in test resources to satisfy Spring @PropertySource.
* Table and column names are case-insensitive
* Locate .feature file under the same folder structure as TestLauncher or specify *feature* option in @CucumberOptions

### Available steps

See **AceTestSteps.java**

#### Given

* "^Properties:$"
* "^Table (.\*) is empty$"
* "^(.\*) table (.\*) is empty$" - dbName, tableName
* "^Table (.*) with records:$" - tableName, dbRecords
* "^(.\*) table (.\*) with records:$" - dbName, tableName, dbRecords
* "^File (.\*) in folder (.\*) with lines:$"  - fileName, folderName, fileRows

#### When

* "^Application (.\*) runs with params: '(.\*)'$" - appName, params
* "^Application (.\*) runs$" - appName
* "^Application (.\*) runs with params: '(.\*)', it fails with exception: (.\*)$" - appName, params, expectedException
* "^Application (.\*) runs, it fails with exception: (.\*)$" - appName, expectedException

#### Then

* "^Table (.\*) will have records:$" - tableName, dbRows
* "^(.\*) table (.\*) will have records:$" - dbName, tableName, dbRows
* "^Table (.\*) will be empty$" - tableName
* "^(.\*) table (.\*) will be empty$" - dbName, tableName
* "^Folder (.\*) will have files: (.\*)$" - folderName, fileNames (e.g. 'file.txt, file2.txt')
* "^Folder (.*) will be empty$" - folderName
* "^File (.\*) in folder (.\*) will have lines:$" - fileName, folderName, fileLines

### Scenario example

```cucumber
Feature: Sample

  Background:
    Given Properties:
      | db.driverClassName=org.h2.Driver |
      | db.url=jdbc:h2:mem:devdb         |
      | input.dir=${in.dir}              |
      | output.dir=${out.dir}            |

  Scenario: Population of product.csv and country filtering
    Given Properties:
      | area.limit=400000 |
    And Table Country is empty
    And File countries.csv in folder in with lines:
      | name, capital, currency, region, area, independenceDay |
      | Ukraine, Kyiv, UAH, Europe, 603700, 1991-08-24         |
      | Germany, Berlin, EUR, Europe, 357021,                  |
      | France, Paris, EUR, Europe, 643801,                    |
    And Table Product with records:
      | id | name                       | description                              | price  | insertDate | lastUpdateTime   |
      | 1  | Mi Band 1s                 | Fitness band from Xiaomi, 1nd generation | 949.99 | 2016-05-20 | 2016-11-05 20:00 |
      | 2  | Mi Band 2                  | Fitness band from Xiaomi, 2nd generation | 429.00 | 2015-02-22 | 2016-11-06 16:30 |
      | 3  | Xiaomi Huami Amazfit Watch | {empty}                                  | 4499   | 2016-11-02 | 2016-11-02 10:35 |
    When Application TestApp runs
    Then Table Country will have records:
      | name    | capital | currency | region | area   | independenceDay |
      | Ukraine | Kyiv    | UAH      | Europe | 603700 | 1991-08-24      |
      | France  | Paris   | EUR      | Europe | 643801 | {null}          |
    And Folder out will have files: products.csv
    And File products.csv in folder out will have lines:
      | id,name,description,price,insertDate,lastUpdateTime                                        |
      | 1,Mi Band 1s,"Fitness band from Xiaomi, 1nd generation",949.99,2016-05-20,2016-11-05 20:00 |
      | 2,Mi Band 2,"Fitness band from Xiaomi, 2nd generation",429.00,2015-02-22,2016-11-06 16:30  |
      | 3,Xiaomi Huami Amazfit Watch,,4499.00,2016-11-02,2016-11-02 10:35                          |
```

### Other examples

See tests in this repo and more examples here: [ace-test-examples](https://github.com/vendigo/ace-test-examples)