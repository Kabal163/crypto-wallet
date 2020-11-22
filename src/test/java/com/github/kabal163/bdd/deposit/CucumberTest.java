package com.github.kabal163.bdd.deposit;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features/deposit",
        plugin = "junit",
        extraGlue = "classpath:com.github.kabal163.bdd.common")
public class CucumberTest {
}
