package com.test.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFactory {

    public static Properties getProperties(String propertieName) throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appPropertiesPath = rootPath + propertieName;
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appPropertiesPath));
        return appProps;
    }


}
