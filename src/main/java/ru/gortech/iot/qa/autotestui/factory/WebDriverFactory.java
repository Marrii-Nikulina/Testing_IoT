package ru.gortech.iot.qa.autotestui.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @project autotestUI
 */
public class WebDriverFactory {

    public static WebDriver create(String webDriverName) {
        return Factory.create(webDriverName);
    }

    public static WebDriver create(String webDriverName, WebDriver.Options options) {
        return Factory.create(webDriverName.toLowerCase(), options);
    }
}

interface Driver {
    WebDriver create();

    WebDriver create(WebDriver.Options options);
}

class DriverChrome implements Driver {

    public WebDriver create() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        //options.addArguments("--headless=new");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications"); // Отключает все уведомления
        options.addArguments("--disable-infobars");      // Убирает информационные панели
        options.addArguments("--disable-popup-blocking"); // Блокирует всплывающие окна
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        return new ChromeDriver(options);
    }

    @Override
    public WebDriver create(WebDriver.Options options) {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver((ChromeOptions) options);
    }
}

class DriverFox implements Driver {

    public WebDriver create() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    @Override
    public WebDriver create(WebDriver.Options options) {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver((FirefoxOptions) options);
    }
}

class DriverEDGE implements Driver {

    public WebDriver create() {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }

    @Override
    public WebDriver create(WebDriver.Options options) {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver((EdgeOptions) options);
    }
}

@Slf4j
class Factory {

    public static WebDriver create(String browser) {
        switch (browser) {
            case ("chrome"):
                return new DriverChrome().create();
            case ("firefox"):
                return new DriverFox().create();
            case ("edge"):
                return new DriverEDGE().create();
            default:
                log.error("Браузер указан не верно");
                System.exit(0);
                return null;
        }
    }

    public static WebDriver create(String browser, WebDriver.Options options) {
        switch (browser) {
            case ("chrome"):
                return new DriverChrome().create(options);
            case ("firefox"):
                return new DriverFox().create(options);
            case ("edge"):
                return new DriverEDGE().create(options);
            default:
                log.error("Браузер указан не верно");
                System.exit(0);
                return null;
        }
    }
}
