package cn.BookWorm.user.service;

import cn.BookWorm.user.domain.Email;
import cn.BookWorm.user.dao.UserDao;
import cn.BookWorm.user.domain.User;
import cn.BookWorm.user.service.exception.UserException;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * 用户模块业务层
 */
public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 用户名注册校验
     * @param userName
     * @return
     */
    public boolean ajaxValidateUserName(String userName)
    {
        try {
            return userDao.ajaxValidateUserName(userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 邮箱注册校验
     * @param email
     * @return
     */
    public boolean ajaxValidateEmail(String email)
    {
        try {
            return userDao.ajaxValidateEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册功能
     * @param servletPath servlet地址
     * @param user 用户
     */
    public void regist(String servletPath, User user)
    {
        //数据补齐
        user.setUserId(UUID.randomUUID().toString().replaceAll("-","")); //userId用户编号
        user.setStatus(0); //status状态
        user.setActivationCode(UUID.randomUUID().toString().replaceAll("-","")
                + UUID.randomUUID().toString().replaceAll("-","")); //激活码

        //向数据库插入user
        try {
            userDao.addUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //发邮件
        String subject = "网上书城激活邮件---阿威的项目";

        servletPath += ("?method=activation&activationCode="+user.getActivationCode()); //激活链接
        String text = "恭喜，你已注册成功，请点击<a href=\"{0}\">这里</a>完成激活";
        String content = MessageFormat.format(text, servletPath);

        Email email = new Email(user.getEmail(), subject, content);
        //由于发邮件进程较慢，开多线程进行处理
        new Thread(email).start();
    }

    /**
     * 激活用户
     * @param activationCode
     */
    public void activation(String activationCode) throws UserException {
        //使用激活码进行查询，得到user
        try {
            User user = userDao.findByActivationCode(activationCode);

            //user是null，抛出异常，添加异常信息（无效激活码）
            if(user == null) throw new UserException("无效的激活码");
            //查看user的状态是否为1，user的状态为1，抛出异常，添加异常信息
            if(user.getStatus() == 1) throw new UserException("你已经激活过了，不要二次激活");
            //修改用户的状态
            userDao.updateStatus(user.getUserId(), 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据formUser对象的账号密码返回user对象
     * @param formUser
     * @return
     */
    public User login(User formUser)
    {
        //根据账号和密码返回user对象
        try {
            return userDao.findByUserNameAndPassword(formUser.getUserName(), formUser.getLoginPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePassword(String userId, String oldPassword, String newPassword) throws UserException {
        //校验老密码，使用userId和oldPassword去访问dao，得到结果
        try {
            boolean b = userDao.findByUserIdAndPassword(userId, oldPassword);
            if(!b)
            {
                //如果校验失败，抛出异常
                throw new UserException("旧密码错误");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //校验通过，使用userName和newPassword来访问doa，完成修改密码
        try {
            userDao.updatePassword(userId, newPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
