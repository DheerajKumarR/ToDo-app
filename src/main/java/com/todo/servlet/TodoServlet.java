package com.todo.servlet;

import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.todo.dao.TodoDAO;
import com.todo.model.Todo;

@WebServlet("/todo")
public class TodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TodoDAO todoDAO;
    
    @Override
    public void init() throws ServletException {
        todoDAO = new TodoDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");
        
        switch (action) {
            case "add":
                addTodo(request, response, userId);
                break;
            case "update":
                updateTodo(request, response, userId);
                break;
            case "delete":
                deleteTodo(request, response, userId);
                break;
            case "toggle":
                toggleTodo(request, response, userId);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
    
    private void addTodo(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priority = request.getParameter("priority");
        String targetDateStr = request.getParameter("targetDate");
        
        if (title != null && !title.trim().isEmpty()) {
            Date targetDate = null;
            if (targetDateStr != null && !targetDateStr.isEmpty()) {
                targetDate = Date.valueOf(targetDateStr);
            }
            
            Todo todo = new Todo(userId, title, description, priority, targetDate);
            todoDAO.addTodo(todo);
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
    
    private void updateTodo(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        
        int todoId = Integer.parseInt(request.getParameter("todoId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priority = request.getParameter("priority");
        String targetDateStr = request.getParameter("targetDate");
        boolean isCompleted = "on".equals(request.getParameter("isCompleted"));
        
        Date targetDate = null;
        if (targetDateStr != null && !targetDateStr.isEmpty()) {
            targetDate = Date.valueOf(targetDateStr);
        }
        
        Todo todo = new Todo();
        todo.setTodoId(todoId);
        todo.setUserId(userId);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setPriority(priority);
        todo.setTargetDate(targetDate);
        todo.setCompleted(isCompleted);
        
        todoDAO.updateTodo(todo);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
    
    private void deleteTodo(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        
        int todoId = Integer.parseInt(request.getParameter("todoId"));
        todoDAO.deleteTodo(todoId, userId);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
    
    private void toggleTodo(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        
        int todoId = Integer.parseInt(request.getParameter("todoId"));
        todoDAO.toggleTodoStatus(todoId, userId);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
