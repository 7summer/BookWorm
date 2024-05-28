# 项目介绍

- 这个项目适合刚刚学完javaweb的同学用来学习，[这个项目的视频链接](https://www.bilibili.com/video/BV1Sb411E7uQ/?spm_id_from=333.337.search-card.all.click&vd_source=df48f8231547cbb9c5b77010c00d24f4,"传智博客网上书城项目")。
- 这个项目跟传智播客的学习项目不太一样，作者做出了许多改动，原因是传智播客原项目中存在工具包，这些工具包可以方便他们学员的开发，作者并没有使用这些工具包，读者可以大胆使用本项目来作为学习。 
- 开发语言：java、html、css、javascript
- 框架：jquery
- 项目开发分为三层架构：控制层、业务处理层、数据层



## 数据表介绍
|   表名    |           描述           |
| :-------: | :----------------------: |
|   user    |       存储用户信息       |
|   admin   |      存储管理员信息      |
| cartitem  |    存储用户购物车信息    |
|  collect  |     存储用户收藏信息     |
| category  |   存储图书信息分类信息   |
|   book    |       存储图书信息       |
| orderitem | 存储订单中图书的详细信息 |
|   order   |       存储订单信息       |



## 项目包的介绍

本项目使用了三层架构，dao层代表数据库层、service层代表事务处理层、servlet层代表控制层<br>

|   包名   |                   描述                   |
| :------: | :--------------------------------------: |
|   user   |           用户登录、注册、退出           |
| category |          显示一级分类和二级分类          |
|   book   |            用户查询、显示图书            |
|   cart   |  加入购物车、查询购物车、购物车条目处理  |
| collect  |       收藏图书、查看收藏、删除收藏       |
|  order   | 生成订单、查询订单、取消订单、支付、收货 |
|   page   |    导航条实体类、数据库查询语句实体类    |
|  filter  |        编码过滤器、用户登录过滤器        |
|  listen  |             关闭服务器时处理             |
|  admin   |               管理员操作包               |

管理员操作包（admin）

|   包名   |                   描述                   |
| :------: | :--------------------------------------: |
|  admin   |             管理员登录、退出             |
| category |    显示一级分类和二级分类、增删改分类    |
|   book   |     管理员查询、显示图书、增删改图书     |
|  order   | 查看订单、按订单状态查询、取消订单、发货 |
|  filter  |             管理员登录过滤器             |



## web文件夹介绍

由于文件较多，选择使用压缩包的形式上传

|  文件名   |      描述      |
| :-------: | :------------: |
|  jquery   |   jquery框架   |
|   menu    |   分类百叶窗   |
| adminfile |   管理员界面   |
| book_img  |    图书图片    |
|  images   | 界面用到的图片 |
|    css    | 用户界面的css  |
|    js     |  用户界面的js  |
|    jsp    | 用户界面的jsp  |
| index.jsp |    用户主页    |



# 项目细节介绍

## ajax请求

```java
public void ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
{
    //获取邮箱
    String email = req.getParameter("email");
    //通过service得到校验结果
    boolean b = userService.ajaxValidateEmail(email);
    //发给客户端
    PrintWriter out = resp.getWriter();
    out.print(b);
}
```

> 通过PrintWriter将结果输出到页面



## 通用查询

```java
/**
 * sql语句条件表达式
 */
public class Expression {
    // 数据库字段名
    private String name;
    // 符号（=, is null, is not null, like）
    private String operator;
    // 值
    private String value;
}
```

```java
/**
 * 分页Bean，会在各层之间传递
 */
public class PageBean {
    private int pageCode; //当前页码
    private int totalRecord; //总记录数
    private int pageSize; //每页记录数
    private int totalPage; //页数
    private String url; //请求路径和参数
    private List beanList; // 结果列表
}
```

```java
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
```



# 敏感信息

- 需要修改/user/domain/Email.java文件
  - 变量from：发件人邮箱地址
  - 变量username：发件人昵称
  - 变量password：邮箱客户端授权码
  - 方法initProperties
    - properties.setProperty("mail.stmp.host", ""); //SMTP服务器
- c3p0-config.xml文件（需要填写数据库用户名和密码、jdbc地址）
