package com.easykanban.taskmanager;

public class UserValidation {
    // Method to validate username
    public static boolean isUsernameValid(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Method to validate password
    public static boolean isPasswordValid(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*()].*");
    }
}