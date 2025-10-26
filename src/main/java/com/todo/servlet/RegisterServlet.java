package com.todo.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.todo.dao.UserDAO;
import com.todo.model.User;
import com.todo.util.PasswordUtil;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        
        // Validation
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "All fields are required");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // Validate password strength
        if (!PasswordUtil.isValidPassword(password)) {
            request.setAttribute("errorMessage", "Password must be at least 6 characters long");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // Check if username already exists
        if (userDAO.isUsernameExists(username)) {
            request.setAttribute("errorMessage", "Username already exists");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // Check if email already exists
        if (userDAO.isEmailExists(email)) {
            request.setAttribute("errorMessage", "Email already registered");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // Hash password and create user
        String hashedPassword = PasswordUtil.hashPassword(password);
        User newUser = new User(username, email, hashedPassword, fullName);
        
        if (userDAO.registerUser(newUser)) {
            // Registration successful
            request.setAttribute("successMessage", "Registration successful! Please login.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
