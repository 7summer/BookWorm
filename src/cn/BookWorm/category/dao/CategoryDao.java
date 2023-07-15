package cn.BookWorm.category.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.category.domain.Category;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分类模块持久层
 */
public class CategoryDao {
    private QueryRunner qr = new QueryRunner(DataUntil.getDataSource());

    /**
     * 将map对象映射到category对象
     * @param map categoryId categoryName parentId description
     * @return
     */
    private Category toCategory(Map<String, Object> map)
    {
//        Category category = QuakeTools.mapToObject(map, Category.class);
        Category category = new Category();
        try {
            BeanUtils.copyProperties(category, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        String parentId = (String) map.get("parentId");
        if(parentId != null)
        {
            //使用父分类对象来封装parentId
            Category parent = new Category();
            parent.setCategoryId(parentId);

            category.setParent(parent);
        }

        return category;
    }

    /**
     * 可以把多个Map<String, Object>映射成多个Category
     * @param mapList
     * @return
     */
    private List<Category> toCategoryList(List<Map<String, Object>> mapList)
    {
        List<Category> categoryList = new ArrayList<Category>();

        for(Map<String, Object> map : mapList)
        {
            Category category = toCategory(map);
            categoryList.add(category);
        }

        return categoryList;
    }

    /**
     * 给外部类调用toCategoryList方法提供接口
     * @param mapList
     * @return
     */
    public List<Category> listMapToCategoryList(List<Map<String, Object>> mapList)
    {
        return toCategoryList(mapList);
    }

    /**
     * 给外部类调用toCategory方法提供接口
     * @param map
     * @return
     */
    public Category mapToCategoryList(Map<String, Object> map)
    {
        return toCategory(map);
    }

    /**
     * 返回所有一级分类，一级分类已包含二级分类
     * @return
     * @throws SQLException
     */
    public List<Category> findAll() throws SQLException {
        //查询出所有的一级分类
        String sql = "select * from category where parentId is null";
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
        List<Category> parents = toCategoryList(mapList);

        //循环遍历每个一级分类，为每个一级分类加载它的所有二级分类
        for(Category parent:parents)
        {
            //查询当前父分类的子分类
            List<Category> children = findByParentId(parent.getCategoryId());
            parent.setChildren(children);
        }

        return parents;
    }

    /**
     * 通过父分类查找子分类
     * @param parentId
     * @return
     */
    public List<Category> findByParentId(String parentId) throws SQLException {
        String sql = "select * from category where parentId=?";

        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), parentId);
        return toCategoryList(mapList);
    }

}
