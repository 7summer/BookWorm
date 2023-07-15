package cn.BookWorm.order.web.servlet;

import cn.BookWorm.book.domain.Book;
import cn.BookWorm.cart.domain.CartItem;
import cn.BookWorm.cart.service.CartItemService;
import cn.BookWorm.order.domain.Order;
import cn.BookWorm.order.domain.OrderItem;
import cn.BookWorm.order.service.OrderService;
import cn.BookWorm.page.PageBean;
import cn.BookWorm.user.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "OrderServlet", urlPatterns = "/OrderServlet")
public class OrderServlet extends HttpServlet {
    private OrderService orderService = new OrderService();
    private CartItemService cartItemService = new CartItemService();

    /**
     * 通过userId查询用户的订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByUserId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
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

        //获取查询条件，即userID
        User user = (User) req.getSession().getAttribute("user");
        String userId = user.getUserId();
        //使用categoryId和pageCode调用bookService.findByCategoryId()，返回PageBean对象
        PageBean pageBean = orderService.findByUserId(userId, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/order/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //获取购物车条目
        String cartItemIds = req.getParameter("cartItemIds");
        Object[] cartItemArray = cartItemIds.split(",");
        List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemArray);

        //创建order对象
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setOrderTime(new Date());
        order.setAddress(req.getParameter("address"));
        order.setStatus(1); //状态为1 未付款

        User user = (User) req.getSession().getAttribute("user");
        order.setUser(user);

        BigDecimal total = new BigDecimal("0");
        for(CartItem cartItem : cartItemList)
        {
            total = total.add(new BigDecimal(String.valueOf(cartItem.getSubtotal())));
        }
        order.setTotal(total.doubleValue());

        //将购物车条目转换为订单条目
        List<OrderItem> orderItemList = new ArrayList<>();
        for(CartItem cartItem : cartItemList)
        {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(UUID.randomUUID().toString().replaceAll("-", ""));
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItem.setBook(cartItem.getBook());
            orderItem.setOrder(order);

            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);

        //调用service完成数据库添加数据
        orderService.createOrder(order);

        //删除购物车条目
        cartItemService.batchDeleteCartItem(cartItemArray);

        req.setAttribute("order", order);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/order/ordersuccess.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 查看订单详情
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    public void findByOrderId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String orderId = req.getParameter("orderId");

        Order order = orderService.findByOrderId(orderId);

        req.setAttribute("order", order);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/order/desc.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 取消订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void cancelOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String orderId = req.getParameter("orderId");

        int orderStatus = orderService.findStatusByOrderId(orderId); //查询订单状态
        if(orderStatus == 1) //订单状态为未付款
        {
            //将订单状态更新为已取消
            orderService.updateStatus(orderId, 5);

            req.setAttribute("code", "成功");
            req.setAttribute("message", "订单已取消");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            req.setAttribute("code", "错误");
            req.setAttribute("message", "订单不能取消");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
    }

    /**
     * 确认收货
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void confirmOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String orderId = req.getParameter("orderId");

        int orderStatus = orderService.findStatusByOrderId(orderId); //查询订单状态
        if(orderStatus == 3) //订单状态为未收货
        {
            //将订单状态更新为交易成功
            orderService.updateStatus(orderId, 4);

            req.setAttribute("code", "成功");
            req.setAttribute("message", "订单交易成功");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            req.setAttribute("code", "错误");
            req.setAttribute("message", "请确认订单状态");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
    }

    /**
     * 支付订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void payOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String orderId = req.getParameter("orderId");

        int orderStatus = orderService.findStatusByOrderId(orderId); //查询订单状态
        if(orderStatus == 1) //订单状态为未付款
        {
            //订单状态更新为未发货
            orderService.updateStatus(orderId, 2);

            req.setAttribute("code", "成功");
            req.setAttribute("message", "订单已支付");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            req.setAttribute("code", "错误");
            req.setAttribute("message", "请确认订单状态");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/msg.jsp");
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

        if(method.equals("findByUserId"))
        {
            findByUserId(req, resp);
        }
        else if(method.equals("createOrder"))
        {
            createOrder(req, resp);
        }
        else if(method.equals("findByOrderId"))
        {
            findByOrderId(req, resp);
        }
        else if(method.equals("cancelOrder"))
        {
            cancelOrder(req, resp);
        }
        else if(method.equals("confirmOrder"))
        {
            confirmOrder(req, resp);
        }
        else if(method.equals("payOrder"))
        {
            payOrder(req, resp);
        }
    }
}
