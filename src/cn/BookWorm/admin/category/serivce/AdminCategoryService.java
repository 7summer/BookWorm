package cn.BookWorm.admin.category.serivce;

import cn.BookWorm.admin.category.dao.AdminCategoryDao;
import cn.BookWorm.category.domain.Category;
import cn.BookWorm.category.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class AdminCategoryService extends CategoryService {
    private AdminCategoryDao adminCategoryDao = new AdminCategoryDao();

    /**
     * 添加一级分类
     * @param category
     */
    public void addCategory(Category category)
    {
        try {
            adminCategoryDao.addCategory(category);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有一级分类，不包括二级分类
     * @return
     */
    public List<Category> findFirstLevel()
    {
        try {
            return adminCategoryDao.findFirstLevel();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据categoryId加载分类
     * @param categoryId
     * @return
     */
    public Category loadCategory(String categoryId)
    {
        try {
            return adminCategoryDao.loadCategory(categoryId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新一级分类或二级分类
     * @param category
     */
    public void editCategory(Category category)
    {
        try {
            adminCategoryDao.editCategory(category);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询parentId分类下的二级分类数目
     * @param parentId
     * @return
     */
    public int findChildrenCountByParent(String parentId)
    {
        try {
            return adminCategoryDao.findChildrenCountByParent(parentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询childId分类下的图书数目
     * @param childId
     * @return
     */
    public int findBookNumByChild(String childId)
    {
        try {
            return adminCategoryDao.findBookNumByChild(childId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除分类
     * @param categoryId
     */
    public void deleteCategory(String categoryId)
    {
        try {
            adminCategoryDao.deleteCategory(categoryId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
