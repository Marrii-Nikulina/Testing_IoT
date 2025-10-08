package ru.gortech.iot.qa.autotestui.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Тогда;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import ru.gortech.iot.qa.autotestui.pages.ElementRepository;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.By.*;

@Slf4j
public class LeftSidebarStepsDefs extends ElementRepository {

    @Затем("Боковая панель. {string} боковую панель")
    public void clickCollapseExpandLeftSidebar(String sidebarButton) {
        switch (sidebarButton) {
            case "Развернуть":
                leftSidebarPage.chevron.find(xpath("./mat-icon[contains(text(), 'chevron_right')]")).click();
                log.info("Развернута боковая панель");
                break;
            case "Свернуть":
                leftSidebarPage.chevron.find(xpath("./mat-icon[contains(text(), 'chevron_left')]")).click();
                log.info("Свернута боковая панель");
                break;
            default:
                throw new IllegalArgumentException("Неверный параметр: " + sidebarButton);
        }
    }

    @Затем("Боковая панель. Проверить, что отображается список основных разделов согласно списку$")
    public void checkLeftSidebarSections(DataTable sections) throws InterruptedException {
        if (sections.row(0).isEmpty()) {
            throw new IllegalArgumentException("DataTable не может быть пустым");
        }

        equalsArraysSections(sections, leftSidebarPage.visibleListLeve1);
        log.info("Список основных разделов боковой панели соответствует ожидаемым значениям");
    }

    @Тогда("Боковая панель. Раскрыть/Свернуть раздел {string}")
    public void clickCollapseExpandLeftSidebarSection(String section) {
        String stateBefore = getStateLeftSidebarSection(section);

        String pattern = String.format("..//a[contains(@aria-label,'%s')]//mat-icon[text()='expand_more']", section);
        leftSidebarPage.commonElement.find(xpath(pattern)).click();

        String stateAfter = getStateLeftSidebarSection(section);
        Assertions.assertNotEquals(stateBefore, stateAfter);
        log.info("{} раздел {}", stateAfter.equals("false") ? "Свернут" : "Развернут", section);
    }

    @Затем("Боковая панель. Проверить, что отображается список подразделов согласно списку$")
    public void checkLeftSidebarSubSections(DataTable sections) throws InterruptedException {
        if (sections.row(0).isEmpty()) {
            throw new IllegalArgumentException("DataTable не может быть пустым");
        }

        equalsArraysSections(sections, leftSidebarPage.visibleListLeve2);
        log.info("Список подразделов боковой панели соответствует ожидаемым значениям");
    }

    @Тогда("Боковая панель. Нажать на раздел {string}")
    public void clickLeftSidebarSection(String section) {
        leftSidebarPage.commonElement
                .find(xpath("..//a//span[text()='" + section + "']"))
                .shouldBe(Condition.visible)
                .click();
        log.info("Выполнен переход в раздел {}", section);
        //new CommonStepsDefs().checkBreadcrumbs(section);
    }

    private void equalsArraysSections(DataTable dataTable, ElementsCollection elements) throws InterruptedException {
        List<String> expectedList = dataTable.asList(String.class);
        List<String> actualList = elements.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1), Duration.ofSeconds(2)).texts();

        Assertions.assertIterableEquals(
                expectedList,
                actualList,
                String.format("Ожидались разделы: %s, но были найдены: %s",
                        String.join(", ", expectedList),
                        String.join(", ", actualList)));
    }

    private String getStateLeftSidebarSection(String section) {
        String statePattern = String.format("..//a[contains(@aria-label,'%s')]/ancestor::mat-nested-tree-node[1]", section);
        return leftSidebarPage.commonElement.find(xpath(statePattern)).getAttribute("aria-expanded");
    }
}
