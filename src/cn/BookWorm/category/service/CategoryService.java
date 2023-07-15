package cn.BookWorm.category.service;

import cn.BookWorm.category.dao.CategoryDao;
import cn.BookWorm.category.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * 分类模块业务层
 */
public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();

    /**
     * 返回所有一级分类，一级分类已包含二级分类
     *
     * @return
     */
    public List<Category> findAll()
    {
        try {
            return categoryDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据一级分类查找二级分类
     * @param parentId
     * @return
     */
    public List<Category> findByParentId(String parentId)
    {
        try {
            return categoryDao.findByParentId(parentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
