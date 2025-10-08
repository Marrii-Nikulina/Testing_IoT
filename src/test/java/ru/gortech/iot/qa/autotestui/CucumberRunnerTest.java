package ru.gortech.iot.qa.autotestui;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

/**
 * @project autotestUI
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "ru.gortech.iot.qa.autotestui.steps," +
                "ru.gortech.iot.qa.autotestui.pages," +
                "ru.gortech.iot.qa.autotestui.helpers," +
                "ru.gortech.iot.qa.autotestui.utils"
)
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm, pretty"
)
@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "not ignore@test"
)
public class CucumberRunnerTest {
}