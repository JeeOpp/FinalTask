package controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by DNAPC on 17.01.2018.
 */
public class BlockAuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null) && (session.getAttribute("user") != null);
            boolean isLocalization = "localization".equals(req.getParameter("method"));  //TODO не пашет раздели логику Local и простых страниц
        boolean loginRequest = "authorization".equals(req.getParameter("action"));
        boolean signRequest = "registration".equals(req.getParameter("action"));

        if (loggedIn || loginRequest || signRequest || isLocalization) {
            filterChain.doFilter(req, resp);
        }
        else {
            resp.sendRedirect("/index.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
