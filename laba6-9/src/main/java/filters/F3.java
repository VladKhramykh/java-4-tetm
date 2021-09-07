package filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/GoCcc")
public class F3 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter F3 init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PrintWriter pw = servletResponse.getWriter();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("Execute Filter F3");
        int length = 0;
        if (servletRequest.getParameter("Value1") != "")
             length += servletRequest.getParameter("Value1").length();
        if (servletRequest.getParameter("Value2") != "")
            length += servletRequest.getParameter("Value1").length();
        if (servletRequest.getParameter("Value3") != "")
            length += servletRequest.getParameter("Value1").length();
        if (length < 10)
            filterChain.doFilter(servletRequest,servletResponse);
        else
            pw.println("<h1>Filter F3 blocked request</h1>");
    }

    @Override
    public void destroy() {
        System.out.println("Filter F3 destroy");
    }
}
