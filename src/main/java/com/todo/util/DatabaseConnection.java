package com.todo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/todo_app?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";  // Change to your MySQL username
    private static final String PASSWORD = "dheeraj";  // Change to your MySQL password
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Get database connection
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            throw new SQLException("Database driver not found", e);
        }
        return connection;
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
