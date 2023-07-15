package cn.BookWorm.order.domain;

import cn.BookWorm.user.domain.User;

import java.util.Date;
import java.util.List;

public class Order {
    private String orderId; //订单编号 主键
    private Date orderTime; //下单时间
    private double total; //总计
    private int status; //状态 1未付款 2未发货 3未收货 4交易成功 5取消
    private String address; //地址
    private User user; //订单所有者
    private List<OrderItem> orderItemList; //订单项

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
