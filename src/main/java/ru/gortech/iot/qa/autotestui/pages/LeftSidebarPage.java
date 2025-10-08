package ru.gortech.iot.qa.autotestui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;


public class LeftSidebarPage {
    public SelenideElement chevron = $(xpath("//mat-sidenav//mat-toolbar/button"));
    public ElementsCollection visibleListLeve1 = $$(xpath("//tb-side-menu/mat-tree//mat-nested-tree-node[@aria-level='1']//div/span[not(ancestor::*[5]/ul[contains(@style, 'height: 0px')])]"));
    public  ElementsCollection visibleListLeve2 = $$(xpath("//tb-side-menu/mat-tree//mat-nested-tree-node[@aria-level='2']//div/span[not(ancestor::*[5]/ul[contains(@style, 'height: 0px')])]"));
    public SelenideElement commonElement = $(xpath("//tb-side-menu/mat-tree//mat-nested-tree-node"));
}
