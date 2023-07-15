package cn.BookWorm.collect.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.book.domain.Book;
import cn.BookWorm.collect.domain.Collect;
import cn.BookWorm.user.domain.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CollectDao {
    private QueryRunner qr = new QueryRunner(DataUntil.getDataSource());

    /**
     * 把一个map映射成一个Collect
     * @param map
     * @return
     */
    private Collect toCollect(Map<String, Object> map)
    {
        if(map == null || map.size() == 0)
        {
            return null;
        }

        Collect collect = new Collect();
        try {
            BeanUtils.copyProperties(collect, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Book book = new Book();
        try {
            BeanUtils.copyProperties(book, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        User user = new User();
        try {
            BeanUtils.copyProperties(user, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        collect.setUser(user);
        collect.setBook(book);

        return collect;
    }

    /**
     * 把多个map对象映射成一个List<Collect>对象
     * @param mapList
     * @return
     */
    private List<Collect> toCollectList(List<Map<String, Object>> mapList)
    {
        List<Collect> collectList = new ArrayList<>();

        for(Map<String, Object> map : mapList)
        {
            Collect collect = toCollect(map);

            collectList.add(collect);
        }

        return collectList;
    }

    /**
     * 查找所有收藏
     *
     * @return
     */
    public List<Collect> findByUserId(String userId) throws SQLException {
        String sql = "select * from collect c,book b where c.bookId=b.bookId and userId=?";

        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), userId);

        return toCollectList(mapList);
    }

    /**
     * 查询某个用户的某本图书是否收藏
     * @param userId
     * @param bookId
     * @return
     */
    public Collect findByUserIdAndBookId(String userId, String bookId) throws SQLException {
        String sql = "select * from collect where userId=? and bookId=?";

        Map<String, Object> map = qr.query(sql, new MapHandler(), userId, bookId);

        Collect collect = toCollect(map);

        return collect;
    }

    /**
     * 添加收藏
     * @param collect
     */
    public void addCollect(Collect collect) throws SQLException {
        String sql = "insert into collect values(?,?,?)";
        Object[] params = new Object[]{collect.getCollectId(),collect.getBook().getBookId(),
        collect.getUser().getUserId()};

        qr.update(sql, params);
    }

    /**
     * 批量删除收藏条目
     * @param collectIdArray
     */
    public void batchDeleteCollect(Object[] collectIdArray) throws SQLException {
        int length = collectIdArray.length;

        String sql = "delete from collect where collectId=?";

        Object[][] params = new Object[length][1];
        for(int i=0; i<length; i++)
        {
            params[i] = new Object[]{collectIdArray[i]};
        }

        qr.batch(sql, params);
    }
}
