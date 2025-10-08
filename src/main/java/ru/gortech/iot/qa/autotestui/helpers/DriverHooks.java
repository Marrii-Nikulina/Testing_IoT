package ru.gortech.iot.qa.autotestui.helpers;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.gortech.iot.qa.autotestui.factory.WebDriverFactory;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Selenide.*;

/**
 * @project autotestUI
 */
@Slf4j
public class DriverHooks {
    private static String nameScenario;
    private static String nameFeature;

    /**
     * Метод настройки, выполняемый перед каждым сценарием теста.
     * Инициализирует драйвер браузера и настраивает окружение для выполнения тестов.
     *
     * <p>Проверяет системные свойства для определения режима запуска (локальный/удаленный)
     * и типа браузера.</p>
     *
     * @param scenario Объект сценария, предоставляемый фреймворком BDD (например, Cucumber),
     *                 содержит информацию о текущем тестовом сценарии
     * @throws Exception Если произошла ошибка при инициализации драйвера браузера,
     *                   исключение пробрасывается дальше для обработки
     */
    @Before
    @SneakyThrows
    public static void setUp(Scenario scenario) {
        nameFeature = getFeatureName(scenario);
        nameScenario = scenario.getName();
        boolean isRemote =  Boolean.getBoolean(System.getProperty("remote", "false"));
        String browser = System.getProperty("browser", "chrome");
        SelenideLogger.addListener("allure", new AllureSelenide());

        try {
            if (isRemote) {
                log.info("Начата настройка удаленного браузера");
                // TODO: реализовать логику для Selenoid
                configureRemoteDriver(browser); // Реализация Selenoid
            } else {
                log.info("Начата настройка локального браузера");
                WebDriverRunner.setWebDriver(WebDriverFactory.create(browser));
            }
            open(ConfProperties.getProperty("baseUrl"));
            log.info("************************************************************************************************");
            log.info("Запуск Test Suite: {}, Сценарий: {}", nameFeature, nameScenario);
            log.info("************************************************************************************************");
        } catch (Exception e) {
            log.error("Ошибка при инициализации драйвера: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Метод завершения, выполняемый после каждого сценария теста.
     * Выполняет очистку ресурсов и обработку результатов тестирования.
     *
     * <p>Проверяет статус завершения сценария:
     * - При неудачном результате (сценарий упал) выполняется:
     * 1. Логирование ошибки с названием теста
     * 2. Вызов метода afterScenario для генерации скриншота
     * - Во всех случаях:
     * 3. Закрытие веб-драйвера
     * 4. Логирование завершения теста</p>
     *
     * @param scenario Объект сценария, предоставляемый фреймворком BDD (например, Cucumber),
     *                 содержит информацию о результатах выполнения теста
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                log.info("Ошибка в тесте: {}, выполняется скриншот", scenario.getName());
                afterScenario(scenario);
            }
        } finally {
            closeWebDriver();
            log.info("Закрытие браузера");
            log.info("****************************** Конец теста ****************************************************");
        }
    }

    /**
     * Извлекает имя файла фичи из URI текущего сценария.
     *
     * @param scenario Объект сценария, предоставляемый фреймворком BDD,
     *                 содержит метаданные о текущем тестовом сценарии
     * @return Строка с именем файла фичи без расширения
     */
    private static String getFeatureName(Scenario scenario) {
        String url = scenario.getUri().toString();
        String[] parts = url.split("/");
        String featureFile = parts[parts.length - 1];
        return featureFile.replace(".feature", "");
    }

    /**
     * Метод генерации и прикрепления скриншота к отчету при неудачном сценарии.
     *
     * @param scenario Объект сценария, предоставляемый BDD-фреймворком (Cucumber),
     *                 к которому будет прикреплен скриншот
     */
    public void afterScenario(Scenario scenario) {
        byte[] screenshot = screenshot(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "Скриншот ошибки");
    }
    private static void configureRemoteDriver(String browser) throws MalformedURLException {
        // Реализация Selenoid (что-то в этом роде должно быть)
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        WebDriverRunner.setWebDriver(new RemoteWebDriver(
                new URL("http://selenoid:4444/wd/hub"),
                capabilities
        ));
    }
}
