package com.intuit.ctof14.util;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

public class ResponseCorsFilter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        res.addHeader("Access-Control-Allow-Headers", "page-size, intuit_realmid, Authorization, intuit_userid, page-token, synch-token, X-Request-Id, accept, csrftoken, origin, cookie, Content-Type, X-Requested-With, Content-Type");
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Max-Age", "1728000");
        res.addHeader("Access-Control-Expose-Headers","page-token, synch-token");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}