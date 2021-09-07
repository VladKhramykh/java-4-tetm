package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

//@WebFilter("/GoCcc")
public class F2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter F2 init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PrintWriter pw = servletResponse.getWriter();
        System.out.println("Execute Filter F2");
        try {
            if (servletRequest.getParameter("Value1") != "" && Integer.parseInt(servletRequest.getParameter("Value1")) < 0)
                throw new Exception();
            if (servletRequest.getParameter("Value2") != "" && Integer.parseInt(servletRequest.getParameter("Value2")) < 0)
                throw new Exception();
            if (servletRequest.getParameter("Value3") != "" && Integer.parseInt(servletRequest.getParameter("Value3")) < 0)
                throw new Exception();
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (Exception ex){
            pw.println("<h1>Filter F2 blocked request</h1>");
        }
    }

    @Override
    public void destroy() {
        System.out.println("Destroy Filter F2");
    }
}
