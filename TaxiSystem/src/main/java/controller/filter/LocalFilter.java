package controller.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Immediately allow user to use earlier chosen localization
 */
public class LocalFilter implements Filter {
    private final static String LOCALE_PARAMETER = "local";
    private final static String LOCALE_COOKIE = "LOCAL";

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
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        Cookie[] cookies = request.getCookies();
        if (session.getAttribute(LOCALE_PARAMETER) == null && cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LOCALE_COOKIE)) {
                    session.setAttribute(LOCALE_PARAMETER, cookie.getValue());
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
