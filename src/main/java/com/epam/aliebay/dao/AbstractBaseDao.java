package com.epam.aliebay.dao;

import com.epam.aliebay.dao.connectionpool.ConnectionPool;
import com.epam.aliebay.exception.DaoException;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public abstract class AbstractBaseDao {
    private static final Logger logger = Logger.getLogger(AbstractBaseDao.class);

    protected void doInTransaction(Consumer<Connection> consumer) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            consumer.accept(connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Cannot rollback", ex);
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    ConnectionPool.getInstance().releaseConnection(connection);
                } catch (SQLException e) {
                    logger.error("Cannot return connection to connection pool", e);
                }
            }
        }
    }
}
