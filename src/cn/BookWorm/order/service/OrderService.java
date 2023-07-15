package cn.BookWorm.order.service;

import cn.BookWorm.order.dao.OrderDao;
import cn.BookWorm.order.domain.Order;
import cn.BookWorm.page.PageBean;

import java.sql.SQLException;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    public PageBean findByUserId(String userId, int pageCode)
    {
        try {
            return orderDao.findByUserId(userId, pageCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createOrder(Order order)
    {
        try {
            orderDao.createOrder(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order findByOrderId(String orderId)
    {
        try {
            return orderDao.findByOrderId(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findStatusByOrderId(String orderId)
    {
        try {
            return orderDao.findStatusByOrderId(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatus(String orderId, int status)
    {
        try {
            orderDao.updateStatus(orderId, status);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
