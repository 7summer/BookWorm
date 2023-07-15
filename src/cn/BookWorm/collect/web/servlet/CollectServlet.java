package cn.BookWorm.collect.web.servlet;

import cn.BookWorm.book.domain.Book;
import cn.BookWorm.collect.domain.Collect;
import cn.BookWorm.collect.service.CollectService;
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
import java.util.UUID;

@WebServlet(name = "CollectServlet", urlPatterns = "/CollectServlet")
public class CollectServlet extends HttpServlet {
    private CollectService collectService = new CollectService();

    /**
     * 查找所有收藏
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByUserId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = (User) req.getSession().getAttribute("user");
        String userId = user.getUserId();

        List<Collect> collectList = collectService.findByUserId(userId);

        req.setAttribute("collectList", collectList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/collect/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 收藏图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void addCollect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = (User) req.getSession().getAttribute("user");
        PrintWriter out = resp.getWriter();

        if(user == null)
        {
            out.print("false");
        }
        else
        {
            Collect collect = new Collect();
            collect.setCollectId(UUID.randomUUID().toString().replaceAll("-",""));

            Book book = new Book();
            book.setBookId(req.getParameter("bookId"));

            collect.setBook(book);
            collect.setUser(user);

            collectService.addCollect(collect);

            out.print("true");
        }
    }

    /**
     * 批量删除
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void batchDeleteCollect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String collectIds = req.getParameter("collectIds");

        Object[] collectIdArray = collectIds.split(",");

        collectService.batchDeleteCollect(collectIdArray);

        findByUserId(req, resp);
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
        else if(method.equals("addCollect"))
        {
            addCollect(req, resp);
        }
        else if(method.equals("batchDeleteCollect"))
        {
            batchDeleteCollect(req, resp);
        }
    }
}
