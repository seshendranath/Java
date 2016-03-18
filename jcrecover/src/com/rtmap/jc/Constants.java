package com.rtmap.jc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * constants and properties for project
 * please write in the corresponding market
 * Created by doge on 15-5-21.
 */
public class Constants {
    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.class);

    public static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String ORACLE_JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    /**
     * connect information for results
     */
    public static final String AJ_JDBC_URL = getValue("aj.jdbc.url");
    public static final String AJ_JDBC_USERNAME = getValue("aj.jdbc.username");
    public static final String AJ_JDBC_PASSWORD = getValue("aj.jdbc.password");
    public static final int    AJ_STATMENT_QUERY_TIMEOUT = getValueAsInt("aj.statment.query.timeout");
    public static final String AJ_CONNECT_PROPS = getValue("aj.connect.props");

    /**
     * get value from loader
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return Loader.getValue(key);
    }

    public static int getValueAsInt(String key) {
        return Integer.valueOf(getValue(key));
    }

    /**
     * configuration properties loader
     */
    static class Loader {
        private static Properties properties = new Properties();
        private static final String CONFIG_PATH = "/sql-source-config.properties";

        /**
         * load configurations while system start
         */
        static {
            try {
                InputStream inputStream = Constants.class.getResourceAsStream(CONFIG_PATH);
                if (inputStream == null) {
                    LOGGER.error("configuration file path[" + CONFIG_PATH + "] not found, system will exit with code 1.");
                    System.exit(1);
                }
                properties.load(inputStream);
                for (Object key : properties.keySet()) {
                	String val = properties.getProperty(key.toString());
                	String val2 =  (val != null) ? val : "null";
                	LOGGER.info("properties key:" + key.toString() + ", value:" + val2);
                }
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("load configuration properties error: " + e.getMessage(), e);
            }
        }

        /**
         * get value from object properties
         *
         * @param key
         * @return
         */
        public static String getValue(String key) {
            return properties.getProperty(key);
        }
    }
}
