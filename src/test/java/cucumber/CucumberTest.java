package cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"cucumber.api.spring", "com.github.vendigo.acetest.cucumber"},
        plugin = {"pretty", "html:target/cucumber"})
public class CucumberTest {
}
