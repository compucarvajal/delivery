package com.test.util;

import com.test.service.ProcessInputFilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class PropertiesSingleton {

    private static final Logger log = LoggerFactory.getLogger(PropertiesSingleton.class.getName());

    private static Properties properties;


    private PropertiesSingleton() {

    }

    public static Properties getApplicationProperties() throws Exception {
        if (null == properties) {
            try {
                return properties = PropertiesFactory.getProperties("application.properties");
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
        } else {
            return properties;
        }
    }

}
