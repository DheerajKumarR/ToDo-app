<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.todo.model.Todo"%>
<%@ page import="com.todo.model.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard - Todo App</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<%
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        List<Todo> todos = (List<Todo>) request.getAttribute("todos");
        int totalTodos = (int) request.getAttribute("totalTodos");
        int completedTodos = (int) request.getAttribute("completedTodos");
        int pendingTodos = (int) request.getAttribute("pendingTodos");
        String currentFilter = (String) request.getAttribute("currentFilter");
    %>

	<!-- Header -->
	<header class="header">
		<div class="container">
			<h1>📝 Todo App</h1>
			<div class="user-info">
				<span>Welcome, <strong><%= user.getFullName() %></strong></span> <a
					href="logout" class="btn btn-secondary">Logout</a>
			</div>
		</div>
	</header>

	<!-- Main Content -->
	<main class="container">
		<!-- Statistics Cards -->
		<div class="stats-container">
			<div class="stat-card">
				<h3><%= totalTodos %></h3>
				<p>Total Todos</p>
			</div>
			<div class="stat-card stat-pending">
				<h3><%= pendingTodos %></h3>
				<p>Pending</p>
			</div>
			<div class="stat-card stat-completed">
				<h3><%= completedTodos %></h3>
				<p>Completed</p>
			</div>
		</div>

		<!-- Add Todo Form -->
		<div class="todo-form-container">
			<h2>➕ Add New Todo</h2>
			<form action="todo" method="post" class="todo-form">
				<input type="hidden" name="action" value="add">

				<div class="form-row">
					<input type="text" name="title" placeholder="Todo title" required>
					<select name="priority" required>
						<option value="LOW">Low Priority</option>
						<option value="MEDIUM" selected>Medium Priority</option>
						<option value="HIGH">High Priority</option>
					</select> <input type="date" name="targetDate" required>
					<button type="submit" class="btn btn-primary">Add Todo</button>
				</div>

				<textarea name="description" placeholder="Description (optional)"
					rows="2"></textarea>
			</form>
		</div>

		<!-- Filter Buttons -->
		<div class="filter-container">
			<h2>📋 Your Todos</h2>
			<div class="filter-buttons">
				<a href="dashboard?filter=all"
					class="btn btn-filter <%= "all".equals(currentFilter) ? "active" : "" %>">
					All (<%= totalTodos %>)
				</a> <a href="dashboard?filter=pending"
					class="btn btn-filter <%= "pending".equals(currentFilter) ? "active" : "" %>">
					Pending (<%= pendingTodos %>)
				</a> <a href="dashboard?filter=completed"
					class="btn btn-filter <%= "completed".equals(currentFilter) ? "active" : "" %>">
					Completed (<%= completedTodos %>)
				</a>
			</div>
		</div>

		<!-- Todos List -->
		<div class="todos-container">
			<% if (todos == null || todos.isEmpty()) { %>
			<div class="empty-state">
				<p>🎉 No todos found! Start by adding a new todo above.</p>
			</div>
			<% } else { %>
			<% for (Todo todo : todos) { %>
			<div
				class="todo-card <%= todo.isCompleted() ? "completed" : "" %> priority-<%= todo.getPriority().toLowerCase() %>">
				<div class="todo-header">
					<h3>
						<%= todo.isCompleted() ? "✅" : "⏳" %>
						<%= todo.getTitle() %>
					</h3>
					<span
						class="priority-badge priority-<%= todo.getPriority().toLowerCase() %>">
						<%= todo.getPriority() %>
					</span>
				</div>

				<p class="todo-description"><%= todo.getDescription() != null ? todo.getDescription() : "No description" %></p>

				<div class="todo-meta">
					<span>📅 Target: <%= todo.getTargetDate() %></span> <span>🕒
						Created: <%= todo.getCreatedDate() %></span>
				</div>

				<div class="todo-actions">
					<form action="todo" method="post" style="display: inline;">
						<input type="hidden" name="action" value="toggle"> <input
							type="hidden" name="todoId" value="<%= todo.getTodoId() %>">
						<button type="submit" class="btn btn-success btn-sm">
							<%= todo.isCompleted() ? "↩️ Undo" : "✓ Complete" %>
						</button>
					</form>

					<button
						onclick="editTodo(<%= todo.getTodoId() %>, '<%= todo.getTitle() %>', '<%= todo.getDescription() %>', '<%= todo.getPriority() %>', '<%= todo.getTargetDate() %>', <%= todo.isCompleted() %>)"
						class="btn btn-secondary btn-sm">✏️ Edit</button>

					<form action="todo" method="post" style="display: inline;"
						onsubmit="return confirm('Are you sure you want to delete this todo?');">
						<input type="hidden" name="action" value="delete"> <input
							type="hidden" name="todoId" value="<%= todo.getTodoId() %>">
						<button type="submit" class="btn btn-danger btn-sm">🗑️
							Delete</button>
					</form>
				</div>
			</div>
			<% } %>
			<% } %>
		</div>
	</main>

	<!-- Edit Todo Modal -->
	<div id="editModal" class="modal">
		<div class="modal-content">
			<span class="close" onclick="closeEditModal()">&times;</span>
			<h2>✏️ Edit Todo</h2>
			<form action="todo" method="post">
				<input type="hidden" name="action" value="update"> <input
					type="hidden" name="todoId" id="editTodoId">

				<div class="form-group">
					<label>Title</label> <input type="text" name="title" id="editTitle"
						required>
				</div>

				<div class="form-group">
					<label>Description</label>
					<textarea name="description" id="editDescription" rows="3"></textarea>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label>Priority</label> <select name="priority" id="editPriority">
							<option value="LOW">Low</option>
							<option value="MEDIUM">Medium</option>
							<option value="HIGH">High</option>
						</select>
					</div>

					<div class="form-group">
						<label>Target Date</label> <input type="date" name="targetDate"
							id="editTargetDate">
					</div>
				</div>

				<div class="form-group">
					<label> <input type="checkbox" name="isCompleted"
						id="editCompleted"> Mark as completed
					</label>
				</div>

				<div class="modal-actions">
					<button type="submit" class="btn btn-primary">Save Changes</button>
					<button type="button" class="btn btn-secondary"
						onclick="closeEditModal()">Cancel</button>
				</div>
			</form>
		</div>
	</div>

	<script>
        function editTodo(id, title, description, priority, targetDate, isCompleted) {
            document.getElementById('editTodoId').value = id;
            document.getElementById('editTitle').value = title;
            document.getElementById('editDescription').value = description || '';
            document.getElementById('editPriority').value = priority;
            document.getElementById('editTargetDate').value = targetDate;
            document.getElementById('editCompleted').checked = isCompleted;
            document.getElementById('editModal').style.display = 'block';
        }
        
        function closeEditModal() {
            document.getElementById('editModal').style.display = 'none';
        }
        
        // Close modal when clicking outside
        window.onclick = function(event) {
            var modal = document.getElementById('editModal');
            if (event.target == modal) {
                modal.style.display = 'none';
            }
        }
    </script>
</body>
</html>
