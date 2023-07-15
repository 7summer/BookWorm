package cn.BookWorm.admin.order.web.servlet;

import cn.BookWorm.admin.order.service.AdminOrderService;
import cn.BookWorm.order.domain.Order;
import cn.BookWorm.page.PageBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminOrderServlet", urlPatterns = "/admin/AdminOrderServlet")
public class AdminOrderServlet extends HttpServlet {
    private AdminOrderService adminOrderService = new AdminOrderService();

    /**
     * 加载所有订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //通过页面传递，得到pageCode，如果没有给，默认为1
        int pageCode = 1;
        String param = req.getParameter("pageCode"); //获取pageCode
        //如果param不为空
        if(param != null && !param.trim().isEmpty())
        {
            pageCode = Integer.parseInt(param);
        }

        //得到url
        String url = req.getRequestURI() + "?" + req.getQueryString();
        int index = url.lastIndexOf("&pageCode=");
        if(index != -1)
        {
            url = url.substring(0, index);
        }

        PageBean pageBean = adminOrderService.findAll(pageCode);
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/order/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 根据订单状态查找订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //通过页面传递，得到pageCode，如果没有给，默认为1
        int pageCode = 1;
        String param = req.getParameter("pageCode"); //获取pageCode
        //如果param不为空
        if(param != null && !param.trim().isEmpty())
        {
            pageCode = Integer.parseInt(param);
        }

        int status = Integer.parseInt(req.getParameter("status"));

        //得到url
        String url = req.getRequestURI() + "?" + req.getQueryString();
        int index = url.lastIndexOf("&pageCode=");
        if(index != -1)
        {
            url = url.substring(0, index);
        }

        PageBean pageBean = adminOrderService.findByStatus(pageCode, status);
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/order/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 查看订单详细
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByOrderId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String orderId = req.getParameter("orderId");
        String btn = req.getParameter("btn");

        Order order = adminOrderService.findByOrderId(orderId);

        req.setAttribute("order", order);
        req.setAttribute("btn", btn);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/order/desc.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 发货
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void deliver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String orderId = req.getParameter("orderId");

        int orderStatus = adminOrderService.findStatusByOrderId(orderId); //查询订单状态
        if(orderStatus == 2) //订单状态为未发货
        {
            //将订单更新为未收货
            adminOrderService.updateStatus(orderId, 3);

            req.setAttribute("code", "成功");
            req.setAttribute("message", "订单已发货");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            req.setAttribute("code", "错误");
            req.setAttribute("message", "订单还不能发货");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
    }

    /**
     * 取消订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void cancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String orderId = req.getParameter("orderId");

        int orderStatus = adminOrderService.findStatusByOrderId(orderId); //查询订单状态
        if(orderStatus == 1) //订单状态为未付款
        {
            //将订单状态更新为已取消
            adminOrderService.updateStatus(orderId, 5);

            req.setAttribute("code", "成功");
            req.setAttribute("message", "订单已取消");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            req.setAttribute("code", "错误");
            req.setAttribute("message", "订单不能取消");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        if(method.equals("findAll"))
        {
            findAll(req, resp);
        }
        else if(method.equals("findByStatus"))
        {
            findByStatus(req, resp);
        }
        else if(method.equals("findByOrderId"))
        {
            findByOrderId(req, resp);
        }
        else if(method.equals("deliver"))
        {
            deliver(req, resp);
        }
        else if(method.equals("cancel"))
        {
            cancel(req, resp);
        }
    }
}
