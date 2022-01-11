package com.epam.aliebay.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.aliebay.entity.*;
import com.epam.aliebay.util.AppUtils;

public final class ResultSetHandlerFactory {
    private final static int INDEX_OF_FIRST_COLUMN = 1;

    public static final ResultSetHandler<Product> PRODUCT_RESULT_SET_HANDLER = new ResultSetHandler<Product>() {
        @Override
        public Product handle(ResultSet rs) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("product_id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getBigDecimal("price"));
            Category category = CATEGORY_RESULT_SET_HANDLER.handle(rs);
            product.setCategory(category);
            Producer producer = PRODUCER_RESULT_SET_HANDLER.handle(rs);
            product.setProducer(producer);
            product.setCount(rs.getInt("count"));
            product.setImage(AppUtils.mapImageBytesToImageWithPrefix(rs.getBytes("product_image")));
            return product;
        }
    };

    public static final ResultSetHandler<Category> CATEGORY_RESULT_SET_HANDLER = new ResultSetHandler<Category>() {
        @Override
        public Category handle(ResultSet rs) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("category_id"));
            category.setName(rs.getString("category_name"));
            category.setParentCategoryId(rs.getInt("parent_id"));
            category.setImage(AppUtils.mapImageBytesToImageWithPrefix(rs.getBytes("category_image")));
            Language language = LANGUAGE_RESULT_SET_HANDLER.handle(rs);
            category.setLanguage(language);
            return category;
        }
    };

    public static final ResultSetHandler<Producer> PRODUCER_RESULT_SET_HANDLER = rs -> {
        Producer producer = new Producer();
        producer.setId(rs.getInt("producer_id"));
        producer.setName(rs.getString("producer_name"));
        return producer;
    };

    public static final ResultSetHandler<User> USER_RESULT_SET_HANDLER = new ResultSetHandler<User>() {
        @Override
        public User handle(ResultSet rs) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setBirthDate(rs.getDate("birth_date"));
            UserStatus userStatus = USER_STATUS_RESULT_SET_HANDLER.handle(rs);
            user.setStatus(userStatus);
            return user;
        }
    };

    public static final ResultSetHandler<OrderItem> ORDER_ITEM_RESULT_SET_HANDLER = rs -> {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getLong("order_item_id"));
        orderItem.setOrderId(rs.getLong("order_id"));
        Product product = PRODUCT_RESULT_SET_HANDLER.handle(rs);
        orderItem.setProduct(product);
        orderItem.setCount(rs.getInt("count_order_item"));
        orderItem.setRetainedProductPrice(rs.getBigDecimal("retained_product_price"));
        return orderItem;
    };

    public static final ResultSetHandler<Order> ORDER_RESULT_SET_HANDLER = new ResultSetHandler<Order>() {
        @Override
        public Order handle(ResultSet rs) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong("order_id"));
            order.setUserId(rs.getInt("user_id"));
            order.setCreated(rs.getTimestamp("created"));
            OrderStatus orderStatus = ORDER_STATUS_RESULT_SET_HANDLER.handle(rs);
            order.setStatus(orderStatus);
            order.setCost(rs.getBigDecimal("cost"));
            order.setAddress(rs.getString("address"));
            return order;
        }
    };

    public static final ResultSetHandler<OrderStatus> ORDER_STATUS_RESULT_SET_HANDLER = rs -> {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(rs.getInt("order_status_id"));
        orderStatus.setName(rs.getString("name"));
        orderStatus.setExplanation(rs.getString("explanation"));
        return orderStatus;
    };

    public static final ResultSetHandler<UserStatus> USER_STATUS_RESULT_SET_HANDLER = rs -> {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(rs.getInt("user_status_id"));
        userStatus.setName(rs.getString("name"));
        userStatus.setExplanation(rs.getString("explanation"));
        return userStatus;
    };

    public static final ResultSetHandler<Language> LANGUAGE_RESULT_SET_HANDLER = rs -> {
        Language language = new Language();
        language.setId(rs.getInt("language_id"));
        language.setCode(rs.getString("code"));
        language.setName(rs.getString("language_name"));
        return language;
    };

    public static ResultSetHandler<Long> getLongResultSetHandler() {
        return rs -> {
            rs.next();
            return rs.getLong(INDEX_OF_FIRST_COLUMN);
        };
    }

    public static ResultSetHandler<Integer> getIntResultSetHandler() {
        return rs -> {
            rs.next();
            return rs.getInt(INDEX_OF_FIRST_COLUMN);
        };
    }

    public static <T> ResultSetHandler<T> getSingleResultSetHandler(final ResultSetHandler<T> oneRowResultSetHandler) {
        return rs -> {
            if (rs.next()) {
                return oneRowResultSetHandler.handle(rs);
            } else {
                return null;
            }
        };
    }

    public static <T> ResultSetHandler<List<T>> getListResultSetHandler(final ResultSetHandler<T> oneRowResultSetHandler) {
        return rs -> {
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                list.add(oneRowResultSetHandler.handle(rs));
            }
            return list;
        };
    }
}
