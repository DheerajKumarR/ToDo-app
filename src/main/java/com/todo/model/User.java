package com.todo.model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private String fullName;
    private Timestamp createdAt;
    private Timestamp lastLogin;
    
    // Constructors
    public User() {
    }
    
    public User(String username, String email, String passwordHash, String fullName) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
    }
    
    public User(int userId, String username, String email, String fullName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }
    
    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", email=" + email + 
               ", fullName=" + fullName + ", createdAt=" + createdAt + "]";
    }
}
