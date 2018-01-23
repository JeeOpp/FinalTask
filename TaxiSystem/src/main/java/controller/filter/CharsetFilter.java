package controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private final static String ENCODING = "characterEncoding";

    private String encoding;

    public void destroy(){
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);
        chain.doFilter(req,resp);
    }

    public void init(FilterConfig filterConfig) throws ServletException{
        encoding = filterConfig.getInitParameter(ENCODING);
    }
}
