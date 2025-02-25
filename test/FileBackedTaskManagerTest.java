import org.junit.jupiter.api.Test;

import task.Task;
import task.Epic;
import task.Subtask;
import task.Status;
import taskmanager.FileBackedTaskManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest {

    @Test
    void testSaveAndLoadEmptyFile() throws IOException {
        File tempFile = Files.createTempFile("tasks", ".csv").toFile();
        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
        assertEquals(0, loadedManager.getAllTasks().size());
        assertEquals(0, loadedManager.getAllEpics().size());
        assertEquals(0, loadedManager.getAllSubtasks().size());

        tempFile.deleteOnExit();
    }

    @Test
    void testSaveAndLoadMultipleTasks() throws IOException {
        File tempFile = Files.createTempFile("tasks", ".csv").toFile();
        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);

        Task task = new Task("Task 1", "Description", 1, Status.NEW);
        Epic epic = new Epic("Epic 1", "Description", 2);
        Subtask subtask = new Subtask("Subtask 1", "Description", 3, Status.NEW, 2);

        manager.createTask(task);
        manager.createEpic(epic);
        manager.createSubtask(subtask);
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
        assertEquals(1, loadedManager.getAllTasks().size());
        assertEquals(1, loadedManager.getAllEpics().size());
        assertEquals(1, loadedManager.getAllSubtasks().size());

        assertEquals(task, loadedManager.getTask(1));
        assertEquals(epic, loadedManager.getEpic(2));
        assertEquals(subtask, loadedManager.getSubtask(3));

        tempFile.deleteOnExit();
    }
}
