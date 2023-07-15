package cn.BookWorm.user.domain;

/**
 * 用户模块实体类
 */

/**
 * user表
 * 该模块所有表单
 */
public class User {
    //对应数据库表
    private String userId; //用户编号 主键
    private String userName; //用户名
    private String loginPassword; //登录密码
    private String email; //邮箱
    private int status; //状态 0代表未激活 1代表激活
    private  String activationCode; //激活码

    //注册表单
    private String reLoginPassword; //确认密码
    private String verifyCode; //验证码

    //修改密码表单
    private String newLoginPassword; //新密码
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getReLoginPassword() {
        return reLoginPassword;
    }

    public void setReLoginPassword(String reLoginPassword) {
        this.reLoginPassword = reLoginPassword;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getNewLoginPassword() {
        return newLoginPassword;
    }

    public void setNewLoginPassword(String newLoginPassword) {
        this.newLoginPassword = newLoginPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
