package com.epam.aliebay.dao.connectionpool;

import com.epam.aliebay.exception.PoolException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionProvider {
    private static final Logger LOGGER = Logger.getLogger(ConnectionProvider.class);

    static {
        try {
            String driver = getProperty("db.driver");
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Cannot create connection pool: failed loading driver\n", e);
            throw new PoolException("Cannot create connection pool\n", e);
        }
    }

    static Connection getConnection() {
        String url = getProperty("db.url");
        String user = getProperty("db.user");
        String pass = getProperty("db.password");
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            LOGGER.fatal("Cannot create connection pool: SQL Exception\n", e);
            throw new PoolException("Cannot create connection pool\n", e);
        }
    }

    public static String getProperty(String key) {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionProvider.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            LOGGER.fatal("Cannot create connection pool: failed reading database properties\n", e);
            throw new PoolException("Cannot create connection pool\n", e);
        }
        return properties.getProperty(key);
    }
}
