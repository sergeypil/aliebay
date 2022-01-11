package com.epam.aliebay.dao.connectionpool;

import com.epam.aliebay.exception.PoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private final BlockingQueue<Connection> connections;
    private static volatile ConnectionPool instance;

    private ConnectionPool() {
        int poolSize = Integer.parseInt(ConnectionProvider.getProperty("db.pool.size"));
        connections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection = ConnectionProvider.getConnection();
            connections.offer(connection);
        }
        LOGGER.info("Connection pool were successfully started");
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() {
        Connection connection;
        try {
            connection = connections.take();
            LOGGER.trace("Connection taken successfully");
            return connection;
        } catch (InterruptedException e) {
            LOGGER.warn("Free connection timeout is over.");
            throw new PoolException("Connection timeout is over.");
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            connections.offer(connection);
        }
    }

    public void shutDownConnections() {
        connections.forEach(connection -> {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        LOGGER.error("Cannot close connection.\n", e);
                    }
                });
        LOGGER.info("Connection pool were successfully shut down");
    }
}
