package cn.BookWorm.cart.domain;

import cn.BookWorm.book.domain.Book;
import cn.BookWorm.user.domain.User;

import java.math.BigDecimal;

public class CartItem {
    private String cartItemId; //购物条目编号 主键
    private int quantity; //数量
    private Book book; //图书
    private User user; //用户
    double subtotal; //小计

    public double getSubtotal()
    {
        //防止产生误差
        //要求必须使用String类型构造器
        BigDecimal b1 = new BigDecimal(String.valueOf(book.getCurrentPrice()));
        BigDecimal b2 = new BigDecimal(String.valueOf(quantity));
        BigDecimal b3 = b1.multiply(b2);
        return b3.doubleValue();
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
