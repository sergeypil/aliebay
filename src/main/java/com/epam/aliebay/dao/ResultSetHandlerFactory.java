package com.epam.aliebay.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.epam.aliebay.entity.*;

import static com.epam.aliebay.constant.OtherConstants.PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE;

public final class ResultSetHandlerFactory {

    public static ResultSetHandler<Product> PRODUCT_RESULT_SET_HANDLER = new ResultSetHandler<Product>() {
        @Override
        public Product handle(ResultSet rs) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id_product"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getBigDecimal("price"));
            Category category = CATEGORY_RESULT_SET_HANDLER.handle(rs);
            product.setCategory(category);
            Producer producer = PRODUCER_RESULT_SET_HANDLER.handle(rs);
            product.setProducer(producer);
            product.setCount(rs.getInt("count"));
            product.setImage(PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE +
                    Base64.getEncoder().encodeToString(rs.getBytes("image")));
            return product;
        }
    };

    public static ResultSetHandler<Category> CATEGORY_RESULT_SET_HANDLER = new ResultSetHandler<Category>() {
        @Override
        public Category handle(ResultSet rs) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("id_category"));
            category.setName(rs.getString("name_category"));
            category.setParentCategoryId(rs.getInt("id_parent"));
            category.setImage(PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE +
                    Base64.getEncoder().encodeToString(rs.getBytes("image")));
            Language language = LANGUAGE_RESULT_SET_HANDLER.handle(rs);
            category.setLanguage(language);
            return category;
        }
    };

    public static ResultSetHandler<Producer> PRODUCER_RESULT_SET_HANDLER = rs -> {
        Producer producer = new Producer();
        producer.setId(rs.getInt("id_producer"));
        producer.setName(rs.getString("name_producer"));
        return producer;
    };

    public static ResultSetHandler<User> USER_RESULT_SET_HANDLER = new ResultSetHandler<User>() {
        @Override
        public User handle(ResultSet rs) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
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

    public static ResultSetHandler<OrderItem> ORDER_ITEM_RESULT_SET_HANDLER = rs -> {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getLong("id"));
        orderItem.setIdOrder(rs.getLong("id_order"));
        Product product = PRODUCT_RESULT_SET_HANDLER.handle(rs);
        orderItem.setProduct(product);
        orderItem.setCount(rs.getInt("count_order_item"));
        orderItem.setRetainedProductPrice(rs.getBigDecimal("retained_product_price"));
        return orderItem;
    };

    public static ResultSetHandler<Order> ORDER_RESULT_SET_HANDLER = new ResultSetHandler<Order>() {
        @Override
        public Order handle(ResultSet rs) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setIdUser(rs.getInt("id_user"));
            order.setCreated(rs.getTimestamp("created"));
            OrderStatus orderStatus = ORDER_STATUS_RESULT_SET_HANDLER.handle(rs);
            order.setStatus(orderStatus);
            order.setCost(rs.getBigDecimal("cost"));
            order.setAddress(rs.getString("address"));
            return order;
        }
    };

    public static ResultSetHandler<OrderStatus> ORDER_STATUS_RESULT_SET_HANDLER = rs -> {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(rs.getInt("id_order_status"));
        orderStatus.setName(rs.getString("name"));
        orderStatus.setExplanation(rs.getString("explanation"));
        return orderStatus;
    };
    public static ResultSetHandler<UserStatus> USER_STATUS_RESULT_SET_HANDLER = rs -> {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(rs.getInt("id_user_status"));
        userStatus.setName(rs.getString("name"));
        userStatus.setExplanation(rs.getString("explanation"));
        return userStatus;
    };
    public static ResultSetHandler<Language> LANGUAGE_RESULT_SET_HANDLER = rs -> {
        Language language = new Language();
        language.setId(rs.getInt("id_language"));
        language.setCode(rs.getString("code"));
        language.setName(rs.getString("name_language"));
        return language;
    };

    public static ResultSetHandler<Integer> getCountResultSetHandler() {
        return rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        };
    }

    public static ResultSetHandler<Long> getLongResultSetHandler() {
        return rs -> {
            rs.next();
            return rs.getLong("id");
        };
    }

    public static ResultSetHandler<Integer> getIntResultSetHandler() {
        return rs -> {
            rs.next();
            return rs.getInt("id");
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
