package com.easykanban.taskmanager;
import javax.swing.JOptionPane;

class UserService {
    private User[] users = new User[100];
    private int userCount = 0;

    // Method to register a new user
    public String registerUser(String username, String password, String firstName, String lastName) {
        simulateLoading("Validating details");

        // Validate username and password
        if (!UserValidation.isUsernameValid(username)) {
            return "Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
        if (!UserValidation.isPasswordValid(password)) {
            return "Password is not correctly formatted. Please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";
        }

        // Check if username already exists
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUsername().equals(username)) {
                return "Username already exists.";
            }
        }

        // Add new user
        users[userCount++] = new User(username, password, firstName, lastName);

        simulateLoading("Registering");
        return "User " + username + " has been successfully registered!";
    }

    // Method to authenticate user login
    public String loginUser(String username, String password) {
        simulateLoading("Authenticating");
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUsername().equals(username)) {
                if (users[i].getPassword().equals(password)) {
                    String firstName = users[i].getFirstName();
                    String lastName = users[i].getLastName();
                    return "Welcome " + firstName + ", " + lastName + ", it is great to see you again!";
                } else {
                    return "Invalid password.";
                }
            }
        }
        return "Invalid username.";
    }

    // Method to simulate loading (for user experience)
    public static void simulateLoading(String message) {
        JOptionPane.showMessageDialog(null, message + "...", "Loading", JOptionPane.INFORMATION_MESSAGE);
    }
}