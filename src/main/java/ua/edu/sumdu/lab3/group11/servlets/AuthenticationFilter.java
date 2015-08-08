package ua.edu.sumdu.lab3.group11.servlets;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.obj.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("*")

public class AuthenticationFilter implements Filter {

    public static final String CURRENT_USER = "currentUser";
    static Logger log = Logger.getLogger(AuthenticationFilter.class.getName());

    public void init(FilterConfig fConfig) throws ServletException {
        log.debug("AuthenticationFilter initialized");
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();
        log.debug("Requested Resource: " + uri);

        HttpSession session = req.getSession(false);

        log.debug("session is " + session);

        User user = (User) req.getSession().getAttribute(CURRENT_USER);

        if ((req.getParameter("action") == null || session == null) &&
                (!(uri.endsWith("login.jsp") || uri.endsWith("register.jsp")))) {
            log.error("Redirect to login.");
            res.sendRedirect("login.jsp");
        }else{
            log.debug("Pass the request along the filter chain");
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    public void destroy() {
        //close any resources here
    }
}
