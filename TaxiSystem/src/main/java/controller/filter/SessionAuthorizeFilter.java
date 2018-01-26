package controller.filter;

import controller.command.impl.SignManager;
import entity.User;
import entity.entityEnum.UserEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Immediately redirects to a standard user page if a user has an open session.
 */
public class SessionAuthorizeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Filter the specified input.
     *
     * @param servletRequest  standard parameter parameter
     * @param servletResponse standard parameter parameter
     * @param filterChain     standard parameter parameter
     * @throws IOException      standard exception
     * @throws ServletException standard exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        User user;
        if ((user = (User) req.getSession().getAttribute(UserEnum.USER.getValue())) != null) {
            if (user.getRole() != null) {
                req.getRequestDispatcher(new SignManager().chooseUserPage(user.getRole())).forward(servletRequest, servletResponse);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}