package ru.gortech.iot.qa.autotestui.steps;

import com.codeborne.selenide.ClickOptions;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import ru.gortech.iot.qa.autotestui.pages.ElementRepository;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CommonStepsDefs extends ElementRepository {
    @Когда("Верхняя панель. Убедиться, что заголовок страницы равен {string}")
    public void checkTitlePage(String expectedTitle) {
        commonElements.breadcrumb.first()
                .find(By.xpath("..//h1[contains(@class, 'ng-star-inserted') and not(contains(@style, 'display: none'))]"))
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text(expectedTitle), Duration.ofSeconds(5));
        log.info("Отображается страница с заголовком: {}", expectedTitle);
    }

    @SneakyThrows
    @Тогда("Верхняя панель. Проверить, что в хлебных крошках присутствует {string}")
    public void checkBreadcrumbs(String expectedBreadcrumbs) {
        Thread.sleep(1000);
        assertThat(commonElements.breadcrumb.texts())
                .as(String.format("Проверка наличия '%s' в хлебных крошках", expectedBreadcrumbs))
                .anyMatch(text -> text.contains(expectedBreadcrumbs));
        log.info("В хлебных крошках присутствует ожидаемое значение: {}", expectedBreadcrumbs);
    }

    @Тогда("Верхняя панель. Нажать на значок {string}")
    public void clickElementName(String elementName) {
        switch (elementName) {
            case "аварий":
                commonElements.alarmsMenu.shouldBe(visible).click();
                log.info("Нажат значок аварий");
                break;
            case "полноэкранного режима":
                commonElements.btnFullScreen.shouldBe(visible).click();
                log.info("Нажат значок входа/выхода из полноэкранного режима");
                break;
            case "ещё":
                commonElements.moreVert.shouldBe(visible).click();
                log.info("Открыто меню верхней панели 'Ещё'");
                break;
            default:
                throw new IllegalStateException("Элемент '" + elementName +
                        "' не существует. Доступные варианты: 'аварий', 'полноэкранного режима', 'ещё'");
        }
    }

    @Тогда("Верхняя панель. Закрыть информационное окно аварий")
    public void closeContextAlarmsMenu() {
        commonElements.alarmsMenu.click(ClickOptions.withOffset(-1, -1));
        log.info("Закрыто информационное окно аварий");
    }

    @Затем("Верхняя панель. Проверить, что поле {string} пользователя равно значению {string}")
    public void checkUserinfo(String variable, String expectedValue) {
        String actualValue;

        switch (variable) {
            case "имя":
                actualValue = commonElements.userInfo
                        .find(By.xpath(".//span[@class='tb-user-display-name ng-star-inserted']"))
                        .text().trim();
                break;
            case "роль":
                actualValue = commonElements.userInfo
                        .find(By.xpath(".//span[@class='tb-user-authority ng-star-inserted']"))
                        .text().trim();
                break;
            default:
                throw new IllegalArgumentException("Неверный параметр: " + variable);
        }

        Assertions.assertEquals(expectedValue, actualValue,
                "Значение поля '" + variable + "' не соответствует ожидаемому");
        log.info("Проверено, что поле {} пользователя равно: {}", variable, expectedValue);
    }

    @Тогда("Верхняя панель. Еще. Нажать на пункт меню {string}")
    public void clickMoreVertMenuItem(String menuItem) {
        switch (menuItem) {
            case "Профиль":
                commonElements.moreMenu.find(By.xpath("./button//span[text()='Профиль']")).shouldBe(visible).click();
                break;
            case "Выйти из системы":
                commonElements.moreMenu.find(By.xpath("./button//span[text()='Выйти из системы']")).shouldBe(visible).click();
                break;
            default:
                throw new IllegalArgumentException("Неверный параметр: " + menuItem);
        }
        log.info("Нажат пункт меню: {}", menuItem);
        if (menuItem.equals("Выйти из системы")) {
            loginPage.loginButton.shouldBe(visible);
            log.info("Открыта страница авторизации");
        }
    }
}
