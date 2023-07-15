package cn.BookWorm.book.dao;

import cn.BookWorm.DataUntil;
import cn.BookWorm.book.domain.Book;
import cn.BookWorm.category.domain.Category;
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

public class BookDao {
    private QueryRunner qr = new QueryRunner(DataUntil.getDataSource());

    /**
     * 通用查询方法
     * @param expressionList sql语句的where条件
     * @param pageCode 页码
     */
    private PageBean findByCriteria(List<Expression> expressionList, int pageCode) throws SQLException {
        int pageSize = 10; //每页记录数
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

        String sql = "select count(*) from book " + whereSql.toString();
        Number number = qr.query(sql, new ScalarHandler<>(), params.toArray());
        int totalRecord = number.intValue(); //总记录数

        sql = "select * from book " + whereSql.toString() + " limit ?,?";
        params.add((pageCode-1) * pageSize); //当前页首行记录的下标
        params.add(pageSize); //查询行数
        List<Book> bookList = qr.query(sql, new BeanListHandler<Book>(Book.class), params.toArray());

        //创建pageBean，设置参数
        PageBean pageBean = new PageBean();
        //还没设置pageBean的url属性
        pageBean.setPageCode(pageCode);
        pageBean.setTotalRecord(totalRecord);
        pageBean.setPageSize(pageSize);
        pageBean.setBeanList(bookList);

        return pageBean;
    }

    /**
     * 按分类号查询
     * @param categoryId 分类号
     * @param pageCode 页码
     * @return
     */
    public PageBean findByCategoryId(String categoryId, int pageCode) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression("categoryId", "=", categoryId));

        return findByCriteria(expressionList, pageCode);
    }

    /**
     * 按书名查询
     * @param bookName
     * @param pageCode
     * @return
     */
    public PageBean findByBookName(String bookName, int pageCode) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression("bookName", "like", "%" + bookName + "%"));

        return findByCriteria(expressionList, pageCode);
    }

    /**
     * 按作者查询
     * @param author
     * @param pageCode
     * @return
     * @throws SQLException
     */
    public PageBean findByAuthor(String author, int pageCode) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression("author", "like", "%" + author + "%"));

        return findByCriteria(expressionList, pageCode);
    }

    /**
     * 按出版社查询
     * @param
     * @param pageCode
     * @return
     * @throws SQLException
     */
    public PageBean findByPublishHouse(String publishHouse, int pageCode) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression("publishHouse", "like", "%" + publishHouse + "%"));

        return findByCriteria(expressionList, pageCode);
    }

    /**
     * 多条件组合查询
     * @param combination book对象存储书名、作者、出版社
     * @param pageCoce
     * @return
     */
    public PageBean findByCombination(Book combination, int pageCoce) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression("bookName", "like", "%" + combination.getBookName() + "%"));
        expressionList.add(new Expression("author", "like", "%" + combination.getAuthor() + "%"));
        expressionList.add(new Expression("publishHouse", "like", "%" + combination.getPublishHouse() + "%"));

        return findByCriteria(expressionList, pageCoce);
    }

    /**
     * 按图书编号查询
     * @param bookId
     * @return
     */
    public Book findByBookId(String bookId) throws SQLException {
        String sql = "select * from book where bookId=?";

        Map<String, Object> map = qr.query(sql, new MapHandler(), bookId);

//        Book book = QuakeTools.mapToObject(map, Book.class);
        Book book = new Book();
        try {
            BeanUtils.copyProperties(book, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

//        Category category = QuakeTools.mapToObject(map, Category.class);
        Category category = new Category();
        try {
            BeanUtils.copyProperties(category, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        book.setParent(category);

        return book;
    }
}
