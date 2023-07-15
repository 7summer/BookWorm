package cn.BookWorm.collect.domain;

import cn.BookWorm.book.domain.Book;
import cn.BookWorm.user.domain.User;

public class Collect {
    private String collectId; //主键
    private User user; //用户
    private Book book; //图书

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
