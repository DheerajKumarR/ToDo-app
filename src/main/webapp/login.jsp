<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Todo App</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="auth-page">
    <div class="auth-container">
        <div class="auth-card">
            <h1 class="auth-title">📝 Todo App</h1>
            <h2>Welcome Back!</h2>
            
            <% if (request.getParameter("logout") != null) { %>
                <div class="success-message">You have been logged out successfully!</div>
            <% } %>
            
            <% if (request.getAttribute("successMessage") != null) { %>
                <div class="success-message"><%= request.getAttribute("successMessage") %></div>
            <% } %>
            
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="error-message"><%= request.getAttribute("errorMessage") %></div>
            <% } %>
            
            <form action="login" method="post" class="auth-form">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" 
                           placeholder="Enter your username" required autofocus>
                </div>
                
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" 
                           placeholder="Enter your password" required>
                </div>
                
                <button type="submit" class="btn btn-primary btn-block">Login</button>
            </form>
            
            <p class="auth-footer">
                Don't have an account? <a href="register.jsp">Register here</a>
            </p>
        </div>
    </div>
</body>
</html>
