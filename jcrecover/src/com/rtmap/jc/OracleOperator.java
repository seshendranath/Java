package com.rtmap.jc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleOperator {
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleOperator.class);

    private Connection connection = null;
    private Statement statement = null;

    public ResultSet executeQuery(String sql) throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            connection = OracleConnector.getConnector();
        }
        if (statement == null || statement.isClosed()) {
            statement = connection.createStatement();
        }

        statement.setQueryTimeout(Constants.AJ_STATMENT_QUERY_TIMEOUT);
        return statement.executeQuery(sql);
    }

    public int executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            connection = OracleConnector.getConnector();
        }
        if (statement == null || statement.isClosed()) {
            statement = connection.createStatement();
        }

        statement.setQueryTimeout(Constants.AJ_STATMENT_QUERY_TIMEOUT);
        return statement.executeUpdate(sql);
    }

    public void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("close oracle connection error: " + e.getMessage(), e);
        }
    }
}
