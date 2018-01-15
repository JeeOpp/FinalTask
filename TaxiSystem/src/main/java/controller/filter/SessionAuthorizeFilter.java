package controller.filter;

import controller.command.impl.SignManager;
import entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by DNAPC on 03.01.2018.
 */
public class SessionAuthorizeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        User user;
        if((user = (User) req.getSession().getAttribute("user"))!=null){
            if(user.getRole()!=null) {
                req.getRequestDispatcher(new SignManager().chooseUserPage(user.getRole())).forward(servletRequest, servletResponse);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
