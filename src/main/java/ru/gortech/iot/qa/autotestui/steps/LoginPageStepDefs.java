package ru.gortech.iot.qa.autotestui.steps;

import com.codeborne.selenide.Condition;
import io.cucumber.java.ru.Когда;
import lombok.extern.slf4j.Slf4j;
import ru.gortech.iot.qa.autotestui.helpers.ConfProperties;
import ru.gortech.iot.qa.autotestui.pages.ElementRepository;

import java.time.Duration;

/**
 * @project autotestUI
 */
@Slf4j
public class LoginPageStepDefs extends ElementRepository {

    // Общий метод для авторизации с параметрами
    private void performLogin(String login, String password) {
        try {
            // Проверяем видимость поля логина
            loginPage.login.shouldBe(Condition.visible, Duration.ofSeconds(5));

            // Вводим логин и пароль
            loginPage.login.sendKeys(login);
            loginPage.password.sendKeys(password);

            // Нажимаем кнопку входа
            loginPage.loginButton.click();

            log.info("Авторизация выполнена. Пользователь: {}", login);
        } catch (Exception e) {
            log.error("Ошибка при авторизации пользователя {}: {}", login, e.getMessage());
            throw e; // Перебрасываем исключение для обработки на более высоком уровне
        }
    }

    @Когда("Выполнить авторизацию по-умолчанию")
    public void defaultAuthorization() {
        String defaultLogin = ConfProperties.getProperty("login");
        String defaultPassword = ConfProperties.getProperty("password");

        if (defaultLogin == null || defaultPassword == null) {
            throw new IllegalArgumentException("Не заданы значения по умолчанию в конфигурации");
        }

        performLogin(defaultLogin, defaultPassword);
    }

    @Когда("Выполнить авторизацию, используя логин {string} и пароль {string}")
    public void selectiveAuthorization(String login, String password) {
        if (login == null) {
            throw new IllegalArgumentException("Логин не можеть быть пустым");
        }

        if (password == null) {
            throw new IllegalArgumentException("Пароль для пользователя " + login + " не может быть пустым");
        }

        performLogin(login, password);
    }
}
