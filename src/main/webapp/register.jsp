<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Register - Todo App</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="auth-page">
	<div class="auth-container">
		<div class="auth-card">
			<h1 class="auth-title">📝 Todo App</h1>
			<h2>Create Account</h2>

			<% if (request.getAttribute("errorMessage") != null) { %>
			<div class="error-message"><%= request.getAttribute("errorMessage") %></div>
			<% } %>

			<form action="register" method="post" class="auth-form">
				<div class="form-group">
					<label for="fullName">Full Name</label> <input type="text"
						id="fullName" name="fullName" placeholder="Enter your full name"
						required>
				</div>

				<div class="form-group">
					<label for="username">Username</label> <input type="text"
						id="username" name="username" placeholder="Choose a username"
						required>
				</div>

				<div class="form-group">
					<label for="email">Email</label> <input type="email" id="email"
						name="email" placeholder="Enter your email" required>
				</div>

				<div class="form-group">
					<label for="password">Password</label> <input type="password"
						id="password" name="password"
						placeholder="Create a password (min 6 characters)" required>
				</div>

				<div class="form-group">
					<label for="confirmPassword">Confirm Password</label> <input
						type="password" id="confirmPassword" name="confirmPassword"
						placeholder="Confirm your password" required>
				</div>

				<button type="submit" class="btn btn-primary btn-block">Register</button>
			</form>

			<p class="auth-footer">
				Already have an account? <a href="login.jsp">Login here</a>
			</p>
		</div>
	</div>
</body>
</html>
