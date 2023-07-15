package cn.BookWorm.admin.order.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.order.dao.OrderDao;
import cn.BookWorm.order.domain.Order;
import cn.BookWorm.order.domain.OrderItem;
import cn.BookWorm.page.Expression;
import cn.BookWorm.page.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminOrderDao extends OrderDao {
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
     * 加载所有订单
     * @return
     */
    public PageBean findAll(int pageCode) throws SQLException {
        List<Expression> expressionList = new ArrayList<>();

        return findByCriteria(expressionList, pageCode);
    }

    /**
     * 根据订单状态查找订单
     * @param pageCode
     * @param status
     * @return
     */
    public PageBean findByStatus(int pageCode, int status) throws SQLException {
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(new Expression("status", "=", String.valueOf(status)));

        return findByCriteria(expressionList, pageCode);
    }
}
