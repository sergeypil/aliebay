package com.epam.aliebay.dao;

import com.epam.aliebay.dao.Interface.*;
import com.epam.aliebay.dao.impl.*;

public class PostgreSqlDaoFactory extends DaoFactory {
    private static final PostgreSqlDaoFactory instance = new PostgreSqlDaoFactory();

    private PostgreSqlDaoFactory() {
    }

    public static PostgreSqlDaoFactory getInstance() {
        return instance;
    }

    @Override
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public ProductDao getProductDao() {
        return new ProductDaoImpl();
    }

    @Override
    public CategoryDao getCategoryDao() {
        return new CategoryDaoImpl();
    }

    @Override
    public ProducerDao getProducerDao() {
        return new ProducerDaoImpl();
    }

    @Override
    public OrderDao getOrderDao() {
        return new OrderDaoImpl();
    }

    @Override
    public OrderStatusDao getOrderStatusDao() {
        return new OrderStatusDaoImpl();
    }

    @Override
    public UserStatusDao getUserStatusDao() {
        return new UserStatusDaoImpl();
    }

    @Override
    public LanguageDao getLanguageDao() {
        return new LanguageDaoImpl();
    }
}

