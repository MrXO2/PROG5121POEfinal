package com.easykanban.taskmanager;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        TaskService taskService = new TaskService();
        boolean isLoggedIn = false;
        String currentUser = "";
        boolean isRegistered = false;

        // Main application loop
        while (true) {
            if (!isLoggedIn) {
                // Login/Register menu
                String[] options = isRegistered ? 
                    new String[]{"Login", "Quit"} : 
                    new String[]{"Register", "Login", "Quit"};
                int choice = JOptionPane.showOptionDialog(null, "Welcome to EasyKanban System", "EasyKanban",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (!isRegistered && choice == 0) {
                    isRegistered = registerUser(userService);
                } else if ((isRegistered && choice == 0) || (!isRegistered && choice == 1)) {
                    String loginResult = loginUser(userService);
                    JOptionPane.showMessageDialog(null, loginResult);
                    if (loginResult.startsWith("Welcome")) {
                        isLoggedIn = true;
                        currentUser = loginResult.split(",")[0].split(" ")[1];
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Thank you for using EasyKanban. Goodbye!");
                    return;
                }
            } else {
                // Main menu for logged-in users
                String[] options = {"Add tasks", "Show report", "Show done tasks", "Show longest task", 
                                    "Search task", "Search developer tasks", "Delete task", "Add test data", "Quit"};
                int choice = JOptionPane.showOptionDialog(null, "Welcome to EasyKanban, " + currentUser, "EasyKanban",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                // Handle user's choice
                switch (choice) {
                    case 0:
                        taskService.addTasks(currentUser);
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null, taskService.displayReport());
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null, taskService.displayDoneTasks());
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(null, taskService.displayLongestTask());
                        break;
                    case 4:
                        String taskName = JOptionPane.showInputDialog("Enter task name to search:");
                        JOptionPane.showMessageDialog(null, taskService.searchTask(taskName));
                        break;
                    case 5:
                        String developer = JOptionPane.showInputDialog("Enter developer name to search:");
                        JOptionPane.showMessageDialog(null, taskService.searchDeveloperTasks(developer));
                        break;
                    case 6:
                        String taskToDelete = JOptionPane.showInputDialog("Enter task name to delete:");
                        boolean deleted = taskService.deleteTask(taskToDelete);
                        JOptionPane.showMessageDialog(null, deleted ? "Task deleted successfully" : "Task not found");
                        break;
                    case 7:
                        taskService.addTestData();
                        JOptionPane.showMessageDialog(null, "Test data added successfully!");
                        break;
                    case 8:
                        JOptionPane.showMessageDialog(null, "Logging out. Thank you for using EasyKanban!");
                        isLoggedIn = false;
                        currentUser = "";
                        break;
                }
            }
        }
    }

    // Helper method for user registration
    private static boolean registerUser(UserService userService) {
        while (true) {
            String usernameInfo = "Note:username must contain an underscore and be no more than 5 characters long.";
            String username = JOptionPane.showInputDialog("Create username:\n" + usernameInfo);
            if (username == null) return false;

            String passwordInfo = "Note:password must be at least 8 characters long, contain a capital letter, a number, and a special character.";
            String password = JOptionPane.showInputDialog("Create password:\n" + passwordInfo);
            if (password == null) return false;

            String firstName = JOptionPane.showInputDialog("Enter first name:");
            if (firstName == null) return false;
            String lastName = JOptionPane.showInputDialog("Enter last name:");
            if (lastName == null) return false;

            String result = userService.registerUser(username, password, firstName, lastName);
            JOptionPane.showMessageDialog(null, result);

            if (result.contains("successfully")) {
                return true;
            }

            int retry = JOptionPane.showConfirmDialog(null, "Would you like to try again?", "Registration", JOptionPane.YES_NO_OPTION);
            if (retry != JOptionPane.YES_OPTION) {
                return false;
            }
        }
    }

    // Helper method for user login
    private static String loginUser(UserService userService) {
        String username = JOptionPane.showInputDialog("Enter username:");
        if (username == null) return "";
        String password = JOptionPane.showInputDialog("Enter password:");
        if (password == null) return "";
        return userService.loginUser(username, password);
    }
}