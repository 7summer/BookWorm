package cn.BookWorm.admin.admin.service;

import cn.BookWorm.admin.admin.dao.AdminDao;
import cn.BookWorm.admin.admin.domain.Admin;

import java.sql.SQLException;

public class AdminService {
    private AdminDao adminDao = new AdminDao();

    public Admin find(String adminName, String password)
    {
        try {
            return adminDao.login(adminName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
