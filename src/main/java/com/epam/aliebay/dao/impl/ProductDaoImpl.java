package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;

import java.util.*;

import static com.epam.aliebay.constant.OtherConstants.LENGTH_OF_PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE;

public class ProductDaoImpl extends AbstractBaseDao implements ProductDao {
    private static final ResultSetHandler<Product> productResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<Product>> productsResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);
    private static final ResultSetHandler<Integer> integerResultSetHandler =
            ResultSetHandlerFactory.getCountResultSetHandler();

    private static final String SELECT_ALL_PRODUCTS_QUERY = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 ORDER BY p.id DESC";
    private static final String SELECT_PRODUCT_BY_ID_QUERY = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 " +
            "AND p.id=?";
    private static final String INSERT_PRODUCT_QUERY = "INSERT INTO product (name, description, " +
            "price, id_category, id_producer, count, image) VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE_PRODUCT_BY_ID_QUERY = "DELETE FROM product where id=?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE product SET name = ?, " +
            "description = ?, price = ?, " +
            "id_category = ?, id_producer = ?, count = ?, image = ? WHERE id = ?";
    private static final String SELECT_PRODUCTS_BY_CATEGORY_QUERY = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 " +
            "AND p.id_category=? ORDER BY p.id DESC";
    private static final String SELECT_PRODUCTS_BY_PRODUCER_QUERY = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 " +
            "AND p.id_producer=? ORDER BY p.id DESC";
    private static final String SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 ORDER BY p.id DESC " +
            "OFFSET ? LIMIT ?";

    private static final String SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT_ORDER = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 " +
            "ORDER BY p.%s OFFSET ? LIMIT ?";

    private static final String SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT_ORDER_SEARCH = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 " +
            "AND LOWER(p.name) like LOWER('%%%s%%') ORDER BY p.%s OFFSET ? LIMIT ?";

    private static final String SELECT_ALL_PRODUCTS_WITH_ORDER_SEARCH = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 " +
            "AND LOWER(p.name) like LOWER('%%%s%%') ORDER BY p.%s";

    private static final String SELECT_PRODUCTS_BY_CATEGORY_WITH_OFFSET_LIMIT_ORDER_QUERY = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 " +
            "AND p.id_category=? ORDER BY p.%s OFFSET ? LIMIT ?" ;

    private static final String SELECT_PRODUCTS_BY_PRODUCER_WITH_OFFSET_LIMIT_ORDER_QUERY = "SELECT p.id AS id_product, p.name, p.description, " +
            "p.price, p.id_category, p.id_producer, p.count, p.image, " +
            "ci.name AS name_category, c.image, c.id_parent, ci.id_language, l.code, l.name AS name_language, " +
            "pr.name AS name_producer " +
            "FROM product p inner join category c on p.id_category=c.id " +
            "inner join producer pr on p.id_producer=pr.id " +
            "LEFT JOIN category_detail ci ON c.id=ci.id " +
            "INNER JOIN language l ON ci.id_language = l.id " +
            "WHERE ci.id_language=2 " +
            "AND p.id_producer=? ORDER BY p.%s OFFSET ? LIMIT ?" ;

    private static final String SELECT_COUNT_OF_PRODUCTS_QUERY = "SELECT COUNT(*) AS count FROM product";
    private static final String SELECT_COUNT_OF_PRODUCTS_WITH_SEARCH_QUERY = "SELECT COUNT(*) AS count FROM product " +
            "WHERE LOWER(name) like LOWER('%%%s%%')";
    private static final String SELECT_COUNT_OF_PRODUCTS_BY_CATEGORY = "SELECT COUNT(*) AS count FROM product " +
            "WHERE id_category = ?";
    private static final String SELECT_COUNT_OF_PRODUCTS_BY_PRODUCER = "SELECT COUNT(*) AS count FROM product " +
            "WHERE id_producer = ?";

    @Override
    public Optional<Product> getProductById(long id) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_PRODUCT_BY_ID_QUERY, productResultSetHandler, id));
    }

    @Override
    public List<Product> getAllProducts() {
        return JdbcTemplate.select(SELECT_ALL_PRODUCTS_QUERY, productsResultSetHandler);
    }


    public List<Product> getProductsByCategory(int idCategory) {
        return JdbcTemplate.select(SELECT_PRODUCTS_BY_CATEGORY_QUERY, productsResultSetHandler, idCategory);
    }

    @Override
    public List<Product> getProductsByProducer(int idProducer) {
        return JdbcTemplate.select(SELECT_PRODUCTS_BY_PRODUCER_QUERY, productsResultSetHandler, idProducer);
    }

    public List<Product> getProductsWithOffsetLimit(int offset, int limit) {
        return JdbcTemplate.select(SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT, productsResultSetHandler, offset, limit);
    }

    public List<Product> getProductsWithOffsetLimitOrder(int offset, int limit, String orderParam) {
        String sql = String.format(SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT_ORDER, orderParam);
        return JdbcTemplate.select(sql, productsResultSetHandler, offset, limit);
    }

    public List<Product> getProductsWithOffsetLimitOrderSearch(int offset, int limit, String orderParam, String searchParam) {
        String sql = String.format(SELECT_ALL_PRODUCTS_WITH_OFFSET_LIMIT_ORDER_SEARCH, searchParam, orderParam);
        return JdbcTemplate.select(sql, productsResultSetHandler, offset, limit);
    }

    @Override
    public List<Product> getProductsWithOrderAndSearch(String orderParam, String searchParam) {
        String sql = String.format(SELECT_ALL_PRODUCTS_WITH_ORDER_SEARCH, searchParam, orderParam);
        return JdbcTemplate.select(sql, productsResultSetHandler);
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
        String imageWithoutCodePrefix = product.getImage().substring(LENGTH_OF_PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE);
        byte[] imageBytes = Base64.getDecoder().decode(imageWithoutCodePrefix);
        JdbcTemplate.executeUpdate(INSERT_PRODUCT_QUERY, product.getName(), product.getDescription(),
                product.getPrice(), product.getCategory().getId(), product.getProducer().getId(),
                product.getCount(), imageBytes);
    }

    @Override
    public void updateProduct(Product product, long id) {
        String imageWithoutCodePrefix = product.getImage().substring(LENGTH_OF_PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE);
        byte[] imageBytes = Base64.getDecoder().decode(imageWithoutCodePrefix);
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
