package cn.BookWorm.admin.category.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.category.dao.CategoryDao;
import cn.BookWorm.category.domain.Category;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminCategoryDao extends CategoryDao {
    private QueryRunner qr = new QueryRunner(DataUntil.getDataSource());

    /**
     * 添加一级分类
     * @param category
     * @throws SQLException
     */
    public void addCategory(Category category) throws SQLException {
        String sql = "insert into category values(?,?,?,?)";
        Object[] params = new Object[]{category.getCategoryId(), category.getCategoryName(),
        category.getParent().getCategoryId(), category.getDescription()};

        qr.update(sql, params);
    }

    /**
     * 查询所有一级分类，不包含二级分类
     * @return
     */
    public List<Category> findFirstLevel() throws SQLException {
        //查询出所有的一级分类
        String sql = "select * from category where parentId is null";
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
        List<Category> parents = listMapToCategoryList(mapList);

        return parents;
    }

    /**
     * 根据categoryId加载分类
     * @param categoryId
     * @return
     */
    public Category loadCategory(String categoryId) throws SQLException {
        String sql = "select * from category where categoryId=?";

        Map<String, Object> map = qr.query(sql, new MapHandler(), categoryId);

        return mapToCategoryList(map);
    }

    /**
     * 编辑一级分类
     * @param category
     */
    public void editCategory(Category category) throws SQLException {
        String sql = "update category set categoryName=?,description=?,parentId=? where categoryId=?";

        Object[] params = new Object[]{category.getCategoryName(), category.getDescription(),
        category.getParent().getCategoryId(),category.getCategoryId()};

        qr.update(sql, params);
    }

    /**
     * 查询parentId分类下的二级分类数目
     * @param parentId
     * @return
     */
    public int findChildrenCountByParent(String parentId) throws SQLException {
        String sql = "select count(*) from category where parentId=?";

        Number num = qr.query(sql, new ScalarHandler<>(), parentId);

        return num==null ? 0 : num.intValue();
    }

    /**
     * 查询childId分类下的图书数目
     * @param childId
     * @return
     */
    public int findBookNumByChild(String childId) throws SQLException {
        String sql = "select count(*) from book where categoryId=?";

        Number num = qr.query(sql, new ScalarHandler<>() ,childId);

        return num==null ? 0 : num.intValue();
    }

    /**
     * 删除分类
     * @param categoryId
     */
    public void deleteCategory(String categoryId) throws SQLException {
        String sql = "delete from category where categoryId=?";

        qr.update(sql, categoryId);
    }
}
