package cn.BookWorm.admin.book.web.servlet;

import cn.BookWorm.admin.book.service.AdminBookService;
import cn.BookWorm.admin.category.serivce.AdminCategoryService;
import cn.BookWorm.book.domain.Book;
import cn.BookWorm.category.domain.Category;
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
import java.util.List;

@WebServlet(name = "AdminBookServlet", urlPatterns = "/admin/AdminBookServlet")
public class AdminBookServlet extends HttpServlet {
    private AdminBookService adminBookService = new AdminBookService();
    private AdminCategoryService adminCategoryService = new AdminCategoryService();

    /**
     * 查询一级分类，一级分类包含二级分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<Category> categoryList = adminCategoryService.findAll();

        req.setAttribute("parents", categoryList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/book/left.jsp");
        dispatcher.forward(req, resp);
    }

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
        PageBean pageBean = adminBookService.findByCategoryId(categoryId, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/book/list.jsp");
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
        PageBean pageBean = adminBookService.findByAuthor(author, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/book/list.jsp");
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
        PageBean pageBean = adminBookService.findByPublishHouse(publishHouuse, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/book/list.jsp");
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
        PageBean pageBean = adminBookService.findByBookName(bookName, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/book/list.jsp");
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
        PageBean pageBean = adminBookService.findByCombination(criteria, pageCode);
        //给PageBean对象设置url
        pageBean.setUrl(url);

        req.setAttribute("pageBean", pageBean);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/book/list.jsp");
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
        Book book = adminBookService.findByBookId(bookId);

        //获取所有一级分类，不带二级分类
        List<Category> parents = adminCategoryService.findFirstLevel();
        req.setAttribute("parents", parents);
        //获取图书二级分类对应的一级分类中的所有二级分类
        List<Category> children = adminCategoryService.findByParentId(book.getParent().getParent().getCategoryId());
        req.setAttribute("children", children);

        req.setAttribute("book", book);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/book/desc.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * ajax异步加载指定一级分类的二级分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String parentId = req.getParameter("parentId");

        List<Category> children = adminCategoryService.findByParentId(parentId);

        String json = listToJson(children);

        resp.getWriter().print(json);
    }

    /**
     * 将List<Category>转换为Json
     * @param categoryList
     * @return
     */
    private String listToJson(List<Category> categoryList)
    {
        StringBuffer buffer = new StringBuffer("[");

        int length = categoryList.size();
        for(int i=0; i<length; i++)
        {
            Category category = categoryList.get(i);
            buffer.append(categoryToJson(category));
            if(i < length-1)
            {
                buffer.append(",");
            }
        }

        buffer.append("]");

        return buffer.toString();
    }

    /**
     * 将Category转换为Json
     * @param category
     * @return
     */
    private String categoryToJson(Category category)
    {
        StringBuffer buffer = new StringBuffer("{");

        buffer.append("\"categoryId\":").append("\"").append(category.getCategoryId()).append("\"").append(",");
        buffer.append("\"categoryName\":").append("\"").append(category.getCategoryName()).append("\"");

        buffer.append("}");

        return buffer.toString();
    }

    /**
     * 编辑图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void editBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Book book = new Book();
        try {
            BeanUtils.copyProperties(book, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        String childId = req.getParameter("childId");

        Category parent = new Category();
        parent.setCategoryId(childId);

        book.setParent(parent);

        adminBookService.updateBook(book);

        req.setAttribute("code","成功");
        req.setAttribute("message","修改图书成功");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/msg.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 删除图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void deleteBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String bookId = req.getParameter("bookId");

        adminBookService.deleteBook(bookId);

        req.setAttribute("code", "成功");
        req.setAttribute("message", "删除图书成功");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/msg.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 添加图书前的准备
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void addBookBefore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<Category> parents = adminCategoryService.findFirstLevel();

        req.setAttribute("parents", parents);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/book/add.jsp");
        dispatcher.forward(req, resp);
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
        else if(method.equals("findByCategoryId"))
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
        else if(method.equals("ajaxFindChildren"))
        {
            ajaxFindChildren(req, resp);
        }
        else if(method.equals("editBook"))
        {
            editBook(req,resp);
        }
        else if(method.equals("deleteBook"))
        {
            deleteBook(req, resp);
        }
        else if(method.equals("addBookBefore"))
        {
            addBookBefore(req, resp);
        }
    }
}
