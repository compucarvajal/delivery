package com.test.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFactory {

    public static Properties getProperties(String propertieName) throws IOException {
        Properties appProps;
        switch (propertieName){
            case "application.properties":
                String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                String appPropertiesPath = rootPath + propertieName;
                appProps = new Properties();
                appProps.load(new FileInputStream(appPropertiesPath));
                break;
            default:
                appProps= null;
                break;
        }

        return appProps;
    }
}
