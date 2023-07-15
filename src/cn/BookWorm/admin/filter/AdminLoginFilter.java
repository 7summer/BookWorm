package cn.BookWorm.admin.filter;

import cn.BookWorm.admin.admin.domain.Admin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebFilter(filterName = "AdminLoginFilter", urlPatterns = {"/admin/*", "/adminfile/jsp/book/*",
        "/adminfile/jsp/category/*"})
public class AdminLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Admin admin = (Admin) req.getSession().getAttribute("admin");
        if(admin == null)
        {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/adminfile/jsp/admin/login.jsp");
            dispatcher.forward(req, resp);
        }
        else
        {
            filterChain.doFilter(servletRequest, servletResponse);//放行
        }
    }

    @Override
    public void destroy() {

    }
}
