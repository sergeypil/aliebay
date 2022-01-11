package com.epam.aliebay.dao.Interface;

import com.epam.aliebay.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Optional<Order> getOrderById(long id, String language);

    List<Order> getAllOrders(String language);

    List<Order> getOrdersByIdUser(int idUser, String language);

    void saveOrder(Order order);

    void updateOrderStatusInOrder(Order order, long id);

    void updateOrderStatusAndRecalculateCountOfProducts(Order order, long id);
}