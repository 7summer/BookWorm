package cn.BookWorm.admin.order.service;

import cn.BookWorm.admin.order.dao.AdminOrderDao;
import cn.BookWorm.order.service.OrderService;
import cn.BookWorm.page.PageBean;

import java.sql.SQLException;

public class AdminOrderService extends OrderService {
    private AdminOrderDao adminOrderDao = new AdminOrderDao();

    /**
     * 加载所有订单
     * @param pageCode
     * @return
     */
    public PageBean findAll(int pageCode)
    {
        try {
            return adminOrderDao.findAll(pageCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据订单状态查找订单
     * @param pageCode
     * @param status
     * @return
     */
    public PageBean findByStatus(int pageCode, int status)
    {
        try {
            return adminOrderDao.findByStatus(pageCode, status);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
