package cn.BookWorm.cart.web.servlet;

import cn.BookWorm.book.domain.Book;
import cn.BookWorm.cart.domain.CartItem;
import cn.BookWorm.cart.service.CartItemService;
import cn.BookWorm.user.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CartItemServlet", urlPatterns = "/CartItemServlet")
public class CartItemServlet extends HttpServlet {
    private CartItemService cartItemService = new CartItemService();

    /**
     * 通过用户编号查找购物车条目
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByUserId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = (User) req.getSession().getAttribute("user");
        String userId = user.getUserId();

        List<CartItem> cartItemList = cartItemService.findByUserId(userId);

        req.setAttribute("cartItemList", cartItemList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/cart/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 添加购物车条目
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //该方法通过jquery中的ajax调用，需要检验用户是否登录
        User user = (User) req.getSession().getAttribute("user");
        PrintWriter out = resp.getWriter();

        if(user == null)
        {
            out.print("false");
        }
        else
        {
            Map<String, String[]> map = req.getParameterMap();

            CartItem cartItem = new CartItem();
            cartItem.setQuantity(Integer.parseInt(map.get("quantity")[0]));

            Book book = new Book();
            book.setBookId(map.get("bookId")[0]);

            cartItem.setUser(user);
            cartItem.setBook(book);

            cartItemService.add(cartItem);

            out.print("true");
        }
    }

    /**
     * 批量删除购物车条目
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void batchDeleteCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String cartItemIds = req.getParameter("cartItemIds");

        Object[] cartItemArray = cartItemIds.split(",");

        cartItemService.batchDeleteCartItem(cartItemArray);

        findByUserId(req, resp);
    }

    /**
     * 更新指定购物车条目的数量，数量大于0
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void updateQuantity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Map<String, String[]> map = req.getParameterMap();

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(map.get("cartItemId")[0]);
        cartItem.setQuantity(Integer.parseInt(map.get("quantity")[0]));

        cartItemService.updateQuantity(cartItem);

        findByUserId(req, resp);
    }

    /**
     * 加载购物车条目，我的订单需要
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void loadCartItems(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String cartItemIds = req.getParameter("cartItemIds");
        String total = req.getParameter("total");

        Object[] cartItemArray = cartItemIds.split(",");

        List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemArray);

        req.setAttribute("cartItemList", cartItemList);
        req.setAttribute("total", total);
        req.setAttribute("cartItemIds", cartItemIds);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/cart/showitem.jsp");
        dispatcher.forward(req, resp);
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
        else if(method.equals("add"))
        {
            add(req, resp);
        }
        else if(method.equals("batchDeleteCartItem"))
        {
            batchDeleteCartItem(req, resp);
        }
        else if(method.equals("updateQuantity"))
        {
            updateQuantity(req, resp);
        }
        else if(method.equals("loadCartItems"))
        {
            loadCartItems(req, resp);
        }
    }
}
