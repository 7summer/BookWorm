package cn.BookWorm.user.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.user.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * 用户模块持久层
 */
public class UserDao {
    private QueryRunner qr = new QueryRunner(DataUntil.getDataSource());

    /**
     * 校验用户名是否被注册
     * @param userName 用户名
     * @return
     */
    public boolean ajaxValidateUserName(String userName) throws SQLException {
        String sql = "select count(1) from user where userName=?";
        long num = qr.query(sql, new ScalarHandler<Long>(), userName);

        return num>0;
    }

    /**
     * 校验邮箱是否被注册
     * @param email
     * @return
     * @throws SQLException
     */
    public boolean ajaxValidateEmail(String email) throws SQLException {
        String sql = "select count(1) from user where email=?";
        long num = qr.query(sql, new ScalarHandler<Long>(), email);

        return num>0;
    }

    /**
     * 添加用户
     * @param user
     */
    public void addUser(User user) throws SQLException {
        String sql = "insert into user values(?,?,?,?,?,?)";
        Object[] params = {user.getUserId(), user.getUserName(), user.getLoginPassword(),
            user.getEmail(), user.getStatus(), user.getActivationCode()};
        qr.update(sql, params);
    }

    /**
     * 通过激活码查询用户
     * @param activationCode
     */
    public User findByActivationCode(String activationCode) throws SQLException {
        String sql = "select * from user where activationCode=?";
        return qr.query(sql, new BeanHandler<User>(User.class), activationCode);
    }

    /**
     * 更新user表的status
     * @param userId
     * @param status
     */
    public void updateStatus(String userId, int status) throws SQLException {
        String sql = "update user set status=? where userId=?";
        qr.update(sql, status, userId);
    }

    /**
     * 通过用户名和密码返回user对象
     * @param userName
     * @param loginPassword
     * @return
     */
    public User findByUserNameAndPassword(String userName, String loginPassword) throws SQLException {
        String sql = "select * from user where userName=? and loginPassword=?";

        return qr.query(sql, new BeanHandler<User>(User.class), userName, loginPassword);
    }

    /**
     * 根据userId和oldPassword来查询用户是否存在
     * @param userId
     * @param oldPassword
     * @return
     */
    public boolean findByUserIdAndPassword(String userId, String oldPassword) throws SQLException {
        String sql = "select count(1) from user where userId=? and loginPassword=?";
        return qr.query(sql, new ScalarHandler<Long>(), userId, oldPassword)>0;
    }

    /**
     * 根据userId和newPassword更新密码
     * @param userId
     * @param newPassword
     */
    public void updatePassword(String userId, String newPassword) throws SQLException {
        String sql = "update user set loginPassword=? where userId=?";

        qr.update(sql, newPassword, userId);
    }
}
