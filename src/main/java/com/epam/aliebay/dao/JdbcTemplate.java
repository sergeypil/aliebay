package com.epam.aliebay.dao;

import com.epam.aliebay.dao.connectionpool.ConnectionPool;
import com.epam.aliebay.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    private static final Logger LOGGER = Logger.getLogger(JdbcTemplate.class);

    public static  <T> T select(String sql, ResultSetHandler<T> resultSetHandler, Object... parameters) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement ps =  connection.prepareStatement(sql)) {
            populatePreparedStatement(ps, parameters);
            try (ResultSet rs = ps.executeQuery()) {
                return resultSetHandler.handle(rs);
            }
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
        finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public static void executeUpdate(String sql, Object... parameters)  {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            populatePreparedStatement(ps, parameters);
            int result = ps.executeUpdate();
            LOGGER.info("Updated" + result + "rows");
        }
        catch (SQLException e) {
            throw new DaoException("Cannot update row to database");
        }
        finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public static <T> T executeUpdateInTransactionWithGenKeys(Connection connection, String sql, ResultSetHandler<T> resultSetHandler, Object... parameters) {
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            populatePreparedStatement(ps, parameters);
            int result = ps.executeUpdate();
            LOGGER.info(ps.toString() + "updated" + result + "rows");
            ResultSet rs = ps.getGeneratedKeys();
            return resultSetHandler.handle(rs);
        }
        catch (SQLException e) {
            throw new DaoException("Cannot update row to database", e);
        }
    }

    public static void executeUpdateInTransaction(Connection connection, String sql, Object... parameters) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            populatePreparedStatement(ps, parameters);
            int result = ps.executeUpdate();
            LOGGER.info(ps.toString() + "updated" + result + "rows");
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static void populatePreparedStatement(PreparedStatement ps, Object... parameters){
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                try {
                    ps.setObject(i + 1, parameters[i]);
                } catch (SQLException e) {
                    throw new DaoException("Cannot update row to database", e);
                }
            }
        }
    }
}
