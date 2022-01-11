package com.epam.aliebay.dao;

import com.epam.aliebay.dao.Interface.*;
import com.epam.aliebay.dao.connectionpool.ConnectionProvider;
import com.epam.aliebay.exception.DaoException;
import org.apache.log4j.Logger;

public abstract class DaoFactory {
    private static final Logger LOGGER = Logger.getLogger(DaoFactory.class);
    private static final String POSTGRES = "postgres";
    private static final String DATABASE_TYPE = ConnectionProvider.getProperty("db.type");

    public static DaoFactory getDaoFactory() {
        switch (DATABASE_TYPE) {
            case POSTGRES:
                return PostgreSqlDaoFactory.getInstance();
            default:
                LOGGER.fatal("DAO Factory with for type " + DATABASE_TYPE + " not found.");
                throw new DaoException();
        }
    }

    public abstract UserDao getUserDao();

    public abstract ProductDao getProductDao();

    public abstract CategoryDao getCategoryDao();

    public abstract ProducerDao getProducerDao();

    public abstract OrderDao getOrderDao();

    public abstract OrderStatusDao getOrderStatusDao();

    public abstract UserStatusDao getUserStatusDao();

    public abstract LanguageDao getLanguageDao();

}
