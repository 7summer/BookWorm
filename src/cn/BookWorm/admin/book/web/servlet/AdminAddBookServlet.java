package cn.BookWorm.admin.book.web.servlet;

import cn.BookWorm.admin.book.service.AdminBookService;
import cn.BookWorm.admin.category.serivce.AdminCategoryService;
import cn.BookWorm.book.domain.Book;
import cn.BookWorm.category.domain.Category;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "AdminAddBookServlet", urlPatterns = "/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
    private AdminCategoryService adminCategoryService = new AdminCategoryService();
    private AdminBookService adminBookService = new AdminBookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建工具
        FileItemFactory factory = new DiskFileItemFactory();

        //创建解析器对象
        ServletFileUpload upload = new ServletFileUpload(factory);

        upload.setFileSizeMax(1024*100); //文件上限100KB
        //解析request得到List<FileItem>
        List<FileItem> fileItemList = null;
        try {
            fileItemList = upload.parseRequest(req);
        } catch (FileUploadException e) {
            error("上传的文件超过100KB", null, req, resp);
            throw new RuntimeException(e);
        }

        //将List<FileItem>封装到Book对象中
        //先将普通表单字段封装到map中
        Map<String, Object> map = new HashMap<>();
        for(FileItem fileItem : fileItemList)
        {
            if(fileItem.isFormField())
            {
                map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
            }
        }

        Book book = new Book();
        try {
            BeanUtils.copyProperties(book, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Category parent = new Category();
        parent.setCategoryId((String) map.get("childId"));

        book.setParent(parent);

        //把上传的图片保存起来（大图）
        //获取文件名
        FileItem fileItem = fileItemList.get(1); //获取大图
        String filename = fileItem.getName();
        //校验文件名称的扩展名
        if(filename.toLowerCase().endsWith(".jpg") == false)
        {
            error("上传的图片扩展名必须是jpg", book,req, resp);
            return;
        }
        //截取文件名
        int index = filename.lastIndexOf("\\");
        if(index != -1)
        {
            filename = filename.substring(index+1);
        }
        //给文件名添加uuid前缀
        filename = UUID.randomUUID().toString().replaceAll("-", "")
                + "_" + filename;
        //保存图片
        //获取真实路径
        String savePath = this.getServletContext().getRealPath("/book_img");
        //创建目标文件
        File destFile = new File(savePath, filename);
        //校验尺寸
        ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
        Image image = icon.getImage();
        if(image.getWidth(null)>350 || image.getHeight(null)>350)
        {
            error("上传的图片超过尺寸350*350", book, req, resp);
            return;
        }
        //保存文件
        try {
            fileItem.write(destFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //图片路径设置给book对象
        book.setImage_w("book_img/" + filename);

        //把上传的图片保存起来（小图）
        //获取文件名
        fileItem = fileItemList.get(2); //获取小图
        filename = fileItem.getName();
        //校验文件名称的扩展名
        if(filename.toLowerCase().endsWith(".jpg") == false)
        {
            error("上传的图片扩展名必须是jpg", book, req, resp);
            return;
        }
        //截取文件名
        index = filename.lastIndexOf("\\");
        if(index != -1)
        {
            filename = filename.substring(index+1);
        }
        //给文件名添加uuid前缀
        filename = UUID.randomUUID().toString().replaceAll("-", "")
                + "_" + filename;
        //保存图片
        //获取真实路径
        savePath = this.getServletContext().getRealPath("/book_img");
        //创建目标文件
        destFile = new File(savePath, filename);
        //校验尺寸
        icon = new ImageIcon(destFile.getAbsolutePath());
        image = icon.getImage();
        if(image.getWidth(null)>350 || image.getHeight(null)>350)
        {
            error("上传的图片超过尺寸350*350", book, req, resp);
            return;
        }
        //保存文件
        try {
            fileItem.write(destFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //图片路径设置给book对象
        book.setImage_b("book_img/" + filename);

        book.setBookId(UUID.randomUUID().toString().replaceAll("-", ""));

        adminBookService.addBook(book);

        req.setAttribute("code","成功");
        req.setAttribute("message", "添加图书成功");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/msg.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 返回添加图书页面，显示错误信息
     * @param message 信息
     * @param book 图书信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void error(String message, Book book, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("msg", message);
        req.setAttribute("parents", adminCategoryService.findFirstLevel());
        req.setAttribute("book", book);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/book/add.jsp");
        dispatcher.forward(req, resp);
    }
}
