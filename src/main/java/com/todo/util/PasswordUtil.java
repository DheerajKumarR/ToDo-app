package com.todo.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    /**
     * Hash a password using BCrypt
     * @param plainPassword - The plain text password
     * @return Hashed password
     */
    public static String hashPassword(String plainPassword) {
        // Generate salt and hash password (cost factor of 10)
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }
    
    /**
     * Verify password against stored hash
     * @param plainPassword - The plain text password to verify
     * @param hashedPassword - The stored hashed password
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            System.err.println("Error verifying password: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if password meets minimum requirements
     * @param password - Password to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        return true;
    }
}
