package controller.filter;


import entity.entityEnum.PaginationEnum;
import entity.entityEnum.UserEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Used to detect invalid requests.
 */
public class SimpleSignFilter implements Filter {
    private static final String INDEX_PAGE = "index.jsp";
    private Set<String> availableLocalPages = null;
    private Set<String> availableActionRequest = null;
    private static final String METHOD = "method";
    private static final String LOCAL_METHOD = "localization";
    private static final String ACTION = "action";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        availableActionRequest = new HashSet<String>(){{
           add("authorization");
           add("registration");
           add("preRestore");
           add("restorePassword");
        }};
        availableLocalPages = new HashSet<String>() {{
            add("authorizationProblem.jsp");
            add("banned.jsp");
            add("registrationProblem.jsp");
            add("registrationSuccess.jsp");
            add("index.jsp");
        }};
    }

    /**
     * Filter the specified input.
     * @param servletRequest standard parameter parameter
     * @param servletResponse standard parameter parameter
     * @param filterChain standard parameter parameter
     * @throws IOException  standard exception
     * @throws ServletException standard exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null) && (session.getAttribute(UserEnum.USER.getValue()) != null);
        boolean availableLocalRequest = false;
        String pageRequest;
        if ((req.getParameter(METHOD)).equals(LOCAL_METHOD)) {
            pageRequest = req.getParameter(PaginationEnum.PAGE.getValue());
            availableLocalRequest = isAvailableLocalPage(pageRequest);
        }
        String requestAction = req.getParameter(ACTION);
        boolean availableAction =  availableActionRequest.contains(requestAction);

        if (loggedIn || availableAction || availableLocalRequest) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect(INDEX_PAGE);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isAvailableLocalPage(String page) {
        return availableLocalPages.contains(page);
    }


}