package cn.BookWorm.admin.category.web.servlet;

import cn.BookWorm.admin.category.serivce.AdminCategoryService;
import cn.BookWorm.category.domain.Category;
import cn.BookWorm.category.service.CategoryService;
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
import java.util.UUID;

@WebServlet(name = "AdminCategoryServlet", urlPatterns = "/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends HttpServlet {
    private AdminCategoryService adminCategoryService = new AdminCategoryService();

    /**
     * 查询所有分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<Category> categoryList = adminCategoryService.findAll();

        req.setAttribute("parents", categoryList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 添加一级分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void addParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Category category = new Category();

        try {
            BeanUtils.copyProperties(category, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        category.setCategoryId(UUID.randomUUID().toString().replace("-",""));

        Category parent = new Category();
        parent.setCategoryId(null);
        category.setParent(parent);

        adminCategoryService.addCategory(category);

        List<Category> categoryList = adminCategoryService.findAll();
        req.setAttribute("parents", categoryList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 为添加二级分类做准备
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void addChildBefore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<Category> parents = adminCategoryService.findFirstLevel();

        String parentId = req.getParameter("parentId");
        req.setAttribute("parentId", parentId);

        req.setAttribute("parents", parents);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/add2.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 添加二级分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void addChild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Category category = new Category();

        try {
            BeanUtils.copyProperties(category, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Category parent = new Category();
        parent.setCategoryId(req.getParameter("parentId"));
        category.setParent(parent);

        category.setCategoryId(UUID.randomUUID().toString().replace("-",""));

        adminCategoryService.addCategory(category);

        List<Category> categoryList = adminCategoryService.findAll();
        req.setAttribute("parents", categoryList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 编辑一级分类前的准备
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void editParentBefore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String parentId = req.getParameter("parentId");

        Category parent = adminCategoryService.loadCategory(parentId);

        req.setAttribute("parent", parent);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/edit.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 编辑一级分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void editParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Category category = new Category();

        try {
            BeanUtils.copyProperties(category, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Category parent = new Category();
        parent.setCategoryId(null);
        category.setParent(parent);

        adminCategoryService.editCategory(category);

        List<Category> categoryList = adminCategoryService.findAll();
        req.setAttribute("parents", categoryList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 编辑二级分类前的准备
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void editChildBefore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String childId = req.getParameter("childId");

        List<Category> parents = adminCategoryService.findFirstLevel();
        Category child = adminCategoryService.loadCategory(childId);

        req.setAttribute("parents", parents);
        req.setAttribute("child", child);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/edit2.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 编辑二级分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void editChild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Category category = new Category();

        try {
            BeanUtils.copyProperties(category, req.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Category parent = new Category();
        parent.setCategoryId(req.getParameter("parentId"));
        category.setParent(parent);

        adminCategoryService.editCategory(category);

        List<Category> categoryList = adminCategoryService.findAll();
        req.setAttribute("parents", categoryList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/list.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 删除一级分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void deleteParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String parentId = req.getParameter("parentId");

        //获取该一级分类下的二级分类个数
        int count = adminCategoryService.findChildrenCountByParent(parentId);

        if(count > 0)
        {
            req.setAttribute("code", "错误");
            req.setAttribute("message", "该分类还有二级分类，不能删除");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            adminCategoryService.deleteCategory(parentId);

            List<Category> categoryList = adminCategoryService.findAll();
            req.setAttribute("parents", categoryList);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/list.jsp");
            dispatcher.forward(req, resp);
        }
    }

    /**
     * 删除二级分类
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void deleteChild(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String childId = req.getParameter("childId");

        //获取该二级分类下的图书数目
        int count = adminCategoryService.findBookNumByChild(childId);

        if(count > 0)
        {
            req.setAttribute("code", "错误");
            req.setAttribute("message", "该分类还有图书，不能删除");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/msg.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            adminCategoryService.deleteCategory(childId);

            List<Category> categoryList = adminCategoryService.findAll();
            req.setAttribute("parents", categoryList);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/category/list.jsp");
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
        else if(method.equals("addParent"))
        {
            addParent(req, resp);
        }
        else if(method.equals("addChildBefore"))
        {
            addChildBefore(req, resp);
        }
        else if(method.equals("addChild"))
        {
            addChild(req, resp);
        }
        else if(method.equals("editParentBefore"))
        {
            editParentBefore(req, resp);
        }
        else if(method.equals("editParent"))
        {
            editParent(req, resp);
        }
        else if(method.equals("editChildBefore"))
        {
            editChildBefore(req, resp);
        }
        else if(method.equals("editChild"))
        {
            editChild(req, resp);
        }
        else if(method.equals("deleteParent"))
        {
            deleteParent(req, resp);
        }
        else if(method.equals("deleteChild"))
        {
            deleteChild(req, resp);
        }
    }
}
