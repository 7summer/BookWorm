package cn.BookWorm.user.web.servlet;

import cn.BookWorm.user.domain.User;
import cn.BookWorm.user.service.UserService;
import cn.BookWorm.user.service.exception.UserException;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户模块控制层
 */
@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();

    /**
     * ajax用户名是否注册
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void ajaxValidateUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //获取用户名
        String userName = req.getParameter("userName");
        //通过service得到校验结果
        boolean b = userService.ajaxValidateUserName(userName);
        //发给客户端
        PrintWriter out = resp.getWriter();
        out.print(b);
    }

    /**
     * ajax邮箱是否注册
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //获取邮箱
        String email = req.getParameter("email");
        //通过service得到校验结果
        boolean b = userService.ajaxValidateEmail(email);
        //发给客户端
        PrintWriter out = resp.getWriter();
        out.print(b);
    }

    /**
     * ajax验证码是否正确
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //获取输入框中的验证码
        String verifyCode = req.getParameter("verifyCode");
        //获取图片上真实的验证码
        String vcode = (String) req.getSession().getAttribute("check_code");
        //进行忽略大小写比较，得到结果
        boolean b = verifyCode.equalsIgnoreCase(vcode);
        //发给客户端
        PrintWriter out = resp.getWriter();
        out.print(b);
    }

    /**
     * 注册功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //封装表单数据到user对象
        User formUser = new User();
        try {
            BeanUtils.copyProperties(formUser, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        //校验，如果校验失败，保存错误信息，返回到register.jsp显示
        Map<String, String> errors = validateRegist(req.getSession(), formUser);
        if(errors.size() > 0)
        {
            req.setAttribute("formUser", formUser);
            req.setAttribute("errors", errors);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/user/register.jsp");
            dispatcher.forward(req, resp);

            return;
        }

        //使用service完成业务
        userService.regist(req.getRequestURL().toString(), formUser);

        //保存成功信息，转发到msg.jsp显示
        req.setAttribute("code", "成功");
        req.setAttribute("message", "恭喜，注册成功，请到邮箱完成账号激活");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 校验注册表单信息
     * @param session
     * @param formUser
     * @return 错误信息
     * @throws ServletException
     * @throws IOException
     */
    public Map<String, String> validateRegist(HttpSession session, User formUser)
    {
        Map<String, String> errors = new HashMap<String, String>();

        //对用户名进行校验
        String userName = formUser.getUserName();
        if(userName == null || userName.trim().isEmpty())
        {
            errors.put("userName", "用户名不能为空");
        }
        else if(userName.length()<3 || userName.length()>20)
        {
            errors.put("userName", "用户名长度为3-20！");
        }
        else if(userService.ajaxValidateUserName(userName))
        {
            errors.put("userName", "该用户名已被注册");
        }

        //对密码进行校验
        String loginPassword = formUser.getLoginPassword();
        if(loginPassword == null || loginPassword.trim().isEmpty())
        {
            errors.put("loginPassword", "密码不能为空");
        }
        else if(loginPassword.length()<6 || loginPassword.length()>30)
        {
            errors.put("loginPassword", "密码长度为6-30！");
        }

        //对确认密码进行校验
        String reloginPassword = formUser.getReLoginPassword();
        if(reloginPassword == null || reloginPassword.trim().isEmpty())
        {
            errors.put("reLoginPassword", "确认密码不能为空");
        }
        else if(!reloginPassword.equals(loginPassword))
        {
            errors.put("reLoginPassword", "两次输入密码不同！");
        }

        //对邮箱进行校验
        String email = formUser.getEmail();
        if(email == null || email.trim().isEmpty())
        {
            errors.put("email", "邮箱不能为空");
        }
        else if(!email.matches("[a-zA-Z0-9-_]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z-_]+)+"))
        {
            errors.put("email", "邮箱格式错误");
        }
        else if(userService.ajaxValidateEmail(email))
        {
            errors.put("email", "该邮箱已被注册");
        }

        //验证码校验
        String verifyCode = formUser.getVerifyCode();
        String check_code = (String) session.getAttribute("check_code");
        if(verifyCode == null || verifyCode.trim().isEmpty())
        {
            errors.put("verifyCode", "验证码不能为空");
        }
        else if(!verifyCode.equalsIgnoreCase(check_code))
        {
            errors.put("verifyCode", "验证码错误");
        }

        return errors;
    }

    /**
     * 激活账号
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void activation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //获取激活码
        String activationCode = req.getParameter("activationCode");
        //把激活码交给service的activation(String)来完成激活
        try {
            userService.activation(activationCode);
            //保存成功信息，转发到msg.jsp显示
            req.setAttribute("code", "成功");
            req.setAttribute("message", "恭喜，完成激活");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        } catch (UserException e) {
            //service可能出现异常，把异常信息拿来，保存到request中，转发到msg.jsp中
            req.setAttribute("code", "错误");
            req.setAttribute("message", e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
    }

    /**
     * 登录功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //封装表单数据到user中
        User formUser = new User();
        try {
            BeanUtils.copyProperties(formUser, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        //校验表单数据
        Map<String, String> errors = validateLogin(req.getSession(), formUser);
        if(errors.size() > 0)
        {
            req.setAttribute("formUser", formUser);
            req.setAttribute("errors", errors);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/user/login.jsp");
            dispatcher.forward(req, resp);

            return;
        }

        //把登录工作交给service，返回user对象
        User user = userService.login(formUser);
        //如果user不存在，登录失败，保存错误信息和表单信息，重定位到login.jsp
        if(user == null)
        {
            req.setAttribute("formUser", formUser);
            req.setAttribute("message", "用户名或密码错误");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/user/login.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            //判断用户状态是否为1，如果为0，说明用户没有激活账号，保存错误信息和表单，重定向到login.jsp
            if(user.getStatus() == 0)
            {
                req.setAttribute("formUser", user);
                req.setAttribute("message", "你还没有激活");

                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/user/login.jsp");
                dispatcher.forward(req, resp);
            }
            else
            {
                //如果user存在，说明登录成功，把当前用户保存到session和cookie中，重定向到主页
                //保存用户到session
                req.getSession().setAttribute("user", user);
                //获取用户名保存到cookie
                Cookie cookie = new Cookie("userName", user.getUserName());
                cookie.setMaxAge(60 * 60 * 24 * 7); //7天
                resp.addCookie(cookie);

                RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
                dispatcher.forward(req, resp);
            }
        }
    }

    /**
     * 校验登录表单信息
     * @param session
     * @param formUser
     * @return
     */
    public Map<String, String> validateLogin(HttpSession session, User formUser)
    {
        Map<String, String> errors = new HashMap<String, String>();

        //对用户名进行校验
        String userName = formUser.getUserName();
        if(userName == null || userName.trim().isEmpty())
        {
            errors.put("userName", "用户名不能为空");
        }
        else if(userName.length()<3 || userName.length()>20)
        {
            errors.put("userName", "用户名长度为3-20！");
        }

        //对密码进行校验
        String loginPassword = formUser.getLoginPassword();
        if(loginPassword == null || loginPassword.trim().isEmpty())
        {
            errors.put("loginPassword", "密码不能为空");
        }
        else if(loginPassword.length()<6 || loginPassword.length()>30)
        {
            errors.put("loginPassword", "密码长度为6-30！");
        }

        //验证码校验
        String verifyCode = formUser.getVerifyCode();
        String check_code = (String) session.getAttribute("check_code");
        if(verifyCode == null || verifyCode.trim().isEmpty())
        {
            errors.put("verifyCode", "验证码不能为空");
        }
        else if(!verifyCode.equalsIgnoreCase(check_code))
        {
            errors.put("verifyCode", "验证码错误");
        }

        return errors;
    }

    /**
     * 更新密码
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //将表单数据封装到user
        User formUser = new User();
        try {
            BeanUtils.copyProperties(formUser, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        //校验表单信息
        Map<String, String> errors = validateUpdatePassowrd(req.getSession(), formUser);
        if(errors.size() > 0)
        {
            req.setAttribute("errors", errors);
            req.setAttribute("formUser", formUser);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/user/changePw.jsp");
            dispatcher.forward(req, resp);

            return;
        }

        //校验旧密码
        User user = (User) req.getSession().getAttribute("user");
        //如果用户没有登录，返回到登录页面，显示错误信息
        if(user == null)
        {
            req.setAttribute("message", "你还没有登录");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/user/login.jsp");
            dispatcher.forward(req, resp);

            return;
        }
        try {
            userService.updatePassword(user.getUserId(), formUser.getLoginPassword(), formUser.getNewLoginPassword());

            req.getSession().removeAttribute("user");

            req.setAttribute("code", "成功");
            req.setAttribute("message", "修改密码成功，请到登录页面重新登录");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        } catch (UserException e) {
            req.setAttribute("message", e.getMessage()); //保存异常信息
            req.setAttribute("formUser", formUser);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/user/changePw.jsp");
            dispatcher.forward(req, resp);
        }
    }

    /**
     * 校验修改密码表单信息
     * @param formUser
     * @return
     */
    public Map<String, String> validateUpdatePassowrd(HttpSession session, User formUser)
    {
        Map<String, String> errors = new HashMap<String, String>();

        //对旧密码进行校验
        String loginPassword = formUser.getLoginPassword();
        if(loginPassword == null || loginPassword.trim().isEmpty())
        {
            errors.put("loginPassword", "旧密码不能为空");
        }
        else if(loginPassword.length()<6 || loginPassword.length()>30)
        {
            errors.put("loginPassword", "旧密码长度为6-30！");
        }

        //对新密码进行校验
        String newLoginPassword = formUser.getNewLoginPassword();
        if(newLoginPassword == null || newLoginPassword .trim().isEmpty())
        {
            errors.put("newLoginPassword", "新密码不能为空");
        }
        else if(loginPassword.length()<6 || loginPassword.length()>30)
        {
            errors.put("newLoginPassword", "新密码长度为6-30！");
        }

        //对确认密码进行校验
        String reLoginPassword = formUser.getReLoginPassword();
        if(reLoginPassword == null || reLoginPassword.trim().isEmpty())
        {
            errors.put("reLoginPassword", "确认密码不能为空");
        }
        else if(!reLoginPassword.equals(newLoginPassword))
        {
            errors.put("reLoginPassword", "两次输入密码不同！");
        }

        //验证码校验
        String verifyCode = formUser.getVerifyCode();
        String check_code = (String) session.getAttribute("check_code");
        if(verifyCode == null || verifyCode.trim().isEmpty())
        {
            errors.put("verifyCode", "验证码不能为空");
        }
        else if(!verifyCode.equalsIgnoreCase(check_code))
        {
            errors.put("verifyCode", "验证码错误");
        }

        return errors;
    }

    /**
     * 退出功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void quit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.getSession().removeAttribute("user");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        if(method.equals("ajaxValidateUserName"))
        {
            ajaxValidateUserName(req, resp);
        }
        else if(method.equals("ajaxValidateEmail"))
        {
            ajaxValidateEmail(req, resp);
        }
        else if(method.equals("ajaxValidateVerifyCode"))
        {
            ajaxValidateVerifyCode(req, resp);
        }
        else if(method.equals("regist"))
        {
            regist(req, resp);
        }
        else if(method.equals("activation"))
        {
            activation(req, resp);
        }
        else if(method.equals("login"))
        {
            login(req, resp);
        }
        else if(method.equals("updatePassword"))
        {
            updatePassword(req, resp);
        }
        else if(method.equals("quit"))
        {
            quit(req, resp);
        }
    }
}
