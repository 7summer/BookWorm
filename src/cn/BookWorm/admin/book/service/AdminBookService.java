package cn.BookWorm.admin.book.service;

import cn.BookWorm.admin.book.dao.AdminBookDao;
import cn.BookWorm.book.domain.Book;
import cn.BookWorm.book.service.BookService;

import java.sql.SQLException;

public class AdminBookService extends BookService{
    private AdminBookDao adminBookDao = new AdminBookDao();

    /**
     * 按图书编号查找
     * @param bookId
     * @return
     */
    public Book findByBookId(String bookId)
    {
        try {
            return adminBookDao.findByBookId(bookId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 编辑图书
     * @param book
     */
    public void updateBook(Book book)
    {
        try {
            adminBookDao.updateBook(book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除图书
     * @param bookId
     */
    public void deleteBook(String bookId)
    {
        try {
            adminBookDao.deleteBook(bookId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加图书
     * @param book
     */
    public void addBook(Book book)
    {
        try {
            adminBookDao.addBook(book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
