package cn.BookWorm.book.service;

import cn.BookWorm.book.dao.BookDao;
import cn.BookWorm.book.domain.Book;
import cn.BookWorm.page.PageBean;

import java.sql.SQLException;

public class BookService {
    private BookDao bookDao = new BookDao();

    /**
     * 按分类查询
     *
     * @param categoryId
     * @param pageCode
     * @return
     */
    public PageBean findByCategoryId(String categoryId, int pageCode) {
        try {
            return bookDao.findByCategoryId(categoryId, pageCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按书名查询
     *
     * @param bookName
     * @param pageCode
     * @return
     */
    public PageBean findByBookName(String bookName, int pageCode) {
        try {
            return bookDao.findByBookName(bookName, pageCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按作者查询
     *
     * @param author
     * @param pageCode
     * @return
     */
    public PageBean findByAuthor(String author, int pageCode) {
        try {
            return bookDao.findByAuthor(author, pageCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 按出版社查询
     *
     * @param publishHouse
     * @param pageCode
     * @return
     */
    public PageBean findByPublishHouse(String publishHouse, int pageCode) {
        try {
            return bookDao.findByPublishHouse(publishHouse, pageCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 多条件组合查询
     *
     * @param combination
     * @param pageCode
     * @return
     */
    public PageBean findByCombination(Book combination, int pageCode) {
        try {
            return bookDao.findByCombination(combination, pageCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按图书编号查询
     * @param bookId
     * @return
     */
    public Book findByBookId(String bookId)
    {
        try {
            return bookDao.findByBookId(bookId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
