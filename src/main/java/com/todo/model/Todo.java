package com.todo.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Todo {
    
    private int todoId;
    private int userId;
    private String title;
    private String description;
    private boolean isCompleted;
    private String priority; // LOW, MEDIUM, HIGH
    private Timestamp createdDate;
    private Date targetDate;
    
    // Constructors
    public Todo() {
    }
    
    public Todo(int userId, String title, String description, String priority, Date targetDate) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.targetDate = targetDate;
        this.isCompleted = false;
    }
    
    public Todo(int todoId, int userId, String title, String description, 
                boolean isCompleted, String priority, Timestamp createdDate, Date targetDate) {
        this.todoId = todoId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.priority = priority;
        this.createdDate = createdDate;
        this.targetDate = targetDate;
    }
    
    // Getters and Setters
    public int getTodoId() {
        return todoId;
    }
    
    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public Timestamp getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    
    public Date getTargetDate() {
        return targetDate;
    }
    
    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }
    
    @Override
    public String toString() {
        return "Todo [todoId=" + todoId + ", userId=" + userId + ", title=" + title + 
               ", description=" + description + ", isCompleted=" + isCompleted + 
               ", priority=" + priority + ", createdDate=" + createdDate + 
               ", targetDate=" + targetDate + "]";
    }
}
