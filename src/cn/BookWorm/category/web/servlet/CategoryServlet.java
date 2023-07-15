package cn.BookWorm.category.web.servlet;

import cn.BookWorm.category.domain.Category;
import cn.BookWorm.category.service.CategoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 分类模块控制层
 */
@WebServlet(name = "CategoryServlet", urlPatterns = "/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();

    /**
     * 查询一级分类，一级分类包含二级分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<Category> categoryList = categoryService.findAll();

        req.setAttribute("parents", categoryList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/left.jsp");
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
    }
}
