package com.todo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.todo.model.Todo;
import com.todo.util.DatabaseConnection;

public class TodoDAO {
    
    /**
     * Add new todo for specific user
     */
    public boolean addTodo(Todo todo) {
        String sql = "INSERT INTO todos (user_id, title, description, is_completed, priority, target_date) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, todo.getUserId());
            pstmt.setString(2, todo.getTitle());
            pstmt.setString(3, todo.getDescription());
            pstmt.setBoolean(4, todo.isCompleted());
            pstmt.setString(5, todo.getPriority());
            pstmt.setDate(6, todo.getTargetDate());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding todo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all todos for a specific user
     */
    public List<Todo> getAllTodosByUser(int userId) {
        List<Todo> todoList = new ArrayList<>();
        String sql = "SELECT * FROM todos WHERE user_id = ? ORDER BY created_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Todo todo = extractTodoFromResultSet(rs);
                todoList.add(todo);
            }
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error fetching todos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return todoList;
    }
    
    /**
     * Get pending todos for a specific user
     */
    public List<Todo> getPendingTodosByUser(int userId) {
        List<Todo> todoList = new ArrayList<>();
        String sql = "SELECT * FROM todos WHERE user_id = ? AND is_completed = FALSE ORDER BY target_date ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Todo todo = extractTodoFromResultSet(rs);
                todoList.add(todo);
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return todoList;
    }
    
    /**
     * Get completed todos for a specific user
     */
    public List<Todo> getCompletedTodosByUser(int userId) {
        List<Todo> todoList = new ArrayList<>();
        String sql = "SELECT * FROM todos WHERE user_id = ? AND is_completed = TRUE ORDER BY created_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Todo todo = extractTodoFromResultSet(rs);
                todoList.add(todo);
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return todoList;
    }
    
    /**
     * Get todo by ID (with user verification)
     */
    public Todo getTodoById(int todoId, int userId) {
        String sql = "SELECT * FROM todos WHERE todo_id = ? AND user_id = ?";
        Todo todo = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, todoId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                todo = extractTodoFromResultSet(rs);
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return todo;
    }
    
    /**
     * Update todo
     */
    public boolean updateTodo(Todo todo) {
        String sql = "UPDATE todos SET title = ?, description = ?, is_completed = ?, priority = ?, target_date = ? WHERE todo_id = ? AND user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, todo.getTitle());
            pstmt.setString(2, todo.getDescription());
            pstmt.setBoolean(3, todo.isCompleted());
            pstmt.setString(4, todo.getPriority());
            pstmt.setDate(5, todo.getTargetDate());
            pstmt.setInt(6, todo.getTodoId());
            pstmt.setInt(7, todo.getUserId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating todo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete todo
     */
    public boolean deleteTodo(int todoId, int userId) {
        String sql = "DELETE FROM todos WHERE todo_id = ? AND user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, todoId);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting todo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Toggle todo completion status
     */
    public boolean toggleTodoStatus(int todoId, int userId) {
        String sql = "UPDATE todos SET is_completed = NOT is_completed WHERE todo_id = ? AND user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, todoId);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get todo statistics for user
     */
    public int[] getTodoStats(int userId) {
        int[] stats = new int[3]; // [total, completed, pending]
        String sql = "SELECT COUNT(*) as total, " +
                     "SUM(CASE WHEN is_completed = TRUE THEN 1 ELSE 0 END) as completed, " +
                     "SUM(CASE WHEN is_completed = FALSE THEN 1 ELSE 0 END) as pending " +
                     "FROM todos WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                stats[0] = rs.getInt("total");
                stats[1] = rs.getInt("completed");
                stats[2] = rs.getInt("pending");
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return stats;
    }
    
    /**
     * Helper method to extract Todo from ResultSet
     */
    private Todo extractTodoFromResultSet(ResultSet rs) throws SQLException {
        return new Todo(
            rs.getInt("todo_id"),
            rs.getInt("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getBoolean("is_completed"),
            rs.getString("priority"),
            rs.getTimestamp("created_date"),
            rs.getDate("target_date")
        );
    }
}
