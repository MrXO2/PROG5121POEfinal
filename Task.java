package com.easykanban.taskmanager;

public class Task {
    public enum TaskStatus {
        TO_DO("To Do"),
        DOING("Doing"),
        DONE("Done");

        private final String displayName;

        TaskStatus(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    private String name;
    private int number;
    private String description;
    private String developerDetails;
    private int duration;
    private String taskID;
    private TaskStatus status;

    // Constructor
    public Task(String name, int number, String description, String developerDetails, int duration) {
        if (description.length() > 50) {
            throw new IllegalArgumentException("Description must be 50 characters or less");
        }
        this.name = name;
        this.number = number;
        this.description = description;
        this.developerDetails = developerDetails;
        this.duration = duration;
        this.status = TaskStatus.TO_DO;
        this.taskID = generateTaskID();
    }

    // Method to generate unique task ID
    private String generateTaskID() {
        return name.substring(0, 2).toUpperCase() + ":" + number + ":" + 
               developerDetails.substring(developerDetails.length() - 3).toUpperCase();
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public String getDescription() { return description; }
    public void setDescription(String description) { 
        if (description.length() > 50) {
            throw new IllegalArgumentException("Description must be 50 characters or less");
        }
        this.description = description; 
    }
    public String getDeveloperDetails() { return developerDetails; }
    public void setDeveloperDetails(String developerDetails) { this.developerDetails = developerDetails; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public String getTaskID() { return taskID; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Task ID: " + taskID + "\n" +
               "Name: " + name + "\n" +
               "Description: " + description + "\n" +
               "Developer: " + developerDetails + "\n" +
               "Duration: " + duration + " hours\n" +
               "Status: " + status;
    }
}