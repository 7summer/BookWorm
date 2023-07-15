package cn.BookWorm.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/CartItemServlet","/jsp/cart/*",
        "/OrderServlet","/jsp/order/*"})
public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Object user = req.getSession().getAttribute("user");
        if(user == null) {
            req.setAttribute("code", "错误");//为了显示X图片
            req.setAttribute("message", "您还没有登录，不能访问本资源");
            req.getRequestDispatcher("/jsp/msg.jsp").forward(req, resp);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);//放行
        }
    }

    @Override
    public void destroy() {

    }
}
