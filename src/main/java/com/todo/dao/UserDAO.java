package com.todo.dao;

import java.sql.*;
import com.todo.model.User;
import com.todo.util.DatabaseConnection;
import com.todo.util.PasswordUtil;

public class UserDAO {
    
    /**
     * Register a new user
     */
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, email, password_hash, full_name) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getFullName());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Authenticate user login
     */
    public User authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                
                // Verify password using BCrypt
                if (PasswordUtil.verifyPassword(password, storedHash)) {
                    user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("full_name")
                    );
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    user.setLastLogin(rs.getTimestamp("last_login"));
                    
                    // Update last login time
                    updateLastLogin(user.getUserId());
                }
            }
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
        }
        
        return user;
    }
    
    /**
     * Check if username already exists
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Check if email already exists
     */
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Update last login timestamp
     */
    private void updateLastLogin(int userId) {
        String sql = "UPDATE users SET last_login = CURRENT_TIMESTAMP WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User user = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("full_name")
                );
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setLastLogin(rs.getTimestamp("last_login"));
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }
}
