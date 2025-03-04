import task.Task;
import task.Epic;
import task.Subtask;
import taskmanager.FileBackedTaskManager;
import task.Status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    private FileBackedTaskManager taskManager;
    private Path tempFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        tempFilePath = Files.createTempFile("taskManagerTest", ".csv");
        taskManager = new FileBackedTaskManager(tempFilePath.toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFilePath);
    }

    @Test
    public void testCreateTask() {
        Task task = taskManager.createTask("Test Task", "Task description", Duration.ofMinutes(60), LocalDateTime.now());
        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
        assertEquals("Task description", task.getDescription());
        assertEquals(Status.NEW, task.getStatus());
    }

    @Test
    public void testCreateEpic() {
        Epic epic = taskManager.createEpic("Test Epic", "Epic description");
        assertNotNull(epic);
        assertEquals("Test Epic", epic.getTitle());
        assertEquals("Epic description", epic.getDescription());
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void testCreateSubtask() {
        Epic epic = taskManager.createEpic("Test Epic", "Epic description");
        Subtask subtask = taskManager.createSubtask("Test Subtask", "Subtask description", epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());
        assertNotNull(subtask);
        assertEquals("Test Subtask", subtask.getTitle());
        assertEquals("Subtask description", subtask.getDescription());
        assertEquals(subtask.getEpicId(), epic.getId());
    }

    @Test
    public void testRemoveTask() {
        Task task = taskManager.createTask("Task to remove", "Description", Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.removeTask(task.getId());
        assertNull(taskManager.getTask(task.getId()));
    }

    @Test
    public void testRemoveEpic() {
        Epic epic = taskManager.createEpic("Epic to remove", "Description");
        taskManager.removeEpic(epic.getId());
        assertNull(taskManager.getEpic(epic.getId()));
    }
}
