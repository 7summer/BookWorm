package cn.BookWorm.admin.admin.web.servlet;

import cn.BookWorm.admin.admin.domain.Admin;
import cn.BookWorm.admin.admin.service.AdminService;
import cn.BookWorm.page.PageBean;
import org.apache.commons.beanutils.BeanUtils;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AdminServlet", urlPatterns = "/AdminServlet")
public class AdminServlet extends HttpServlet {
    private AdminService adminService = new AdminService();

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
        String vcode = (String) req.getSession().getAttribute("Admin_check_code");
        //进行忽略大小写比较，得到结果
        boolean b = verifyCode.equalsIgnoreCase(vcode);
        //发给客户端
        PrintWriter out = resp.getWriter();
        out.print(b);
    }

    /**
     * 管理员登录
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Admin formAdmin = new Admin();
        try {
            BeanUtils.copyProperties(formAdmin, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> errors = validateLogin(req.getSession(), formAdmin);
        if(errors.size() > 0)
        {
            req.setAttribute("errors", errors);
            req.setAttribute("formAdmin", formAdmin);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/admin/login.jsp");
            dispatcher.forward(req, resp);

            return;
        }

        Admin admin = adminService.find(formAdmin.getAdminName(), formAdmin.getPassword());
        if(admin == null)
        {
            req.setAttribute("formAdmin", formAdmin);
            req.setAttribute("message", "用户名或密码错误");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/admin/login.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            req.getSession().setAttribute("admin", admin);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/index.jsp");
            dispatcher.forward(req, resp);
        }
    }

    /**
     * 校验登录表单
     * @param formAdmin
     * @return
     */
    public Map<String, String> validateLogin(HttpSession session, Admin formAdmin)
    {
        Map<String, String> errors = new HashMap<>();

        //对用户名进行校验
        String adminNmae = formAdmin.getAdminName();
        if(adminNmae == null || adminNmae.trim().isEmpty())
        {
            errors.put("adminName", "用户名不能为空");
        }
        else if(adminNmae.length()<3 || adminNmae.length()>20)
        {
            errors.put("adminName", "用户名长度为3-20！");
        }

        //对密码进行校验
        String password = formAdmin.getPassword();
        if(password == null || password.trim().isEmpty())
        {
            errors.put("password", "密码不能为空");
        }
        else if(password.length()<6 || password.length()>30)
        {
            errors.put("password", "密码长度为6-30！");
        }

        //对验证码进行校验
        String verifyCode = formAdmin.getVerifyCode();
        String check_code = (String) session.getAttribute("Admin_check_code");
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
     * 退出
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void quit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.getSession().removeAttribute("admin");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(method.equals("ajaxValidateVerifyCode"))
        {
            ajaxValidateVerifyCode(req, resp);
        }
        else if(method.equals("login"))
        {
            login(req, resp);
        }
        else if(method.equals("quit"))
        {
            quit(req, resp);
        }
    }
}
