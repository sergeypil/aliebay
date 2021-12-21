package com.epam.aliebay.dao.Interface;

import com.epam.aliebay.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderStatusDao {

    Optional<OrderStatus> getOrderStatusById(int id, String language);

    List<OrderStatus> getAllOrderStatuses(String language);
}
