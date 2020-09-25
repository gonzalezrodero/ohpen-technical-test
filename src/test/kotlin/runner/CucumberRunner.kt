package runner

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = ["pretty",
        "junit:build/test-results/cucumber-report.xml",
        "html:build/test-results/cucumber-html-report.html",
        "json:build/test-results/cucumber-report.json"],
    features = ["src/test/resources/features/"],
    glue = ["stepDefinitions/", "runner/"]
)

class CucumberRunner
