package ru.gortech.iot.qa.autotestui.helpers;



import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @project autotestUI
 */
@Slf4j
public class ConfProperties {
    protected static FileInputStream fileInputStream;
    protected static Properties PROPERTIES;

    static {
        try {
            String stand = System.getProperty("stand") == null ? "test98" : System.getProperty("stand");
            fileInputStream = new FileInputStream(String.format("src/test/resources/config/stands/%s/conf.properties", stand));
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
            log.info("Файл с настройками успешно загружен");
        } catch (FileNotFoundException e) {
            log.error("Файл с настройками отсутствует или не может быть прочитан");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
