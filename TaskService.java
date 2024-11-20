package com.easykanban.taskmanager;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private String[] developers;
    private String[] taskNames;
    private String[] taskIDs;
    private int[] taskDurations;
    private String[] taskStatuses;

    public TaskService() {
        developers = new String[0];
        taskNames = new String[0];
        taskIDs = new String[0];
        taskDurations = new int[0];
        taskStatuses = new String[0];
    }

    public void addTasks(String currentUser) {
        String numTasksStr = JOptionPane.showInputDialog("Enter the number of tasks to add:");
        if (numTasksStr == null) return;
        int numTasks;
        try {
            numTasks = Integer.parseInt(numTasksStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
            return;
        }

        for (int i = 0; i < numTasks; i++) {
            addSingleTask(currentUser);
        }
    }

    private void addSingleTask(String currentUser) {
        String taskName = JOptionPane.showInputDialog("Enter task name:");
        String description = JOptionPane.showInputDialog("Enter task description (max 50 characters):");
        String durationStr = JOptionPane.showInputDialog("Enter task duration (in hours):");
        int duration;
        try {
            duration = Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid duration. Task not added.");
            return;
        }

        String[] statusOptions = {"To Do", "Doing", "Done"};
        int statusChoice = JOptionPane.showOptionDialog(null, "Select task status:", "Task Status",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, statusOptions, statusOptions[0]);

        Task.TaskStatus status;
        switch (statusChoice) {
            case 1:
                status = Task.TaskStatus.DOING;
                break;
            case 2:
                status = Task.TaskStatus.DONE;
                break;
            default:
                status = Task.TaskStatus.TO_DO;
        }

        try {
            Task newTask = new Task(taskName, tasks.size(), description, currentUser, duration);
            newTask.setStatus(status);
            tasks.add(newTask);
            updateArrays();
            JOptionPane.showMessageDialog(null, "Task added successfully!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void addTestData() {
        addTask("Mike Smith", "Create Login", "Login functionality implementation", 5, Task.TaskStatus.TO_DO);
        addTask("Edward Harrison", "Create Add Features", "Implement additional features", 8, Task.TaskStatus.DOING);
        addTask("Samantha Paulson", "Create Reports", "Create reporting module", 2, Task.TaskStatus.DONE);
        addTask("Glenda Oberholzer", "Add Arrays", "Implement array functionality", 11, Task.TaskStatus.TO_DO);
    }

    private void addTask(String developer, String taskName, String description, int duration, Task.TaskStatus status) {
        Task task = new Task(taskName, tasks.size(), description, developer, duration);
        task.setStatus(status);
        tasks.add(task);
        updateArrays();
    }

    private void updateArrays() {
        int size = tasks.size();
        developers = new String[size];
        taskNames = new String[size];
        taskIDs = new String[size];
        taskDurations = new int[size];
        taskStatuses = new String[size];

        for (int i = 0; i < size; i++) {
            Task task = tasks.get(i);
            developers[i] = task.getDeveloperDetails();
            taskNames[i] = task.getName();
            taskIDs[i] = task.getTaskID();
            taskDurations[i] = task.getDuration();
            taskStatuses[i] = task.getStatus().toString();
        }
    }

    public String displayReport() {
        if (tasks.isEmpty()) return "No tasks to display.";

        StringBuilder report = new StringBuilder("Full Task Report:\n\n");
        for (int i = 0; i < tasks.size(); i++) {
            report.append(String.format("Task ID: %s\n", taskIDs[i]))
                  .append(String.format("Name: %s\n", taskNames[i]))
                  .append(String.format("Developer: %s\n", developers[i]))
                  .append(String.format("Duration: %d hours\n", taskDurations[i]))
                  .append(String.format("Status: %s\n\n", taskStatuses[i]));
        }
        return report.toString();
    }

    public String displayDoneTasks() {
        StringBuilder result = new StringBuilder("Completed tasks (Done):\n");
        for (int i = 0; i < tasks.size(); i++) {
            if (taskStatuses[i].equals("Done")) {
                result.append(String.format("Developer: %s\nTask Name: %s\nTask Duration: %d hours\n\n", 
                    developers[i], taskNames[i], taskDurations[i]));
            }
        }
        return result.toString();
    }

    public String displayLongestTask() {
        if (tasks.isEmpty()) return "No tasks available.";
        
        int maxIndex = 0;
        for (int i = 1; i < taskDurations.length; i++) {
            if (taskDurations[i] > taskDurations[maxIndex]) {
                maxIndex = i;
            }
        }
        
        return String.format("Developer: %s\nDuration: %d hours\nTask: %s", 
            developers[maxIndex], taskDurations[maxIndex], taskNames[maxIndex]);
    }

    public String searchTask(String taskName) {
        for (int i = 0; i < taskNames.length; i++) {
            if (taskNames[i].equalsIgnoreCase(taskName)) {
                return String.format("Task Name: %s\nDeveloper: %s\nStatus: %s", 
                    taskNames[i], developers[i], taskStatuses[i]);
            }
        }
        return "Task not found.";
    }

    public String searchDeveloperTasks(String developer) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < developers.length; i++) {
            if (developers[i].equalsIgnoreCase(developer)) {
                result.append(String.format("Task: %s\nStatus: %s\n\n", 
                    taskNames[i], taskStatuses[i]));
            }
        }
        return result.length() > 0 ? result.toString() : "No tasks found for this developer.";
    }

    public boolean deleteTask(String taskName) {
        for (int i = 0; i < tasks.size(); i++) {
            if (taskNames[i].equalsIgnoreCase(taskName)) {
                tasks.remove(i);
                updateArrays();
                return true;
            }
        }
        return false;
    }

    // Getters for testing
    public String[] getDevelopers() { return developers; }
    public String[] getTaskNames() { return taskNames; }
    public String[] getTaskIDs() { return taskIDs; }
    public int[] getTaskDurations() { return taskDurations; }
    public String[] getTaskStatuses() { return taskStatuses; }
}