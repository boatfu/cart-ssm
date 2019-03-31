package com.boat.dao;

import com.boat.entity.Order;

import java.util.List;

public interface OrderDao {
    void addOrder(Order order);
    void updateOrder(Order order);
    Order getOrder(int id);
    List<Order> checkOrderByName(String name);
    List<Order> list();
    void updateOrderByStatus(Order order);
    void deleteOrder(int id);
}
