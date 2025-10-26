package com.todo.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.todo.dao.TodoDAO;
import com.todo.model.Todo;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TodoDAO todoDAO;
    
    @Override
    public void init() throws ServletException {
        todoDAO = new TodoDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        int userId = (int) session.getAttribute("userId");
        
        // Get filter parameter
        String filter = request.getParameter("filter");
        List<Todo> todos;
        
        if ("completed".equals(filter)) {
            todos = todoDAO.getCompletedTodosByUser(userId);
        } else if ("pending".equals(filter)) {
            todos = todoDAO.getPendingTodosByUser(userId);
        } else {
            todos = todoDAO.getAllTodosByUser(userId);
        }
        
        // Get statistics
        int[] stats = todoDAO.getTodoStats(userId);
        
        request.setAttribute("todos", todos);
        request.setAttribute("totalTodos", stats[0]);
        request.setAttribute("completedTodos", stats[1]);
        request.setAttribute("pendingTodos", stats[2]);
        request.setAttribute("currentFilter", filter != null ? filter : "all");
        
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
