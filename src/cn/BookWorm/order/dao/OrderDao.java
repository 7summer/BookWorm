package cn.BookWorm.order.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.book.domain.Book;
import cn.BookWorm.order.domain.Order;
import cn.BookWorm.order.domain.OrderItem;
import cn.BookWorm.page.Expression;
import cn.BookWorm.page.PageBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private QueryRunner qr = new QueryRunner(DataUntil.getDataSource());

    /**
     * 通用查询方法
     * @param expressionList
     * @param pageCode
     * @return
     * @throws SQLException
     */
    private PageBean findByCriteria(List<Expression> expressionList, int pageCode) throws SQLException
    {
        int pageSize = 4; //每页记录数
        //通过expressionList来生成where子句
        StringBuilder whereSql = new StringBuilder("where 1=1");
        //对应sql语句中?的值
        List<Object> params = new ArrayList<Object>();
        //给where语句添加条件
        for(Expression expression : expressionList)
        {
            whereSql.append(" and ").append(expression.getName()).append(" ")
                    .append(expression.getOperator()).append(" ");
            if(!expression.getOperator().equals("is null"))
            {
                whereSql.append("?");
                params.add(expression.getValue());
            }
        }

        String sql = "select count(*) from orders " + whereSql.toString();
        Number number = qr.query(sql, new ScalarHandler<>(), params.toArray());
        int totalRecord = number.intValue(); //总记录数

        sql = "select * from orders " + whereSql.toString() + " order by orderTime desc limit ?,?";
        params.add((pageCode-1) * pageSize); //当前页首行记录的下标
        params.add(pageSize); //查询行数
        //列表中Order对象会丢掉userId
        List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), params.toArray());
        for(Order order : orderList)
        {
            List<OrderItem> orderItemList = loadOrderItemList(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }


        //创建pageBean，设置参数
        PageBean pageBean = new PageBean();
        //还没设置pageBean的url属性
        pageBean.setPageCode(pageCode);
        pageBean.setTotalRecord(totalRecord);
        pageBean.setPageSize(pageSize);
        pageBean.setBeanList(orderList);

        return pageBean;
    }

    /**
     * 通过userId查询用户订单
     * @param userId
     * @param pageCode
     * @return
     * @throws SQLException
     */
    public PageBean findByUserId(String userId, int pageCode) throws SQLException {
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(new Expression("userId", "=", userId));

        return findByCriteria(expressionList, pageCode);
    }

    /**
     * 为指定的Order加载所有的OrderItem
     * @return
     */
    public List<OrderItem> loadOrderItemList(String orderId) throws SQLException {
        String sql = "select * from orderitem where orderId=?";

        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), orderId);

        return toOrderItemList(mapList);
    }

    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList)
    {
        List<OrderItem> orderItemList = new ArrayList<>();

        for(Map<String, Object> map : mapList)
        {
            OrderItem orderItem = toOrderItem(map);

            orderItemList.add(orderItem);
        }

        return orderItemList;
    }

    private OrderItem toOrderItem(Map<String, Object> map)
    {
        if (map == null ||  map.size() == 0) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        try {
            BeanUtils.copyProperties(orderItem, map);
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

        orderItem.setBook(book);

        return orderItem;
    }

    /**
     * 插入订单
     * @param order
     */
    public void createOrder(Order order) throws SQLException {
        //插入订单
        String sql = "insert into orders values(?,?,?,?,?,?)";

        Object[] params1 = {order.getOrderId(), order.getOrderTime(),
        order.getTotal(), order.getStatus(), order.getAddress(), order.getUser().getUserId()};

        qr.update(sql, params1);

        //循环遍历订单的订单条目，插入订单条目
        sql = "insert into orderitem values(?,?,?,?,?,?,?,?)";

        List<OrderItem> orderItemList = order.getOrderItemList();
        int length = orderItemList.size();
        Object[][] params2 = new Object[length][8];

        for(int i=0; i<length; i++)
        {
            OrderItem orderItem = orderItemList.get(i);
            Book book = orderItem.getBook();

            params2[i] = new Object[]{orderItem.getOrderItemId(), orderItem.getQuantity(),
        orderItem.getSubtotal(), book.getBookId(), book.getBookName(), book.getCurrentPrice(),
        book.getImage_b(), orderItem.getOrder().getOrderId()};
        }

        qr.batch(sql, params2);
    }

    /**
     * 通过orderId查找订单信息
     * @param orderId
     * @return
     */
    public Order findByOrderId(String orderId) throws SQLException {
        String sql =  "select * from orders where orderId=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), orderId);

        Order order = new Order();

        try {
            BeanUtils.copyProperties(order, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        List<OrderItem> orderItemList =  loadOrderItemList(orderId);
        order.setOrderItemList(orderItemList);

        return order;
    }

    /**
     * 根据orderId查询订单状态
     * @param orderId
     * @return
     * @throws SQLException
     */
    public int findStatusByOrderId(String orderId) throws SQLException {
        String sql = "select * from orders where orderId=?";

        Order order = qr.query(sql, new BeanHandler<Order>(Order.class), orderId);

        return order.getStatus();
    }

    /**
     * 根据orderId更新订单状态
     */
    public void updateStatus(String orderId, int status) throws SQLException {
        String sql = "update orders set status=? where orderId=?";

        qr.update(sql, status, orderId);
    }
}
