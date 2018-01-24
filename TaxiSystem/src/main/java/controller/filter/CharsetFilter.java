package controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * A charset filter for incrementally converting text streams from one charset encoding to another.
 */
public class CharsetFilter implements Filter {
    private final static String ENCODING = "characterEncoding";

    private String encoding;

    public void destroy(){
    }

    /**
     * Filter the specified input.
     * @param req standard parameter parameter
     * @param resp standard parameter parameter
     * @param chain standard parameter parameter
     * @throws IOException  standard exception
     * @throws ServletException standard exception
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);
        chain.doFilter(req,resp);
    }

    public void init(FilterConfig filterConfig) throws ServletException{
        encoding = filterConfig.getInitParameter(ENCODING);
    }
}
