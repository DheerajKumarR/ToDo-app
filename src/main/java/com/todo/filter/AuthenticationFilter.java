package com.todo.filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

@WebFilter({"/dashboard.jsp", "/dashboard", "/todo"})
public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        // Check if user is logged in
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        
        if (isLoggedIn) {
            // User is authenticated, allow request to proceed
            chain.doFilter(request, response);
        } else {
            // User is not authenticated, redirect to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }
    
    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
