package cn.BookWorm.cart.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.book.domain.Book;
import cn.BookWorm.cart.domain.CartItem;
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

public class CartItemDao {
    private QueryRunner qr = new QueryRunner(DataUntil.getDataSource());

    /**
     * 把一个map映射成一个CartItem
     * @param map
     * @return
     */
    private CartItem toCartItem(Map<String, Object> map) {
        if (map == null ||  map.size() == 0) {
            return null;
        }

        CartItem cartItem = new CartItem();
        try {
            BeanUtils.copyProperties(cartItem, map);
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

        cartItem.setBook(book);
        cartItem.setUser(user);

        return cartItem;
    }

    /**
     * 把多个map对象映射成一个List<CartItem>对象
     * @param mapList
     * @return
     */
    private List<CartItem> toCartItemList(List<Map<String, Object>> mapList) {
        List<CartItem> cartItemList = new ArrayList<CartItem>();

        for (Map<String, Object> map : mapList) {
            CartItem cartItem = toCartItem(map);

            cartItemList.add(cartItem);
        }

        return cartItemList;
    }


    /**
     * 通过用户编号查找购物车条目
     * @param userId
     * @return
     */
    public List<CartItem> findByUserId(String userId) throws SQLException {
        String sql = "select * from cartitem c,book b where c.bookId=b.bookId and userId=?";
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), userId);

        return toCartItemList(mapList);
    }

    /**
     * 查询某个用户的某本图书的购物车条目是否存在
     * @param userId
     * @param bookId
     * @return
     */
    public CartItem findByUserIdAndBookId(String userId, String bookId) throws SQLException {
        String sql = "select * from cartitem where userId=? and bookId=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), userId, bookId);

        CartItem cartItem = toCartItem(map);

        return cartItem;
    }

    /**
     * 更新购物车条目的数量
     * @param cartItemId
     * @param quantity
     */
    public void updateCartItemQuantity(String cartItemId, int quantity) throws SQLException {
        String sql = "update cartitem set quantity=? where cartItemId=?";

        qr.update(sql, quantity, cartItemId);
    }

    /**
     * 购物车添加条目
     * @param cartItem
     */
    public void addCartItem(CartItem cartItem) throws SQLException {
        String sql = "insert into cartitem values(?, ?, ?, ?)";
        Object[] params = {cartItem.getCartItemId(), cartItem.getQuantity(),
        cartItem.getBook().getBookId(), cartItem.getUser().getUserId()};

        qr.update(sql, params);
    }

    /**
     * 批量删除购物车条目
     * @param cartItemIdArray
     * @throws SQLException
     */
    public void batchDeleteCartItem(Object[] cartItemIdArray) throws SQLException {
        int length = cartItemIdArray.length;

        String sql = "delete from cartitem where cartItemId=?";

        Object[][] params = new Object[length][1];
        for(int i=0; i<length; i++)
        {
            params[i] = new Object[]{cartItemIdArray[i]};
        }

        qr.batch(sql, params);
    }

    /**
     * 通过carItemId查询购物车条目
     * @param cartItemId
     * @return
     */
    public CartItem findByCartItemId(String cartItemId) throws SQLException {
        String sql = "select * from cartitem c,book b where c.bookId=b.bookId and cartItemId=?";

        Map<String,Object> map = qr.query(sql, new MapHandler(), cartItemId);

        return toCartItem(map);
    }

    /**
     * 加载购物车条目，我的订单需要
     * @param cartItemArray
     * @return
     * @throws SQLException
     */
    public List<CartItem> loadCartItems(Object[] cartItemArray) throws SQLException {
        int length = cartItemArray.length;

        StringBuilder sql = new StringBuilder("select * from cartitem c,book b where c.bookId=b.bookId and cartItemId in(");
        for(int i=0; i<length-1; i++)
        {
            sql.append("\'");
            sql.append(cartItemArray[i]);
            sql.append("\'");
            sql.append(",");
        }
        sql.append("\'");
        sql.append(cartItemArray[length-1]);
        sql.append("\'");
        sql.append(")");

        List<Map<String, Object>> mapList = qr.query(sql.toString(), new MapListHandler());
        return toCartItemList(mapList);
    }
}
