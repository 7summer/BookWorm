package cn.BookWorm.admin.admin.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.admin.admin.domain.Admin;
import cn.BookWorm.user.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class AdminDao {
    private QueryRunner qr = new QueryRunner(DataUntil.getDataSource());

    public Admin login(String adminName, String password) throws SQLException {
        String sql = "select * from admin where adminName=? and password=?";

        Admin admin = qr.query(sql, new BeanHandler<Admin>(Admin.class), adminName, password);

        return admin;
    }

}
