package com.boat.service;

import com.boat.dao.OrderDao;
import com.boat.entity.Order;

import java.util.List;

public interface OrderService extends OrderDao {
    void addOrder(Order order);

    void updateOrder(Order order);

    Order getOrder(int id);

    List<Order> checkOrderByName(String name);

    void deleteOrder(int id);

    void updateOrderByStatus(Order order);

    List<Order> list();
}
