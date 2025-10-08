package ru.gortech.iot.qa.autotestui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.*;

/**
 * @project autotestUI
 */
public class LoginPage {
    public SelenideElement login = $(id("username-input"));
    public SelenideElement password = $(id("password-input"));
    public SelenideElement loginButton = $(id("login-button"));
}
