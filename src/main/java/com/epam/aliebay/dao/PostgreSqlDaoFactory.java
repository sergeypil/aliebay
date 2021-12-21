package com.epam.aliebay.dao;

import com.epam.aliebay.dao.Interface.*;
import com.epam.aliebay.dao.impl.*;

public class PostgreSqlDaoFactory {
    private static final PostgreSqlDaoFactory instance = new PostgreSqlDaoFactory();
    private UserDao userDao;
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private ProducerDao producerDao;
    private OrderDao orderDao;
    private OrderStatusDao orderStatusDao;
    private UserStatusDao userStatusDao;
    private LanguageDao languageDao;

    private PostgreSqlDaoFactory() {
        userDao = new UserDaoImpl();
        productDao = new ProductDaoImpl();
        categoryDao = new CategoryDaoImpl();
        producerDao = new ProducerDaoImpl();
        orderDao = new OrderDaoImpl();
        orderStatusDao = new OrderStatusDaoImpl();
        userStatusDao = new UserStatusDaoImpl();
        languageDao = new LanguageDaoImpl();
    }

    public static PostgreSqlDaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public ProducerDao getProducerDao() {
        return producerDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public OrderStatusDao getOrderStatusDao() {
        return orderStatusDao;
    }

    public UserStatusDao getUserStatusDao() {
        return userStatusDao;
    }

    public LanguageDao getLanguageDao() {
        return languageDao;
    }
}

