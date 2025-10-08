package ru.gortech.iot.qa.autotestui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

/**
 * @project autotestUI
 */
public class CommonElements {
    public ElementsCollection breadcrumb = $$(xpath("//tb-breadcrumb/div"));
    public SelenideElement alarmsMenu = $(xpath("//tb-alarms-menu//button/span"));
    public SelenideElement btnFullScreen = $(xpath("//button/span/mat-icon[contains(text(),'fullscreen')]"));
    public SelenideElement moreVert = $(xpath("//tb-user-menu//button//mat-icon[text()='more_vert']"));
    public SelenideElement moreMenu = $(xpath("//div[@class='cdk-overlay-container']//div[contains(@class,'tb-user-menu-items')]"));
    public SelenideElement userInfo = $(xpath("//tb-user-menu//div[contains(@class,'user-info')]"));

}
