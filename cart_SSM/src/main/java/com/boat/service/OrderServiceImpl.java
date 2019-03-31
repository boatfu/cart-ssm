package com.boat.service;

import com.boat.dao.OrderDao;
import com.boat.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    public void updateOrderByStatus(Order order) {
         orderDao.updateOrderByStatus(order);
    }

    public void deleteOrder(int id) {
        orderDao.deleteOrder(id);
    }

    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    public Order getOrder(int id) {
        return orderDao.getOrder(id);
    }

    public List<Order> checkOrderByName(String name) {
        return orderDao.checkOrderByName(name);
    }

    public List<Order> list() {
        return orderDao.list();
    }
}
