package cn.BookWorm.admin.book.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.book.dao.BookDao;
import cn.BookWorm.book.domain.Book;
import cn.BookWorm.category.domain.Category;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

public class AdminBookDao extends BookDao {
    private QueryRunner qr = new QueryRunner(DataUntil.getDataSource());

    /**
     * 按图书编号查找
     * @param bookId
     * @return
     */
    public Book findByBookId(String bookId) throws SQLException {
        String sql = "select * from book b,category c where b.categoryId=c.categoryId and bookId=?";

        Map<String, Object> map = qr.query(sql, new MapHandler(), bookId);


        Book book = new Book();
        try {
            BeanUtils.copyProperties(book, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Category child = new Category();
        try {
            BeanUtils.copyProperties(child, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Category parent = new Category();
        parent.setCategoryId((String) map.get("parentId"));

        child.setParent(parent);
        book.setParent(child);

        return book;
    }

    /**
     * 编辑图书
     * @param book
     */
    public void updateBook(Book book) throws SQLException {
        String sql = "update book set bookName=?,author=?,price=?,currentPrice=?," +
                "discount=?,publishHouse=?,edition=?,pageNum=?,wordNum=?," +
                "publishTime=?,printTime=?,bookSize=?,paper=?,categoryId=? " +
                "where bookId=?";

        Object[] params = new Object[]{book.getBookName(),book.getAuthor(),book.getPrice(),
        book.getCurrentPrice(),book.getDiscount(),book.getPublishHouse(),book.getEdition(),
        book.getPageNum(),book.getWordNum(),book.getPublishTime(),book.getPrintTime(),
        book.getBookSize(),book.getPaper(),book.getParent().getCategoryId(),book.getBookId()};

        qr.update(sql, params);
    }

    /**
     * 删除图书
     * @param bookId
     */
    public void deleteBook(String bookId) throws SQLException {
        String sql  = "delete from book where bookId=?";

        qr.update(sql, bookId);
    }

    /**
     * 添加图书
     * @param book
     */
    public void addBook(Book book) throws SQLException {
        String sql = "insert into book values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Object[] params = new Object[]{book.getBookId(),book.getBookName(),book.getAuthor(),
        book.getPrice(),book.getCurrentPrice(),book.getDiscount(),book.getPublishHouse(),
        book.getEdition(),book.getPageNum(),book.getWordNum(),book.getPublishTime(),
        book.getPrintTime(),book.getBookSize(),book.getPaper(),book.getParent().getCategoryId(),
        book.getImage_w(),book.getImage_b()};

        qr.update(sql, params);
    }
}

