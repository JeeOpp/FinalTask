package controller;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by DNAPC on 05.12.2017.
 */
public class LocalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        Cookie[] cookies = request.getCookies();
        if (session.getAttribute("local")==null && cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("LOCAL")) {
                    session.setAttribute("local", cookie.getValue());
                }
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
    }
}
