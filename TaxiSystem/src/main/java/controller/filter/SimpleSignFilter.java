package controller.filter;

import entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by DNAPC on 17.01.2018.
 */
public class SimpleSignFilter implements Filter {
    private Set<String> availableLocalPages = null;
    private Set<String> availableActionRequest = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        availableActionRequest = new HashSet<String>(){{
           add("authorization");
           add("registration");
           add("preRestore");
        }};
        availableLocalPages = new HashSet<String>() {{
            add("authorizationProblem.jsp");
            add("banned.jsp");
            add("registrationProblem.jsp");
            add("registrationSuccess.jsp");
            add("index.jsp");
        }};
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null) && (session.getAttribute("user") != null);
        boolean availableLocalRequest = false;
        String pageRequest;
        if ((req.getParameter("method")).equals("localization")) {
            pageRequest = req.getParameter("page");
            availableLocalRequest = isAvailableLocalPage(pageRequest);
        }
        String requestAction = req.getParameter("action");
        boolean availableAction =  availableActionRequest.contains(requestAction);

        if (loggedIn || availableAction || availableLocalRequest) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("/index.jsp");
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isAvailableLocalPage(String page) {
        return availableLocalPages.contains(page);
    }


}