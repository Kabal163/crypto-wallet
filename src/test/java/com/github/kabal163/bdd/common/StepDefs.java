package com.github.kabal163.bdd.common;

import com.github.kabal163.bdd.CucumberTestContext;
import io.cucumber.java8.En;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Common useful steps which can be used in different scenarios
 */
public class StepDefs implements En {

    private final CucumberTestContext CONTEXT = CucumberTestContext.CONTEXT;

    public StepDefs() {
        Then("receives response with status {int}", (Integer expected) -> {

            Integer actual = CONTEXT.getResponse().statusCode();
            assertThat(actual).isEqualTo(expected);
        });

        And("response contains field {word} with value {nullable_string}", (String property, String expected) -> {
            String actual = CONTEXT.getResponse().getBody().path("%s", property);
            Assertions.assertThat(actual).isEqualTo(expected);
        });

        And("response contains field {word} with value {nullable_int}", (String property, Integer expected) -> {
            Number actual = CONTEXT.getResponse().getBody().path("%s", property);
            Assertions.assertThat(actual).isEqualTo(expected);
        });

        And("response contains field {word} with value {nullable_boolean}", (String property, Boolean expected) -> {
            Boolean actual = CONTEXT.getResponse().getBody().path("%s", property);
            Assertions.assertThat(actual).isEqualTo(expected);
        });

        And("response contains field {word} with value {nullable_bigDecimal}", (String property, BigDecimal expected) -> {
            BigDecimal actual = JsonPath
                    .with(CONTEXT.getResponse().asString())
                    .using(new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL)).get(property);
            Assertions.assertThat(actual).isEqualTo(expected);
        });

        And("response contains field {word} with null value", (String property) -> {
            Object actual = CONTEXT.getResponse().getBody().path("%s", property);
            Assertions.assertThat(actual).isNull();
        });

        And("response contains field {word} with not null value", (String property) -> {
            Object actual = CONTEXT.getResponse().getBody().path("%s", property);
            Assertions.assertThat(actual).isNotNull();
        });

        ParameterType(
                "nullable_string",
                ":string=.*",
                (String value) -> ":string=".equals(value)
                        ? null
                        : value.replaceFirst(":string=", ""));
        ParameterType(
                "nullable_int",
                ":int=\\d*",
                (String value) -> ":int=".equals(value)
                        ? null
                        : Integer.parseInt(value.replaceFirst(":int=", "")));
        ParameterType(
                "nullable_bigDecimal",
                ":bigDec=.*",
                (String value) -> ":bigDec=".equals(value)
                        ? null
                        : new BigDecimal(value.replaceFirst(":bigDec=", "")));
        ParameterType(
                "nullable_boolean",
                ":bool=.*",
                (String value) -> ":bool=".equals(value)
                        ? null
                        : Boolean.parseBoolean(value.replaceFirst(":bool=", "")));
    }
}
