package com.rtmap.jc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class OracleConnector {
    public static Connection getConnector() throws ClassNotFoundException, SQLException {
        return getConnector(Constants.AJ_JDBC_URL, Constants.AJ_JDBC_USERNAME, Constants.AJ_JDBC_PASSWORD);
    }

    public static Connection getConnector(String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.ORACLE_JDBC_DRIVER);
        Properties props = new Properties();
        
        props.put("user", username);
        props.put("password", password);
        props.put("connectionProperties", Constants.AJ_CONNECT_PROPS);
        return DriverManager.getConnection(url, props);
    }
    
    
}
