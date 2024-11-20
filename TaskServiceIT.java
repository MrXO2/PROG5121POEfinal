package com.easykanban.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceIT {
    private TaskService taskService;
    
    @BeforeEach
    public void setUp() {
        taskService = new TaskService();
        taskService.addTestData();
    }

    @Test
    public void testDeveloperArrayPopulation() {
        String[] developers = taskService.getDevelopers();
        assertEquals(4, developers.length, "Developer array should contain 4 entries");
        assertEquals("Mike Smith", developers[0], "First developer should be Mike Smith");
        assertEquals("Edward Harrison", developers[1], "Second developer should be Edward Harrison");
        assertEquals("Samantha Paulson", developers[2], "Third developer should be Samantha Paulson");
        assertEquals("Glenda Oberholzer", developers[3], "Fourth developer should be Glenda Oberholzer");
    }

    @Test
    public void testDisplayLongestTask() {
        String result = taskService.displayLongestTask();
        assertTrue(result.contains("Glenda Oberholzer"), "Should find Glenda Oberholzer");
        assertTrue(result.contains("11"), "Should show duration of 11 hours");
    }

    @Test
    public void testSearchTask() {
        String result = taskService.searchTask("Create Login");
        assertTrue(result.contains("Mike Smith"), "Should find Mike Smith as developer");
        assertTrue(result.contains("Create Login"), "Should find Create Login task");
        assertTrue(result.contains("To Do"), "Should show To Do status");
    }

    @Test
    public void testSearchDeveloperTasks() {
        String result = taskService.searchDeveloperTasks("Samantha Paulson");
        assertTrue(result.contains("Create Reports"), "Should find Create Reports task");
        assertTrue(result.contains("Done"), "Task should be marked as Done");
    }

    @Test
    public void testDeleteTask() {
        assertTrue(taskService.deleteTask("Create Reports"), "Delete should return true");
        assertEquals("Task not found.", taskService.searchTask("Create Reports"), 
            "Task should not be found after deletion");
    }

    @Test
    public void testDisplayReport() {
        String report = taskService.displayReport();
        assertTrue(report.contains("Mike Smith"), "Report should contain Mike Smith");
        assertTrue(report.contains("Create Login"), "Report should contain Create Login task");
        assertTrue(report.contains("Edward Harrison"), "Report should contain Edward Harrison");
        assertTrue(report.contains("Create Add Features"), "Report should contain Create Add Features task");
        assertTrue(report.contains("Samantha Paulson"), "Report should contain Samantha Paulson");
        assertTrue(report.contains("Create Reports"), "Report should contain Create Reports task");
        assertTrue(report.contains("Glenda Oberholzer"), "Report should contain Glenda Oberholzer");
        assertTrue(report.contains("Add Arrays"), "Report should contain Add Arrays task");
    }

    @Test
    public void testAddTasks() {
        TaskService newInstance = new TaskService();
        assertEquals(0, newInstance.getDevelopers().length, "Should start with empty array");
        newInstance.addTestData();
        assertEquals(4, newInstance.getDevelopers().length, "Should have 4 developers after adding test data");
    }

    @Test
    public void testGetTaskDurations() {
        int[] durations = taskService.getTaskDurations();
        assertEquals(4, durations.length, "Should have 4 durations");
        assertEquals(5, durations[0], "First task should have duration 5");
        assertEquals(8, durations[1], "Second task should have duration 8");
        assertEquals(2, durations[2], "Third task should have duration 2");
        assertEquals(11, durations[3], "Fourth task should have duration 11");
    }

    @Test
    public void testGetTaskStatuses() {
        String[] statuses = taskService.getTaskStatuses();
        assertEquals(4, statuses.length, "Should have 4 statuses");
        assertEquals("To Do", statuses[0], "First task should be To Do");
        assertEquals("Doing", statuses[1], "Second task should be Doing");
        assertEquals("Done", statuses[2], "Third task should be Done");
        assertEquals("To Do", statuses[3], "Fourth task should be To Do");
    }
}