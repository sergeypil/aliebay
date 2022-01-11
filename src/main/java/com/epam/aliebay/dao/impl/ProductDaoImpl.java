package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.util.AppUtils;

import java.util.*;

public class ProductDaoImpl extends AbstractBaseDao implements ProductDao {
    private static final ResultSetHandler<Product> productResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<Product>> productsResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);
    private static final ResultSetHandler<Integer> integerResultSetHandler =
            ResultSetHandlerFactory.getIntResultSetHandler();

    private static final String SELECT_ALL_PRODUCTS_QUERY = "SELECT p.id AS product_id, p.name, p.description, " +
            "p.price, p.category_id, p.producer_id, p.count, p.image AS product_image, " +
            "cd.name AS category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name, " +
            "pr.name AS producer_name FROM product p INNER join category c ON p.category_id = c.id " +
            "INNER join producer pr ON p.producer_id = pr.id LEFT JOIN category_detail cd ON c.id = cd.id " +
            "INNER JOIN language l ON cd.language_id = l.id WHERE l.id in (SELECT l.id FROM language l WHERE l.code = ?) ORDER BY p.id DESC";
    private static final String SELECT_PRODUCT_BY_ID_QUERY = "SELECT p.id AS product_id, p.name, p.description, " +
            "p.price, p.category_id, p.producer_id, p.count, p.image AS product_image, " +
            "cd.name AS category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name, " +
            "pr.name AS producer_name FROM product p INNER join category c ON p.category_id = c.id " +
            "INNER join producer pr ON p.producer_id = pr.id LEFT JOIN category_detail cd ON c.id = cd.id " +
            "INNER JOIN language l ON cd.language_id = l.id WHERE cd.language_id = 2 AND p.id = ?";
    private static final String INSERT_PRODUCT_QUERY = "INSERT INTO product (name, description, " +
            "price, category_id, producer_id, count, image) VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE_PRODUCT_BY_ID_QUERY = "DELETE FROM product where id = ?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE product SET name = ?, description = ?, price = ?, " +
            "category_id = ?, producer_id = ?, count = ?, image = ? WHERE id = ?";
    private static final String SELECT_PRODUCTS_BY_CATEGORY_QUERY = "SELECT p.id AS product_id, p.name, p.description, " +
            "p.price, p.category_id, p.producer_id, p.count, p.image AS product_image, " +
            "cd.name AS category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name, " +
            "pr.name AS producer_name FROM product p INNER join category c ON p.category_id = c.id " +
            "INNER join producer pr ON p.producer_id = pr.id LEFT JOIN category_detail cd ON c.id = cd.id " +
            "INNER JOIN language l ON cd.language_id = l.id WHERE cd.language_id = 2 AND p.category_id = ? ORDER BY p.id DESC";
    private static final String SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT = "SELECT p.id AS product_id, p.name, p.description, " +
            "p.price, p.category_id, p.producer_id, p.count, p.image AS product_image, " +
            "cd.name AS category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name, " +
            "pr.name AS producer_name FROM product p INNER join category c ON p.category_id = c.id " +
            "INNER join producer pr ON p.producer_id = pr.id LEFT JOIN category_detail cd ON c.id = cd.id " +
            "INNER JOIN language l ON cd.language_id = l.id WHERE cd.language_id = 2 ORDER BY p.id DESC " +
            "OFFSET ? LIMIT ?";
    private static final String SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT_ORDER = "SELECT p.id AS product_id, p.name, p.description, " +
            "p.price, p.category_id, p.producer_id, p.count, p.image AS product_image, " +
            "cd.name AS category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name, " +
            "pr.name AS producer_name FROM product p INNER join category c ON p.category_id = c.id " +
            "INNER join producer pr ON p.producer_id = pr.id LEFT JOIN category_detail cd ON c.id = cd.id " +
            "INNER JOIN language l ON cd.language_id = l.id WHERE cd.language_id = 2 ORDER BY p.%s OFFSET ? LIMIT ?";
    private static final String SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT_ORDER_SEARCH = "SELECT p.id AS product_id, p.name, " +
            "p.description, p.price, p.category_id, p.producer_id, p.count, p.image AS product_image, " +
            "cd.name AS category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name, " +
            "pr.name AS producer_name FROM product p INNER join category c ON p.category_id = c.id " +
            "INNER join producer pr ON p.producer_id = pr.id LEFT JOIN category_detail cd ON c.id = cd.id " +
            "INNER JOIN language l ON cd.language_id = l.id WHERE cd.language_id = 2 " +
            "AND LOWER(p.name) like LOWER('%%%s%%') ORDER BY p.%s OFFSET ? LIMIT ?";
    private static final String SELECT_PRODUCTS_BY_CATEGORY_WITH_OFFSET_LIMIT_ORDER_QUERY = "SELECT p.id AS product_id, " +
            "p.name, p.description, p.price, p.category_id, p.producer_id, p.count, p.image AS product_image, " +
            "cd.name AS category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name, " +
            "pr.name AS producer_name FROM product p INNER join category c ON p.category_id = c.id " +
            "INNER join producer pr ON p.producer_id = pr.id LEFT JOIN category_detail cd ON c.id = cd.id " +
            "INNER JOIN language l ON cd.language_id = l.id WHERE cd.language_id = 2 " +
            "AND p.category_id = ? ORDER BY p.%s OFFSET ? LIMIT ?";
    private static final String SELECT_PRODUCTS_BY_PRODUCER_WITH_OFFSET_LIMIT_ORDER_QUERY = "SELECT p.id AS product_id, " +
            "p.name, p.description, p.price, p.category_id, p.producer_id, p.count, p.image AS product_image, cd.name AS " +
            "category_name, c.image AS category_image, c.parent_id, cd.language_id, l.code, l.name AS language_name, " +
            "pr.name AS producer_name FROM product p INNER join category c ON p.category_id = c.id " +
            "INNER join producer pr ON p.producer_id = pr.id LEFT JOIN category_detail cd ON c.id = cd.id " +
            "INNER JOIN language l ON cd.language_id = l.id WHERE cd.language_id = 2 " +
            "AND p.producer_id = ? ORDER BY p.%s OFFSET ? LIMIT ?" ;
    private static final String SELECT_COUNT_OF_PRODUCTS_QUERY = "SELECT COUNT(*) AS count FROM product";
    private static final String SELECT_COUNT_OF_PRODUCTS_WITH_SEARCH_QUERY = "SELECT COUNT(*) AS count FROM product " +
            "WHERE LOWER(name) like LOWER('%%%s%%')";
    private static final String SELECT_COUNT_OF_PRODUCTS_BY_CATEGORY = "SELECT COUNT(*) AS count FROM product " +
            "WHERE category_id = ?";
    private static final String SELECT_COUNT_OF_PRODUCTS_BY_PRODUCER = "SELECT COUNT(*) AS count FROM product " +
            "WHERE producer_id = ?";

    @Override
    public Optional<Product> getProductById(long id) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_PRODUCT_BY_ID_QUERY, productResultSetHandler, id));
    }

    @Override
    public List<Product> getAllProducts(String language) {
        return JdbcTemplate.select(SELECT_ALL_PRODUCTS_QUERY, productsResultSetHandler, language);
    }

    @Override
    public List<Product> getProductsByCategory(int idCategory) {
        return JdbcTemplate.select(SELECT_PRODUCTS_BY_CATEGORY_QUERY, productsResultSetHandler, idCategory);
    }

    @Override
    public List<Product> getProductsWithOffsetLimit(int offset, int limit) {
        return JdbcTemplate.select(SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT, productsResultSetHandler, offset, limit);
    }

    @Override
    public List<Product> getProductsWithOffsetLimitOrder(int offset, int limit, String orderParam) {
        String sql = String.format(SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT_ORDER, orderParam);
        return JdbcTemplate.select(sql, productsResultSetHandler, offset, limit);
    }

    @Override
    public List<Product> getProductsWithOffsetLimitOrderSearch(int offset, int limit, String orderParam, String searchParam) {
        String sql = String.format(SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT_ORDER_SEARCH, searchParam, orderParam);
        return JdbcTemplate.select(sql, productsResultSetHandler, offset, limit);
    }

    @Override
    public List<Product> getProductsByCategoryWithOffsetLimitOrder(int offset, int limit, String orderParam, int idCategory) {
        String sql = String.format(SELECT_PRODUCTS_BY_CATEGORY_WITH_OFFSET_LIMIT_ORDER_QUERY, orderParam);
        return JdbcTemplate.select(sql, productsResultSetHandler,
                idCategory, offset, limit);
    }

    @Override
    public List<Product> getProductsByProducerWithOffsetLimitOrder(int offset, int limit, String orderParam, int idProducer) {
        String sql = String.format(SELECT_PRODUCTS_BY_PRODUCER_WITH_OFFSET_LIMIT_ORDER_QUERY, orderParam);
        return JdbcTemplate.select(sql, productsResultSetHandler,
                idProducer, offset, limit);
    }

    @Override
    public void saveProduct(Product product) {
        byte[] imageBytes = AppUtils.mapImageWithPrefixToBytes(product.getImage());
        JdbcTemplate.executeUpdate(INSERT_PRODUCT_QUERY, product.getName(), product.getDescription(),
                product.getPrice(), product.getCategory().getId(), product.getProducer().getId(),
                product.getCount(), imageBytes);
    }

    @Override
    public void updateProduct(Product product, long id) {
        byte[] imageBytes = AppUtils.mapImageWithPrefixToBytes(product.getImage());
        JdbcTemplate.executeUpdate(UPDATE_PRODUCT_QUERY, product.getName(), product.getDescription(),
                product.getPrice(), product.getCategory().getId(),
                product.getProducer().getId(), product.getCount(), imageBytes, id);
    }

    @Override
    public void deleteProductById(long id) {
        JdbcTemplate.executeUpdate(DELETE_PRODUCT_BY_ID_QUERY, id);
    }

    public int getCountOfProducts() {
        return JdbcTemplate.select(SELECT_COUNT_OF_PRODUCTS_QUERY, integerResultSetHandler);
    }

    @Override
    public int getCountOfProductsWithSearch(String searchParam) {
        String sql = String.format(SELECT_COUNT_OF_PRODUCTS_WITH_SEARCH_QUERY, searchParam);
        return JdbcTemplate.select(sql, integerResultSetHandler);
    }

    @Override
    public int getCountOfProductsByCategory(int idCategory) {
        return JdbcTemplate.select(SELECT_COUNT_OF_PRODUCTS_BY_CATEGORY, integerResultSetHandler, idCategory);
    }

    @Override
    public int getCountOfProductsByProducer(int idProducer) {
        return JdbcTemplate.select(SELECT_COUNT_OF_PRODUCTS_BY_PRODUCER, integerResultSetHandler, idProducer);
    }
}
