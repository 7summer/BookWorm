# 传智博客网上书城项目

## 介绍
这个项目适合刚刚学完javaweb的同学用来学习，[这个项目的视频链接](https://www.bilibili.com/video/BV1Sb411E7uQ/?spm_id_from=333.337.search-card.all.click&vd_source=df48f8231547cbb9c5b77010c00d24f4,"传智博客网上书城项目")。<br/>
这个项目跟传智播客的学习项目不太一样，作者做出了许多改动，原因是传智播客原项目中存在工具包，这些工具包可以方便他们学员的开发，作者并没有使用这些工具包，读者可以大胆使用本项目来作为学习。
开发语言：java、html、css、javascript<br>
框架：jquery

## 项目部署
jdk11、mysql8.0、tomcat8.5.88<br/>
使用idea企业版开发

## 数据表介绍
|    表名     |      描述      |
|:---------:|:------------:|
|   user    |    存储用户信息    |
|   admin   |   存储管理员信息    |
| cartitem  |  存储用户购物车信息   |
|  collect  |   存储用户收藏信息   |
| category  |  存储图书信息分类信息  |
|   book    |    存储图书信息    |
| orderitem | 存储订单中图书的详细信息 |
|   order   |    存储订单信息    |

## 项目包的介绍
本项目使用了三层架构，dao层代表数据库层、service层代表事务处理层、servlet层代表控制层<br>

|    包名    |          描述          |
|:--------:|:--------------------:|
|   user   |      用户登录、注册、退出      |
| category |     显示一级分类和二级分类      |
|   book   |      用户查询、显示图书       |
|   cart   | 加入购物车、查询购物车、购物车条目处理  |
| collect  |    收藏图书、查看收藏、删除收藏    |
|  order   | 生成订单、查询订单、取消订单、支付、收货 |
|   page   |  导航条实体类、数据库查询语句实体类   |
|  filter  |    编码过滤器、用户登录过滤器     |
|  listen  |       关闭服务器时处理       |
|  admin   |        管理员操作包        |

管理员操作包

|    包名    |          描述          |
|:--------:|:--------------------:|
|  admin   |       管理员登录、退出       |
| category |  显示一级分类和二级分类、增删改分类   |
|   book   |   管理员查询、显示图书、增删改图书   |
|  order   | 查看订单、按订单状态查询、取消订单、发货 |
|  filter  |       管理员登录过滤器       |


## web文件夹介绍
由于文件较多，选择使用压缩包的形式上传

|    文件名    |    描述    |
|:---------:|:--------:|
|  jquery   | jquery框架 |
|   menu    |  分类百叶窗   |
| adminfile |  管理员界面   |
| book_img  |   图书图片   |
|  images   | 界面用到的图片  |
|    css    | 用户界面的css |
|    js     | 用户界面的js  |
 |    jsp    | 用户界面的jsp |
| index.jsp |   用户主页   |

## 敏感信息
- 需要修改/user/domain/Email.java文件
  - 变量from
  - 变量username
  - 变量password
  - 方法initProperties
    - properties.setProperty("mail.stmp.host", ""); //SMTP服务器
- c3p0-config.xml文件（需要填写数据库用户名和密码、jdbc地址）
  


