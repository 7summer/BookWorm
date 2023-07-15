package cn.BookWorm.book.web.servlet;

import cn.BookWorm.book.domain.Book;
import cn.BookWorm.book.service.BookService;
import cn.BookWorm.page.PageBean;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet(name = "BookServlet", urlPatterns = "/BookServlet")
public class BookServlet extends HttpServlet {
    private BookService bookService = new BookService();

    /**
     * 按分类号查找
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByCategoryId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
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

        //获取查询条件，即categoryId
        String categoryId = req.getParameter("categoryId");
        //使用categoryId和pageCode调用bookService.findByCategoryId()，返回PageBean对象
        PageBean pageBean = bookService.findByCategoryId(categoryId, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/book/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 按作者查询
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByAuthor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
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

        //获取查询条件，即categoryId
        String author = req.getParameter("author");
        //使用categoryId和pageCode调用bookService.findByCategoryId()，返回PageBean对象
        PageBean pageBean = bookService.findByAuthor(author, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/book/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 按出版社查询
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByPublishHouse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
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

        //获取查询条件，即categoryId
        String publishHouuse = req.getParameter("publishHouse");
        //使用categoryId和pageCode调用bookService.findByCategoryId()，返回PageBean对象
        PageBean pageBean = bookService.findByPublishHouse(publishHouuse, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/book/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 按书名查询
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByBookName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
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

        //获取查询条件，即categoryId
        String bookName = req.getParameter("bookName");
        //使用categoryId和pageCode调用bookService.findByCategoryId()，返回PageBean对象
        PageBean pageBean = bookService.findByBookName(bookName, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/book/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 多条件组合查询
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByCombination(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
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

        //获取查询条件，即categoryId
        Book criteria = new Book();
        try {
            BeanUtils.copyProperties(criteria, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        //使用categoryId和pageCode调用bookService.findByCategoryId()，返回PageBean对象
        PageBean pageBean = bookService.findByCombination(criteria, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/book/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 通过图书编号加载图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByBookId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String bookId = req.getParameter("bookId");
        Book book = bookService.findByBookId(bookId);

        req.setAttribute("book", book);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/book/desc.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        if(method.equals("findByCategoryId"))
        {
            findByCategoryId(req, resp);
        }
        else if(method.equals("findByAuthor"))
        {
            findByAuthor(req, resp);
        }
        else if(method.equals("findByPublishHouse"))
        {
            findByPublishHouse(req, resp);
        }
        else if(method.equals("findByBookName"))
        {
            findByBookName(req, resp);
        }
        else if(method.equals("findByCombination"))
        {
            findByCombination(req, resp);
        }
        else if(method.equals("findByBookId"))
        {
            findByBookId(req, resp);
        }
    }
}
